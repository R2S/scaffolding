package br.ufscar.sagui.scaffolding.mapping

import br.ufscar.sagui.core.crud.SaguiHasManyRestfulController
import br.ufscar.sagui.core.crud.SaguiRestfulController
import br.ufscar.sagui.scaffolding.builder.ClassScaffoldBuilder
import br.ufscar.sagui.scaffolding.interceptor.DomainResource
import br.ufscar.sagui.scaffolding.interceptor.DomainScaffoldStore
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsStringUtils

/**
 * Created by raphael on 13/08/15.
 */
class MappingScaffoldFacade {
    private MappingGrails mappingGrails

    static MappingScaffoldFacade _instance

    public MappingScaffoldFacade(){
        mappingGrails = new MappingGrails()
    }
    static MappingScaffoldFacade getInstance(){
        if(!_instance){
            _instance = new MappingScaffoldFacade()
        }
        return _instance
    }

    def mapped(){
        this.addAll(DomainScaffoldStore.domainResources)
    }

    def addAll(List<DomainResource> domainResources){
        domainResources?.each {
            this.add(it)
        }
    }

    def add(DomainResource domainResource){
        mappingGrails.addMapping(domainResource.url, this.getResources(domainResource.domainClass, domainResource.propertyName), domainResource.domainClass.clazz, domainResource.propertyName, this.getController(domainResource.domainClass, domainResource.propertyName))
    }

    def getResources(GrailsDomainClass domainClass, String propertyName){
        String resource = "defaultScaffoldRest"
        GrailsControllerClass controllerClassDomain = this.getControllerClassDomain(domainClass, propertyName)
        if(controllerClassDomain){
            SaguiRestfulController bean = GrailsUtil.grailsApplication.mainContext.getBean(controllerClassDomain.clazz)
            resource = bean.resourceName
        }
        return resource
    }
    def getController(GrailsDomainClass domainClass, String propertyName){
        String controller = "defaultScaffoldRest"
        GrailsControllerClass controllerClassDomain = this.getControllerClassDomain(domainClass, propertyName)
        if(controllerClassDomain){
            controller = this.getNameController(controllerClassDomain.clazz.simpleName)
        }
        return controller
    }

    String getNameController(String nameSimplesClassController){
        return GrailsStringUtils.uncapitalize(nameSimplesClassController.replace("Controller", ""));
    }

    static GrailsControllerClass getControllerClassDomain(GrailsDomainClass domainClass, String propertyName){
        DomainResource domainResource = DomainScaffoldStore.getDomainResourse(domainClass, propertyName)
         return this.getControllerClassDomain(domainResource)
    }
    
    static GrailsControllerClass getControllerClassDomain( DomainResource domainResource){
        GrailsControllerClass controllerClassDomain = null
        ClassScaffold classScaffold = ClassScaffoldBuilder.instance.builder(domainResource.getDomainClassResourse())
        if(classScaffold.controllerClass){
            Class aClass = Class.forName(classScaffold.controllerClass)
            controllerClassDomain = GrailsUtil.getGrailsApplication().getControllerClasses().find{it.clazz==aClass}
        }
        return controllerClassDomain
    }


    static boolean isBean(GrailsDomainClass domainClass, String propertyName, SaguiRestfulController bean){
        try{
            if(bean.resource == domainClass.clazz){
                if(propertyName) {
                    if(bean instanceof SaguiHasManyRestfulController&&bean.propertyHasMany==propertyName) {
                        return true
                    }
                }else {
                    return true
                }
            }
            return false
        }catch(e){
            return false
        }
    }
}
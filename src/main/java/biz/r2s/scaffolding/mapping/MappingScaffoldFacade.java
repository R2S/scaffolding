package biz.r2s.scaffolding.mapping;

import java.util.List;

import biz.r2s.scaffolding.builder.ClassScaffoldBuilder;
import biz.r2s.scaffolding.interceptor.DomainResource;
import biz.r2s.scaffolding.interceptor.DomainScaffoldStore;
import biz.r2s.scaffolding.meta.ClassScaffold;;

/**
 * Created by raphael on 13/08/15.
 */
public class MappingScaffoldFacade {
    private MappingGrails mappingGrails;

    static MappingScaffoldFacade _instance;

    public MappingScaffoldFacade(){
        mappingGrails = new MappingGrails();
    }
    public static MappingScaffoldFacade getInstance(){
        if(_instance==null){
            _instance = new MappingScaffoldFacade();
        }
        return _instance;
    }

    public void mapped(){
        this.addAll(DomainScaffoldStore.domainResources);
    }

    void addAll(List<DomainResource> domainResources){
    	for(DomainResource domainResource:domainResources){
    		 this.add(domainResource);
    	}
    }

    void add(DomainResource domainResource){
        mappingGrails.addMapping(domainResource.getUrl(), this.getResources(domainResource.getDomainClass(), domainResource.getPropertyName()), domainResource.getDomainClass(), domainResource.getPropertyName(), this.getController(domainResource.getDomainClass(), domainResource.getPropertyName()));
    }

    String getResources(Class domainClass, String propertyName){
        String resource = "defaultScaffoldRest";
        Class controllerClassDomain = this.getControllerClassDomain(domainClass, propertyName);
        if(controllerClassDomain!=null){
            /*SaguiRestfulController bean = GrailsUtil.grailsApplication.mainContext.getBean(controllerClassDomain.clazz)
            resource = bean.resourceName*/
        }
        return resource;
    }
    String getController(Class domainClass, String propertyName){
        String controller = "defaultScaffoldRest";
        Class controllerClassDomain = this.getControllerClassDomain(domainClass, propertyName);
        if(controllerClassDomain!=null){
            controller = this.getNameController(controllerClassDomain.getSimpleName());
        }
        return controller;
    }

    String getNameController(String nameSimplesClassController){
    	StringBuilder sb = new StringBuilder(nameSimplesClassController.replace("Controller", ""));
    	sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
    	return sb.toString();
    }

    static Class getControllerClassDomain(Class domainClass, String propertyName){
        DomainResource domainResource = DomainScaffoldStore.getDomainResourse(domainClass, propertyName);
         return getControllerClassDomain(domainResource);
    }
    
    static Class getControllerClassDomain( DomainResource domainResource){
    	Class controllerClassDomain = null;
        ClassScaffold classScaffold = ClassScaffoldBuilder.getInstance().builder(domainResource.getDomainClassResourse());
        if(classScaffold.getControllerClass()!=null){
            try {
				Class aClass = Class.forName(classScaffold.getControllerClass());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //controllerClassDomain = GrailsUtil.getGrailsApplication().getControllerClasses().find{it.clazz==aClass}
        }
        return controllerClassDomain;
    }


    /*static boolean isBean(Class domainClass, String propertyName, SaguiRestfulController bean){
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
    }*/
}
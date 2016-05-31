package br.ufscar.sagui.scaffolding.interceptor

import br.ufscar.sagui.scaffolding.extractor.clazz.FieldClassExtractor
import br.ufscar.sagui.scaffolding.mapping.MappingScaffoldFacade
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

/**
 * Created by raphael on 07/08/15.
 */
class ScaffoldInterceptor {


    public static void intercept(){

        for (GrailsDomainClass domainClass: GrailsUtil.getGrailsApplication().getDomainClasses()){
            if(isScaffold(domainClass)){
                DomainScaffoldStore.setDomainResourse(domainClass)

                this.getFieldHasMany(domainClass)?.each {
                    DomainScaffoldStore.setDomainResourse(domainClass, it.key)
                }
            }
        }
        MappingScaffoldFacade.getInstance().mapped()
    }

    static boolean isScaffold(GrailsDomainClass domainClass)
    {
        try{
            def scaffolding = domainClass.clazz.scaffolding
            if(scaffolding!=null){
                if(scaffolding.class.asBoolean()){
                    return scaffolding
                }else{
                    return true
                }
            }
        }catch (e){
            return false
        }
    }

    static List<FieldScaffold> getFieldHasMany(GrailsDomainClass domainClass){
        FieldClassExtractor fieldClassExtractor = new FieldClassExtractor()
        ClassScaffold classScaffold = new ClassScaffold()
        classScaffold.fields = fieldClassExtractor.getFields(domainClass, null)
        return classScaffold.fieldHasMany
    }
}

package br.ufscar.sagui.scaffolding.marshallers

import br.ufscar.sagui.core.marshaller.UfscarCustomObjectMarshallers
import br.ufscar.sagui.scaffolding.RulesFacade
import br.ufscar.sagui.scaffolding.builder.ClassScaffoldBuilder
import br.ufscar.sagui.scaffolding.interceptor.ScaffoldInterceptor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.security.PermissionFacade
import br.ufscar.sagui.util.GrailsUtil

/**
 * Created by raphael on 08/09/15.
 */
class ScaffoldCustomObjectMarshallers extends UfscarCustomObjectMarshallers{

    def marshalDefault = { object ->
        def retorno = marshalCompact(object)
        getFields(object).each { nomePropriedade ->
            retorno["$nomePropriedade"] = tratarProperty(object, nomePropriedade, marshalCompact)
        }
        retorno
    }

    def marshalExtended = { object ->
        def retorno = marshalCompact(object)
        getFields(object).each { nomePropriedade ->
            retorno["$nomePropriedade"] = tratarProperty(object, nomePropriedade, marshalDefault)
        }
        retorno
    }

    List<String> getFields(object){
        List<String> fs = []
        if(ScaffoldInterceptor.isScaffold(object?.domainClass)){
            ClassScaffold classScaffold = ClassScaffoldBuilder.instance.builder(GrailsUtil.getDomainClass(object.class))
            def permission = PermissionFacade.newInstance().getPermissionScaffold(classScaffold, null, null, null)
            RulesFacade.instance.getFieldsShow(permission, classScaffold.fields).each {
                fs << it.key
            }
        }else{
            fs = object?.domainClass?.persistentProperties*.name
        }
        return fs
    }
}
package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.TypeFieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.params.DataTableParamsFieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.params.ParamsFactory
import br.ufscar.sagui.scaffolding.meta.field.params.ParamsFieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.params.Select2AjaxParamsFieldScaffold
import br.ufscar.sagui.util.GrailsUtil

/**
 * Created by raphael on 06/08/15.
 */
class ParamsScaffoldingExtrator {

    DataTableScaffoldingExtrator dataTableScaffoldingExtrator
    ActionsScaffoldingExtrator actionsScaffoldingExtrator

    public ParamsScaffoldingExtrator() {
        dataTableScaffoldingExtrator = new DataTableScaffoldingExtrator()
        actionsScaffoldingExtrator = new ActionsScaffoldingExtrator()
    }

    void changeTypeAndParamsFields(def fieldScaffolding, FieldScaffold fieldScaffold) {
        TypeFieldScaffold type = this.getType(fieldScaffolding)
        def paramsConfig = this.getParams(fieldScaffolding)
        if (type) {
            fieldScaffold.type = type
            ParamsFieldScaffold params = ParamsFactory.factory(type)
            if (fieldScaffold.type == TypeFieldScaffold.SELECT2_AJAX) {
                def domain = GrailsUtil.getDomainClass(fieldScaffold.parent.clazz)
                ((Select2AjaxParamsFieldScaffold)params).resourceUrl = ResourceUrlScaffold.builder(domain.getPropertyByName(fieldScaffold.key)?.referencedDomainClass,null)
            }
            if (!fieldScaffold.params.class.equals(params.class)) {
                params.properties.putAll(fieldScaffold.params.properties)
                fieldScaffold.params = params
            }
        }

        if (paramsConfig) {
            if (fieldScaffold.type == TypeFieldScaffold.DATATABLE) {
                DataTableParamsFieldScaffold dataTableParamsFieldScaffold = fieldScaffold.params

                actionsScaffoldingExtrator.changeActions(paramsConfig, dataTableParamsFieldScaffold.actions)
                dataTableScaffoldingExtrator.changeDatatable(paramsConfig, dataTableParamsFieldScaffold)
                dataTableParamsFieldScaffold.aplicarActions()
            } else if (fieldScaffold.params.validate(paramsConfig)) {
                paramsConfig.each {key, value ->
                    fieldScaffold.params."$key" = value
                }
            } else {
                throw new Exception("Parametros invalidos " + fieldScaffold.key)
            }

        }
    }

    TypeFieldScaffold getType(fieldScaffolding) {
        def type = fieldScaffolding.get("type")
        TypeFieldScaffold typeFieldScaffold = null
        if (type) {
            typeFieldScaffold = this.converterParaEnum(type)
        }
        return typeFieldScaffold
    }

    def getParams(fieldScaffolding) {
        fieldScaffolding.get("params")
    }

    TypeFieldScaffold converterParaEnum(def type) {
        if (!(type instanceof TypeFieldScaffold)) {
            try{
                if (type instanceof String) {
                    return TypeFieldScaffold.valueOf(type.toUpperCase())
                } else {
                    throw new Exception("TypeFieldScaffold invalido")
                }
            }catch (e){
                throw new Exception("TypeFieldScaffold invalido")
            }
        }
    }


}

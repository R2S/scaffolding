package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.core.SaguiArquivo
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionsScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.TypeFieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.params.*
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

/**
 * Created by raphael on 28/07/15.
 */
class FieldClassExtractor {

    static FIELD_HIDDEN = ["id", "version"]

    private DatatableClassExtrator datatableClassBuilder = new DatatableClassExtrator()

    private ActionsClassExtractor actionsClassBuilder = new ActionsClassExtractor()

    List<FieldScaffold> getFields(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        List<FieldScaffold> fields = []
        def properties = domainClass.getProperties()
        for (int i = 0; i< properties.size(); i++) {
            fields << this.getFieldScaffold(properties[i], classScaffold, i+1000)
        }
        return fields
    }

    FieldScaffold getFieldScaffold(GrailsDomainClassProperty property, ClassScaffold classScaffold, def order) {
        FieldScaffold fieldScaffold = new FieldScaffold()

        GrailsDomainClass grailsClass = property.getDomainClass()

        fieldScaffold.key = property.name
        fieldScaffold.elementId = this.getId(grailsClass, property)
        fieldScaffold.label = property.naturalName
        fieldScaffold.insertable = true
        fieldScaffold.updateable = true
        def typeAndParams = this.getTypeAndParams(property, fieldScaffold)
        fieldScaffold.type = typeAndParams.type
        fieldScaffold.params = typeAndParams.params
        fieldScaffold.scaffold = true
        fieldScaffold.parent = classScaffold
        fieldScaffold.order = order
        fieldScaffold.clazzType = property.type
        fieldScaffold.bidirecional = property.bidirectional
        fieldScaffold.transients = !property.persistent
        return fieldScaffold

    }

    String getId(GrailsDomainClass grailsClass, GrailsDomainClassProperty property) {
        return "${grailsClass.name}.${property.name}"
    }

    def getTypeAndParams(GrailsDomainClassProperty property, FieldScaffold fieldScaffold) {
        TypeFieldScaffold typeFieldScaffold = TypeFieldScaffold.INPUT
        ParamsFieldScaffold paramsFieldScaffold = new InputParamsFieldScaffold()
        paramsFieldScaffold.setRequired(true)
        Class clazz = property.type
        if(property.name in FIELD_HIDDEN){
            typeFieldScaffold = TypeFieldScaffold.HIDDEN
            paramsFieldScaffold = new HiddenParamsFieldScaffold()
            paramsFieldScaffold.setRequired(true)
        }else if (clazz.isAssignableFrom(Number.class)) {
            if (clazz in [Double, Float, BigDecimal]) {
                typeFieldScaffold = TypeFieldScaffold.NUMBER_PRECISION
                paramsFieldScaffold = new NumberPrecisionParamsFieldScaffold()
                paramsFieldScaffold.setRequired(true)
                paramsFieldScaffold.setPrecision(3)
            } else {
                typeFieldScaffold = TypeFieldScaffold.NUMBER
                paramsFieldScaffold = new NumberParamsFieldScaffold()
                paramsFieldScaffold.setRequired(true)

            }
        } else if (clazz.isAssignableFrom(Date.class)) {
            typeFieldScaffold = TypeFieldScaffold.DATE
            paramsFieldScaffold = new DateParamsFieldScaffold()
            paramsFieldScaffold.setRequired(true)
            paramsFieldScaffold.setDateEmpty(true)
        } else if (clazz.isAssignableFrom(Boolean.class)) {
            typeFieldScaffold = TypeFieldScaffold.CHECKBOX
            paramsFieldScaffold = new CheckboxParamsFieldScaffold()
            paramsFieldScaffold.setRequired(false)
        } else if (clazz.isAssignableFrom( SaguiArquivo.class)) {
            typeFieldScaffold = TypeFieldScaffold.FILE
            paramsFieldScaffold = new FileParamsFieldScaffold()
            paramsFieldScaffold.setRequired(false)
        } else if (property.enum) {
            typeFieldScaffold = TypeFieldScaffold.SELECT2
            paramsFieldScaffold = new Select2ParamsFieldScaffold()
            paramsFieldScaffold.setDataTextField("text")
            paramsFieldScaffold.setDataValueField("id")
            def options = []
            clazz.values()?.each{
                options << [id:it.toString(), text:it.toString()]
            }
            paramsFieldScaffold.setOptions(options)
            paramsFieldScaffold.setRequired(true)
        }else if(property.referencedDomainClass){
            return this.getTypeAndParamsAssocition(property, fieldScaffold)
        }
        paramsFieldScaffold.parent = fieldScaffold
        return [type: typeFieldScaffold, params: paramsFieldScaffold]
    }

    private ActionsScaffold getActions(GrailsDomainClass domainClass) {
        return actionsClassBuilder.getActions(domainClass)
    }

    private getTypeAndParamsAssocition(GrailsDomainClassProperty property, FieldScaffold fieldScaffold) {
        TypeFieldScaffold typeFieldScaffold = TypeFieldScaffold.INPUT
        ParamsFieldScaffold paramsFieldScaffold = new InputParamsFieldScaffold()
       if (property.isBasicCollectionType()) {
            typeFieldScaffold = TypeFieldScaffold.SELECT2
            paramsFieldScaffold = new Select2ParamsFieldScaffold()
            paramsFieldScaffold.setDataTextField("text")
            paramsFieldScaffold.setDataValueField("id")
            paramsFieldScaffold.setMultiple(true)
            paramsFieldScaffold.setRequired(true)
        } else if (property.isOneToMany()) { // hasMany
            typeFieldScaffold = TypeFieldScaffold.DATATABLE
            paramsFieldScaffold = new DataTableParamsFieldScaffold()
            datatableClassBuilder.initDatatableDefault(paramsFieldScaffold, property.referencedDomainClass, property.name)
            paramsFieldScaffold.setResourceUrlScaffold(ResourceUrlScaffold.builder(property.domainClass, property.name))
            paramsFieldScaffold.setActions(this.getActions(property.referencedDomainClass))
        } else if (property.isManyToMany()) {
            typeFieldScaffold = TypeFieldScaffold.SELECT2_AJAX
            paramsFieldScaffold = new Select2AjaxParamsFieldScaffold()
            paramsFieldScaffold.setDataTextField("text")
            paramsFieldScaffold.setDataValueField("id")
            paramsFieldScaffold.setMultiple(true)
            paramsFieldScaffold.setResourceUrl(ResourceUrlScaffold.builder(property.referencedDomainClass, property.name))
        } else {
            typeFieldScaffold = TypeFieldScaffold.SELECT2_AJAX
            paramsFieldScaffold = new Select2AjaxParamsFieldScaffold()
            paramsFieldScaffold.setDataTextField("text")
            paramsFieldScaffold.setDataValueField("id")
            paramsFieldScaffold.setResourceUrl(ResourceUrlScaffold.builder(property.referencedDomainClass))
        }

        paramsFieldScaffold.parent = fieldScaffold
        return [type: typeFieldScaffold, params: paramsFieldScaffold]
    }
}

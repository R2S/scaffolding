package br.ufscar.sagui.scaffolding.format

import br.ufscar.sagui.scaffolding.RulesFacade
import br.ufscar.sagui.scaffolding.interceptor.DomainResource
import br.ufscar.sagui.scaffolding.interceptor.DomainScaffoldStore
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.ResourceUrl
import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.constraint.Constraint
import br.ufscar.sagui.scaffolding.meta.field.params.ParamsFieldScaffold
import br.ufscar.sagui.scaffolding.security.PermissionFacade
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

/**
 * Created by raphael on 11/08/15.
 */
class FieldsFormat {

    CommonFormat commonFormat
    PermissionFacade permissionFacade

    public FieldsFormat(){
        commonFormat = new CommonFormat()
        permissionFacade = new PermissionFacade()
    }

    def formatFieldsAndHasMany(def permission, ClassScaffold classScaffold, List<FieldScaffold> fieldScaffolds, TypeActionScaffold actionScaffold, Class fatherClass=null){
        def listFieldsMeta = []
        def listHasManyMeta = []
        if(actionScaffold == TypeActionScaffold.CREATE){
            RulesFacade.instance.getFieldsCreate(permission, fieldScaffolds).each {FieldScaffold field, boolean readonly ->
                addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, fatherClass, field, readonly)
            }
        }else if(actionScaffold == TypeActionScaffold.EDIT){
            RulesFacade.instance.getFieldsEdit(permission, fieldScaffolds).each {FieldScaffold field, boolean readonly ->
                addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, fatherClass, field, readonly)
            }
        }else if(actionScaffold == TypeActionScaffold.VIEW){
            RulesFacade.instance.getFieldsShow(permission, fieldScaffolds).each {FieldScaffold field ->
                addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, fatherClass, field, true)
            }
        }

        return [fields:listFieldsMeta, hasMany:listHasManyMeta]
    }

    def addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, Class fatherClass, FieldScaffold field, boolean readonly){
        if(field.isTypeHasMany()){
            formatFieldsHasMany(listHasManyMeta, field, readonly)
        }else{
            if(!fatherClass||!RulesFacade.instance.isBidirecional(field, fatherClass)){
                addField(listFieldsMeta, field, readonly)
            }
        }
    }

    def formatFieldsHasMany(def listFieldsMeta, FieldScaffold field, boolean readonly){
        GrailsDomainClassProperty classProperty = GrailsUtil.getDomainClass(field.parent.clazz)?.getPropertyByName(field.key)
        String name = field.label
        String url
        String key
        if(classProperty?.domainClass){
            ResourceUrl resourceUrl = ResourceUrlScaffold.builder(classProperty.domainClass, field.key).resolver(TypeActionScaffold.LIST)
            url = commonFormat.tratarUrl(resourceUrl.formatUrl().path)
            key = DomainScaffoldStore.getKey(classProperty?.domainClass)
        }
        listFieldsMeta << DomainResource.formatMenu(key, name, url, null, null, null)
    }

    def addField(def listFieldsMeta, FieldScaffold field, boolean readonly){
        def meta = this.formatField(field)
        meta.readonly = readonly
        listFieldsMeta << meta
    }

    def formatField(FieldScaffold fieldScaffold){
        def meta = [:]
        meta.key = fieldScaffold.key
        meta.label = fieldScaffold.label
        meta.id = fieldScaffold.elementId
        //meta.constraints = this.formatConstraints(fieldScaffold.constraints)
        meta.type = fieldScaffold.params.type.name()
        meta.params = this.formatParams(fieldScaffold.params)
        meta.params.required = fieldScaffold.isMandatory()
        if(fieldScaffold.icon){
            meta.icon = commonFormat.formatIcon(fieldScaffold.icon)
        }
        meta.defaultValue = fieldScaffold.defaultValue
        return meta
    }

    def formatConstraints(List<Constraint> constraints){
        def constraintsMeta = []
        constraints.each {Constraint constraint->
            constraintsMeta << this.formatConstraint(constraint)
        }
        return constraintsMeta
    }

    def formatConstraint(Constraint constraints){
        [name:constraints.name, type:constraints.type.name(), value:constraints.value]
    }

    def formatParams(ParamsFieldScaffold params){
        return params.format()
    }
}
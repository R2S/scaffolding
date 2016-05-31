package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.security.PermissionClass
import br.ufscar.sagui.scaffolding.meta.security.PermissionField

/**
 * Created by raphael on 06/08/15.
 */
class FieldScaffoldingExtractor {

    IconScaffoldingExtrator iconScaffoldingExtrator

    ConstrantsScaffoldingExtrator constrantsScaffoldingExtrator
    ParamsScaffoldingExtrator paramsScaffoldingExtrator
    PermissionScaffoldingExtrator permissionScaffoldingExtrator

    public FieldScaffoldingExtractor(){
        iconScaffoldingExtrator = new IconScaffoldingExtrator()
        constrantsScaffoldingExtrator = new ConstrantsScaffoldingExtrator()
        paramsScaffoldingExtrator = new ParamsScaffoldingExtrator()
        permissionScaffoldingExtrator = new PermissionScaffoldingExtrator()
    }

    void changeConfigField(def fieldScaffolding, FieldScaffold fieldScaffold){
        this.changeStatus(fieldScaffolding, fieldScaffold)
        this.changePermission(fieldScaffolding, fieldScaffold)
        this.changeId(fieldScaffolding, fieldScaffold)
        this.changeIcon(fieldScaffolding, fieldScaffold)
        this.changeLabel(fieldScaffolding, fieldScaffold)
        this.changeInsertable(fieldScaffolding, fieldScaffold)
        this.changeUpdateable(fieldScaffolding, fieldScaffold)
        this.changeDefaultValue(fieldScaffolding, fieldScaffold)
        this.changeScaffold(fieldScaffolding, fieldScaffold)
        this.changeOrder(fieldScaffolding, fieldScaffold)
        constrantsScaffoldingExtrator.chengeConstraintsField(fieldScaffolding, fieldScaffold)
        paramsScaffoldingExtrator.changeTypeAndParamsFields(fieldScaffolding, fieldScaffold)
    }

    def changeScaffold(def fieldScaffolding, FieldScaffold fieldScaffold) {
        def scaffold = fieldScaffolding.get("scaffold")
        if(scaffold !=null){
            fieldScaffold.scaffold = scaffold
        }
    }

    void changeStatus(def fieldScaffolding, FieldScaffold fieldScaffold){
        if(fieldScaffolding instanceof  Boolean){
            fieldScaffold.scaffold = fieldScaffolding
        }else{
            def scaffold = fieldScaffolding.get("status")
            if(scaffold != null){
                fieldScaffold.scaffold = scaffold
            }
        }
    }

    void changePermission(def fieldScaffolding, FieldScaffold fieldScaffold){
        def permissionScaffolding = fieldScaffolding.get("permission")
        if(permissionScaffolding) {
            if(!fieldScaffold.permission) {
                fieldScaffold.permission = new PermissionField(fieldScaffold: fieldScaffold)
            }
            permissionScaffoldingExtrator.changePermissionField(permissionScaffolding, fieldScaffold.permission)
        }
    }

    void changeId(def fieldScaffolding, FieldScaffold fieldScaffold){
        def idValue = fieldScaffolding.get("id")
        if(idValue !=null){
            fieldScaffold.elementId = idValue
        }
    }

    void changeOrder(def fieldScaffolding, FieldScaffold fieldScaffold){
        def orderValue = fieldScaffolding.get("order")
        if(orderValue !=null){
            fieldScaffold.order = orderValue
        }
    }

    void changeIcon(def fieldScaffolding, FieldScaffold fieldScaffold){
        def icon = iconScaffoldingExtrator.getIcon(fieldScaffolding)
        if(icon !=null){
            fieldScaffold.icon = icon
        }
    }

    void changeLabel(def fieldScaffolding, FieldScaffold fieldScaffold){
        def labelValue = fieldScaffolding.get("label")
        if(labelValue !=null){
            fieldScaffold.label = labelValue
        }
    }

    void changeInsertable(def fieldScaffolding, FieldScaffold fieldScaffold){
        def insertable = fieldScaffolding.get("insertable")
        if(insertable !=null){
            fieldScaffold.insertable = insertable
        }
    }

    void changeUpdateable(def fieldScaffolding, FieldScaffold fieldScaffold){
        def updateable = fieldScaffolding.get("updateable")
        if(updateable !=null){
            fieldScaffold.updateable = updateable
        }
    }

    void changeDefaultValue(def fieldScaffolding, FieldScaffold fieldScaffold){
        def defaultValue = fieldScaffolding.get("defaultValue")
        if(defaultValue !=null){
            fieldScaffold.defaultValue = defaultValue
        }
    }



}

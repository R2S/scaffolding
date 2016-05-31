package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.extractor.MetaDomainExtractor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.StatusClassScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.security.PermissionClass
import br.ufscar.sagui.util.ClosureUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 28/07/15.
 */
class ScaffoldingExtractor implements MetaDomainExtractor {

    static fieldsConfigInclude = ["name", "title", "actions", "permission","datatable", "menu", "showTransients", "service", "controller", "buttons"]

    FieldScaffoldingExtractor fieldScaffoldingExtractor
    ActionsScaffoldingExtrator actionsScaffoldingExtrator
    TitleScaffoldingExtrator titleScaffoldingExtrator
    DataTableScaffoldingExtrator dataTableScaffoldingExtrator
    PermissionScaffoldingExtrator permissionScaffoldingExtrator
    MenuScaffoldingExtrator menuScaffoldingExtrator
    ButtonsScaffoldingExtrator buttonsScaffoldingExtrator

    public ScaffoldingExtractor() {
        fieldScaffoldingExtractor = new FieldScaffoldingExtractor()
        actionsScaffoldingExtrator = new ActionsScaffoldingExtrator()
        titleScaffoldingExtrator = new TitleScaffoldingExtrator()
        dataTableScaffoldingExtrator = new DataTableScaffoldingExtrator()
        permissionScaffoldingExtrator = new PermissionScaffoldingExtrator()
        menuScaffoldingExtrator = new MenuScaffoldingExtrator()
        buttonsScaffoldingExtrator = new ButtonsScaffoldingExtrator()
    }

    @Override
    ClassScaffold extractor(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        def closureScaffolding = getClosureScaffoldind(domainClass)

        if (closureScaffolding) {
            if(closureScaffolding instanceof Boolean){
                classScaffold.status = closureScaffolding?StatusClassScaffold.ACTIVE:StatusClassScaffold.DISABLE
            }else{
                def classScaffolding = ClosureUtil.toMapConfig(closureScaffolding, domainClass.clazz, fieldsConfigInclude)
                this.changeName(classScaffolding, classScaffold)
                this.changeTitle(classScaffolding, classScaffold)
                this.actionsScaffoldingExtrator.changeActions(classScaffolding, classScaffold)
                this.changePermission(classScaffolding, classScaffold)
                this.changeDatatable(classScaffolding, classScaffold)
                this.showTransients(classScaffolding, classScaffold)
                this.changeService(classScaffolding, classScaffold)
                this.changeController(classScaffolding, classScaffold)
                this.buttonsScaffoldingExtrator.changeAndInsertButtons(classScaffolding, classScaffold)
                for (FieldScaffold fieldScaffold : classScaffold.fields) {
                    def fieldScaffolding = classScaffolding.get(fieldScaffold.key)

                    if (fieldScaffolding!=null) {
                        fieldScaffold.order = classScaffolding.findIndexOf{it.key == fieldScaffold.key}
                        if(fieldScaffolding instanceof Boolean){
                            fieldScaffold.scaffold = fieldScaffolding
                        }else if(fieldScaffolding instanceof Number){
                            fieldScaffold.order = fieldScaffolding
                        }else if(fieldScaffolding instanceof String){
                            fieldScaffold.label = fieldScaffolding
                        }else{
                            fieldScaffoldingExtractor.changeConfigField(fieldScaffolding, fieldScaffold)
                        }

                    }
                }
                this.changeMenu(classScaffolding, classScaffold)
            }
        }
        return classScaffold
    }

    static getClassScaffoldingConfig(Class clazz){
        def closureScaffolding = getClosureScaffoldind(clazz)
        if(closureScaffolding){
            return ClosureUtil.toMapConfig(closureScaffolding, clazz, fieldsConfigInclude)
        }
    }

    def getClosureScaffoldind(GrailsDomainClass domainClass){
        this.getClosureScaffoldind(domainClass.clazz)
    }

    static getClosureScaffoldind(Class clazz){
        try{
            return clazz.scaffolding
        }catch (e){
            return null
        }
    }


    void changeTitle(def classScaffolding, ClassScaffold classScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(classScaffolding)
        if (titleScaffold) {
            classScaffold.title = titleScaffold
        }
    }

    void changePermission(def actionScaffolding, ClassScaffold classScaffold){
        def permissionScaffolding = actionScaffolding.get("permission")
        if(permissionScaffolding) {
            if(!classScaffold.permission) {
                classScaffold.permission = new PermissionClass(classScaffold: classScaffold)
            }
            permissionScaffoldingExtrator.changePermission(permissionScaffolding, classScaffold.permission)
        }
    }

    void changeName(def classScaffolding, ClassScaffold classScaffold) {
        def value = classScaffolding.get("name")
        if (value) {
            classScaffold.name = value
            classScaffold.datatable?.title?.name = value
        }
    }

    void changeService(def classScaffolding, ClassScaffold classScaffold) {
        def value = classScaffolding.get("service")
        if (value) {
            classScaffold.serviceClass = value
        }
    }

    void changeController(def classScaffolding, ClassScaffold classScaffold) {
        def value = classScaffolding.get("controller")
        if (value) {
            classScaffold.controllerClass = value
        }
    }

    void changeDatatable(def classScaffolding, ClassScaffold classScaffold) {
        def value = classScaffolding.get("datatable")
        if (value) {
            this.dataTableScaffoldingExtrator.changeDatatable(value, classScaffold.datatable)
        }
    }

    void showTransients(def classScaffolding, ClassScaffold classScaffold) {
        def value = classScaffolding.get("showTransients")
        if(value!=null){
            if (value instanceof Boolean&&value==true) {
                try{
                    classScaffold.transiendsShow =  classScaffold.clazz?.transients
                }catch (e){
                    classScaffold.transiendsShow = []
                }
            }else if(value instanceof Collection){
                classScaffold.transiendsShow = value
            }
        }
    }

    void changeMenu(def classScaffolding, ClassScaffold classScaffold){
        menuScaffoldingExtrator.changeMenu(classScaffolding, classScaffold)
    }
}

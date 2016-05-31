package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.scaffolding.extractor.MetaDomainExtractor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.MenuScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionsScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.DatatableScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 28/07/15.
 */
class ClazzExtractor implements MetaDomainExtractor {

    private ActionsClassExtractor actionsBuilder
    private FieldClassExtractor fieldBuilder
    private TitleClassExtractor titleClassBuilder
    private DatatableClassExtrator datatableClassBuilder
    private MenuClassExtrator menuClassExtrator
    private ButtonClassExtrator buttonClassExtrator

    public ClazzExtractor() {
        actionsBuilder = new ActionsClassExtractor()
        fieldBuilder = new FieldClassExtractor()
        titleClassBuilder = new TitleClassExtractor()
        datatableClassBuilder = new DatatableClassExtrator()
        menuClassExtrator = new MenuClassExtrator()
        buttonClassExtrator = new ButtonClassExtrator()
    }

    ClassScaffold extractor(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        if (!classScaffold) {
            classScaffold = new ClassScaffold()
        }
        classScaffold.clazz = domainClass.clazz
        classScaffold.name = this.getName(domainClass)
        classScaffold.title = titleClassBuilder.getTitle(domainClass)
        classScaffold.actions = this.getActions(domainClass, classScaffold)
        classScaffold.fields = this.getFields(domainClass, classScaffold)
        classScaffold.datatable = this.getDatatable(domainClass, classScaffold)
        classScaffold.menu = this.getMenu(domainClass, classScaffold)
        classScaffold.buttons = buttonClassExtrator.getButtons(domainClass, classScaffold)
        return classScaffold
    }

    private DatatableScaffold getDatatable(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        DatatableScaffold datatableScaffold = new DatatableScaffold()
        datatableClassBuilder.initDatatableDefault(datatableScaffold, domainClass)
        datatableScaffold.parent = classScaffold
        return datatableScaffold
    }

    String getName(GrailsDomainClass domainClass) {
        return domainClass.naturalName
    }

    ActionsScaffold getActions(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        return actionsBuilder.getActions(domainClass, classScaffold)
    }

    List<FieldScaffold> getFields(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        return fieldBuilder.getFields(domainClass, classScaffold)
    }

    MenuScaffold getMenu(GrailsDomainClass domainClass, ClassScaffold classScaffold){
        return menuClassExtrator.getMenu(domainClass, classScaffold)
    }
}

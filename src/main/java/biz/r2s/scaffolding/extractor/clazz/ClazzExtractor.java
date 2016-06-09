package  biz.r2s.scaffolding.extractor.clazz;

import java.util.List;

import  biz.r2s.scaffolding.extractor.MetaDomainExtractor;
import  biz.r2s.scaffolding.meta.ClassScaffold;
import  biz.r2s.scaffolding.meta.MenuScaffold;
import  biz.r2s.scaffolding.meta.action.ActionsScaffold;
import  biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import  biz.r2s.scaffolding.meta.field.FieldScaffold;

/**
 * Created by raphael on 28/07/15.
 */
public class ClazzExtractor implements MetaDomainExtractor {

    private ActionsClassExtractor actionsBuilder;
    private FieldClassExtractor fieldBuilder;
    private TitleClassExtractor titleClassBuilder;
    private DatatableClassExtrator datatableClassBuilder;
    private MenuClassExtrator menuClassExtrator;
    private ButtonClassExtrator buttonClassExtrator;

    public ClazzExtractor() {
        actionsBuilder = new ActionsClassExtractor();
        fieldBuilder = new FieldClassExtractor();
        titleClassBuilder = new TitleClassExtractor();
        datatableClassBuilder = new DatatableClassExtrator();
        menuClassExtrator = new MenuClassExtrator();
        buttonClassExtrator = new ButtonClassExtrator();
    }

    public ClassScaffold extractor(Class domainClass, ClassScaffold classScaffold) {
        if (classScaffold==null) {
            classScaffold = new ClassScaffold();
        }
        classScaffold.setClazz(domainClass);
        classScaffold.setName(this.getName(domainClass));
        classScaffold.setTitle(titleClassBuilder.getTitle(domainClass));
        classScaffold.setActions(this.getActions(domainClass, classScaffold));
        classScaffold.setFields(this.getFields(domainClass, classScaffold));
        classScaffold.setDatatable(this.getDatatable(domainClass, classScaffold));
        classScaffold.setMenu(this.getMenu(domainClass, classScaffold));
        classScaffold.setButtons(buttonClassExtrator.getButtons(domainClass, classScaffold));
        return classScaffold;
    }

    private DatatableScaffold getDatatable(Class domainClass, ClassScaffold classScaffold) {
        DatatableScaffold datatableScaffold = new DatatableScaffold();
        datatableClassBuilder.initDatatableDefault(datatableScaffold, domainClass);
        datatableScaffold.setParent(classScaffold);
        return datatableScaffold;
    }

    String getName(Class domainClass) {
        return domainClass.getName();
    }

    ActionsScaffold getActions(Class domainClass, ClassScaffold classScaffold) {
        return actionsBuilder.getActions(domainClass);
    }

    List<FieldScaffold> getFields(Class domainClass, ClassScaffold classScaffold) {
        return fieldBuilder.getFields(domainClass, classScaffold);
    }

    MenuScaffold getMenu(Class domainClass, ClassScaffold classScaffold){
        return menuClassExtrator.getMenu(domainClass);
    }
}

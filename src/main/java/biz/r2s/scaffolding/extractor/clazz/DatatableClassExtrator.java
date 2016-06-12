package  biz.r2s.scaffolding.extractor.clazz;

import  biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import  biz.r2s.scaffolding.meta.TitleScaffold;
import  biz.r2s.scaffolding.meta.datatatable.CampoDatatable;
import  biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import  biz.r2s.scaffolding.meta.datatatable.OrderDatatable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import  biz.r2s.core.util.ClassUtils;
import biz.r2s.core.util.ObjectUtil;

/**
 * Created by raphael on 30/07/15.
 */
public class DatatableClassExtrator {

    static final List<String> FIELDS_EXCLUDE = Arrays.asList("version");

    private TitleClassExtractor titleClassBuilder;

    public DatatableClassExtrator() {
        titleClassBuilder = new TitleClassExtractor();
    }

    public void initDatatableDefault(DatatableScaffold dataTable, Class domainClass) {
    	initDatatableDefault(dataTable,domainClass,null);
    }
    public void initDatatableDefault(DatatableScaffold dataTable, Class domainClass, String propertyHasMany) {
        dataTable.setPagination(true);
        dataTable.setSearchable(isSearchable(domainClass));
        dataTable.setSortable(true);
        dataTable.setOrdenate(true);
        dataTable.setSort("id");
        dataTable.setOrder(OrderDatatable.DESC);
        dataTable.setTitle(this.getTitle(domainClass));
        dataTable.setNumMaxPaginate(10);
        dataTable.setResourceUrlScaffold(ResourceUrlScaffold.builder(domainClass, propertyHasMany));
        dataTable.setColumns(this.getColumns(domainClass, dataTable));

    }

    private TitleScaffold getTitle(Class domainClass) {
        return titleClassBuilder.getTitle(domainClass);
    }


    private boolean isSearchable(Class domainClass) {
        //TODO:SEARCHABLE
    	return false;
    }

    private List<CampoDatatable> getColumns(Class domainClass, DatatableScaffold dataTable) {
        List<CampoDatatable> campos = new java.util.ArrayList();
        int count = 0;
        for(Field field: domainClass.getDeclaredFields()){
            if(validateColumn(field)){
                count++;
                CampoDatatable campo = new CampoDatatable();
                campo.setKey(field.getName());
                campo.setName(field.getName());
                campo.setTitle(field.getName());
                campo.setParent(dataTable);
                campo.setOrder(count);
                campos.add(campo);
            }
        }
        return campos;
    }

    private boolean validateColumn(Field field){
        return !(FIELDS_EXCLUDE.contains(field.getName()));
    }
}

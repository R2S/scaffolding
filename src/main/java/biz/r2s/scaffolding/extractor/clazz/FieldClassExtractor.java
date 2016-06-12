package  biz.r2s.scaffolding.extractor.clazz;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import  biz.r2s.scaffolding.meta.ClassScaffold;
import  biz.r2s.scaffolding.meta.action.ActionsScaffold;
import  biz.r2s.scaffolding.meta.field.FieldScaffold;
import  biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.CheckboxParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.DateParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.FileParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.InputParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.NumberParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.NumberPrecisionParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.ParamsFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.Select2ParamsFieldScaffold;

/**
 * Created by raphael on 28/07/15.
 */
public class FieldClassExtractor {

    private DatatableClassExtrator datatableClassBuilder = new DatatableClassExtrator();

    private ActionsClassExtractor actionsClassBuilder = new ActionsClassExtractor();

    public List<FieldScaffold> getFields(Class domainClass, ClassScaffold classScaffold) {
        List<FieldScaffold> fieldScaffolds = Collections.EMPTY_LIST;
        int count = 0;
        for (Field field: domainClass.getFields()) {
        	count++;
        	fieldScaffolds.add(this.getFieldScaffold(field, classScaffold, count+1000));
        }
        return fieldScaffolds;
    }

    FieldScaffold getFieldScaffold(Field field, ClassScaffold classScaffold, int order) {
        FieldScaffold fieldScaffold = new FieldScaffold();

        Class grailsClass = field.getDeclaringClass();

        fieldScaffold.setKey(field.getName());
        fieldScaffold.setElementId(this.getId(grailsClass, field));
        fieldScaffold.setLabel(field.getName());
        fieldScaffold.setInsertable(true);
        fieldScaffold.setUpdateable(true);
        this.changeTypeAndParams(field, fieldScaffold);
        fieldScaffold.setScaffold(true);
        fieldScaffold.setParent(classScaffold);
        fieldScaffold.setOrder(order);
        fieldScaffold.setClazzType(grailsClass);
        fieldScaffold.setBidirecional(false);
        fieldScaffold.setTransients(false);
        return fieldScaffold;

    }

    String getId(Class grailsClass, Field field) {
        return grailsClass.getName()+"."+field.getName();
    }

    void changeTypeAndParams(Field field, FieldScaffold fieldScaffold) {
        TypeFieldScaffold typeFieldScaffold = TypeFieldScaffold.INPUT;
        ParamsFieldScaffold paramsFieldScaffold = new InputParamsFieldScaffold();
        ((InputParamsFieldScaffold)paramsFieldScaffold).setRequired(true);
        Class clazz = field.getDeclaringClass();
        if (clazz.isAssignableFrom(Number.class)) {
            if (Arrays.asList(double.class, float.class, Double.class, Float.class, BigDecimal.class).contains(clazz)) {
                typeFieldScaffold = TypeFieldScaffold.NUMBER_PRECISION;
                paramsFieldScaffold = new NumberPrecisionParamsFieldScaffold();
                ((NumberPrecisionParamsFieldScaffold)paramsFieldScaffold).setRequired(true);
                ((NumberPrecisionParamsFieldScaffold)paramsFieldScaffold).setPrecision(3);
            } else {
                typeFieldScaffold = TypeFieldScaffold.NUMBER;
                paramsFieldScaffold = new NumberParamsFieldScaffold();
                ((NumberParamsFieldScaffold)paramsFieldScaffold).setRequired(true);

            }
        } else if (clazz.isAssignableFrom(Date.class)) {
            typeFieldScaffold = TypeFieldScaffold.DATE;
            paramsFieldScaffold = new DateParamsFieldScaffold();
            ((DateParamsFieldScaffold) paramsFieldScaffold).setRequired(true);
            ((DateParamsFieldScaffold) paramsFieldScaffold).setDateEmpty(true);
        } else if (clazz.isAssignableFrom(Boolean.class)) {
            typeFieldScaffold = TypeFieldScaffold.CHECKBOX;
            paramsFieldScaffold = new CheckboxParamsFieldScaffold();
            ((InputParamsFieldScaffold) paramsFieldScaffold).setRequired(false);
        } else if (clazz.isAssignableFrom(byte[].class)) {
            typeFieldScaffold = TypeFieldScaffold.FILE;
            paramsFieldScaffold = new FileParamsFieldScaffold();
            ((FileParamsFieldScaffold) paramsFieldScaffold).setRequired(false);
        } else if (field.isEnumConstant()) {
            typeFieldScaffold = TypeFieldScaffold.SELECT2;
            paramsFieldScaffold = new Select2ParamsFieldScaffold();
            ((Select2ParamsFieldScaffold) paramsFieldScaffold).setDataTextField("text");
            ((Select2ParamsFieldScaffold) paramsFieldScaffold).setDataValueField("id");
            List<Object> options = Collections.emptyList();
            for(Enum it:(clazz).values()){
            	Map<String, Object> map = Collections.EMPTY_MAP;
            	map.put("id", it.toString());
            	map.put("text", it.toString());
            	options.add(map);
            }
	        ((Select2ParamsFieldScaffold)paramsFieldScaffold).setOptions(options);
	        ((Select2ParamsFieldScaffold)paramsFieldScaffold).setRequired(true);
        }/*else if(property.referencedDomainClass){
            return this.getTypeAndParamsAssocition(property, fieldScaffold)
        }*/
        paramsFieldScaffold.setParent(fieldScaffold);
        fieldScaffold.setType(typeFieldScaffold);
        fieldScaffold.setParams(paramsFieldScaffold);
    }

    private ActionsScaffold getActions(Class domainClass) {
        return actionsClassBuilder.getActions(domainClass);
    }

    /*private getTypeAndParamsAssocition(GrailsDomainClassProperty property, FieldScaffold fieldScaffold) {
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
    }*/
}

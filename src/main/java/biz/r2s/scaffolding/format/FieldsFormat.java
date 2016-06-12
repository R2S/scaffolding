package biz.r2s.scaffolding.format;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.core.util.ClassUtils;
import biz.r2s.core.util.ObjectUtil;
import biz.r2s.scaffolding.RulesFacade;
import biz.r2s.scaffolding.interceptor.DomainResource;
import biz.r2s.scaffolding.interceptor.DomainScaffoldStore;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.ResourceUrl;
import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.params.ParamsFieldScaffold;
import biz.r2s.scaffolding.security.PermissionFacade;

/**
 * Created by raphael on 11/08/15.
 */
public class FieldsFormat {

    CommonFormat commonFormat;
    PermissionFacade permissionFacade;

    public FieldsFormat(){
        commonFormat = new CommonFormat();
        permissionFacade = new PermissionFacade();
    }

    public Map formatFieldsAndHasMany(Map permission, ClassScaffold classScaffold, List<FieldScaffold> fieldScaffolds, TypeActionScaffold actionScaffold){
    	return formatFieldsAndHasMany(permission, classScaffold, fieldScaffolds, actionScaffold, null);
    }
    public Map formatFieldsAndHasMany(Map permission, ClassScaffold classScaffold, List<FieldScaffold> fieldScaffolds, TypeActionScaffold actionScaffold, Class fatherClass){
        List listFieldsMeta = new java.util.ArrayList();
        List listHasManyMeta = new java.util.ArrayList();
        if(actionScaffold == TypeActionScaffold.CREATE){
        	Map<FieldScaffold, Boolean> fieldsPermission = RulesFacade.getInstance().getFieldsCreate(permission, fieldScaffolds);
        	for(FieldScaffold field: fieldsPermission.keySet()){
        		boolean readonly = fieldsPermission.get(field);
        		addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, fatherClass, field, readonly);
        	}
        }else if(actionScaffold == TypeActionScaffold.EDIT){
        	Map<FieldScaffold, Boolean> fieldsPermission = RulesFacade.getInstance().getFieldsEdit(permission, fieldScaffolds);
        	for(FieldScaffold field: fieldsPermission.keySet()){
        		boolean readonly = fieldsPermission.get(field);
        		addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, fatherClass, field, readonly);
        	}        	
        }else if(actionScaffold == TypeActionScaffold.VIEW){
        	List<FieldScaffold> fieldsPermission = RulesFacade.getInstance().getFieldsShow(permission, fieldScaffolds);
        	for(FieldScaffold field: fieldsPermission){
        		addFieldsAndHasMany(listFieldsMeta, listHasManyMeta, fatherClass, field, true);
        	}
        }
        Map meta = new java.util.HashMap();
        meta.put("fields", listFieldsMeta);
        meta.put("hasMany", listHasManyMeta);        
        return meta;
    }

    void addFieldsAndHasMany(List listFieldsMeta, List listHasManyMeta, Class fatherClass, FieldScaffold field, boolean readonly){
        if(field.isTypeHasMany()){
            formatFieldsHasMany(listHasManyMeta, field, readonly);
        }else{
            if(fatherClass==null||!RulesFacade.getInstance().isBidirecional(field, fatherClass)){
                addField(listFieldsMeta, field, readonly);
            }
        }
    }

    void formatFieldsHasMany(List listFieldsMeta, FieldScaffold field, boolean readonly){
        Field field2 = ObjectUtil.getField(field.getKey(), field.getParent().getClazz());
        String name = field.getLabel();
        String url = null;
        String key = null;
        if(field2!=null){
            ResourceUrl resourceUrl = ResourceUrlScaffold.builder(field2.getDeclaringClass(), field.getKey()).resolver(TypeActionScaffold.LIST);
            url = commonFormat.tratarUrl((String) resourceUrl.formatUrl().get("path"));
            key = DomainScaffoldStore.getKey(field2.getDeclaringClass());
        }
        listFieldsMeta.add(DomainResource.formatMenu(key, name, url, null, null, null));
    }

    void addField(List listFieldsMeta, FieldScaffold field, boolean readonly){
        Map meta = this.formatField(field);
        meta.put("readonly",readonly);
        listFieldsMeta.add(meta);
    }

    Map formatField(FieldScaffold fieldScaffold){
    	Map meta = new java.util.HashMap();
        meta.put("key",fieldScaffold.getKey());
        meta.put("label",fieldScaffold.getLabel());
        meta.put("id",fieldScaffold.getElementId());
        //meta.constraints = this.formatConstraints(fieldScaffold.constraints)
        meta.put("type",fieldScaffold.getParams().getType().name());
        Map params = this.formatParams(fieldScaffold.getParams());
        params.put("required",fieldScaffold.isMandatory());
        meta.put("params",params);
        if(fieldScaffold.getIcon()!=null){
            meta.put("icon",commonFormat.formatIcon(fieldScaffold.getIcon()));
        }
        meta.put("defaultValue",fieldScaffold.getDefaultValue());
        return meta;
    }

    Map formatParams(ParamsFieldScaffold params){
        return params.format();
    }
}
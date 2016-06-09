package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Map;

import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.security.PermissionField;

/**
 * Created by raphael on 06/08/15.
 */
public class FieldScaffoldingExtractor {

    IconScaffoldingExtrator iconScaffoldingExtrator;

    ParamsScaffoldingExtrator paramsScaffoldingExtrator;
    PermissionScaffoldingExtrator permissionScaffoldingExtrator;

    public FieldScaffoldingExtractor(){
        iconScaffoldingExtrator = new IconScaffoldingExtrator();        
        paramsScaffoldingExtrator = new ParamsScaffoldingExtrator();
        permissionScaffoldingExtrator = new PermissionScaffoldingExtrator();
    }

    void changeConfigField(Map fieldScaffolding, FieldScaffold fieldScaffold) throws Exception{
        this.changeStatus(fieldScaffolding, fieldScaffold);
        this.changePermission(fieldScaffolding, fieldScaffold);
        this.changeId(fieldScaffolding, fieldScaffold);
        this.changeIcon(fieldScaffolding, fieldScaffold);
        this.changeLabel(fieldScaffolding, fieldScaffold);
        this.changeInsertable(fieldScaffolding, fieldScaffold);
        this.changeUpdateable(fieldScaffolding, fieldScaffold);
        this.changeDefaultValue(fieldScaffolding, fieldScaffold);
        this.changeScaffold(fieldScaffolding, fieldScaffold);
        this.changeOrder(fieldScaffolding, fieldScaffold);
        paramsScaffoldingExtrator.changeTypeAndParamsFields(fieldScaffolding, fieldScaffold);
    }

    void changeScaffold(Map fieldScaffolding, FieldScaffold fieldScaffold) {
        Boolean scaffold = (Boolean) fieldScaffolding.get("scaffold");
        if(scaffold !=null){
            fieldScaffold.setScaffold(scaffold);
        }
    }

    void changeStatus(Map fieldScaffolding, FieldScaffold fieldScaffold){
    	Boolean scaffold = (Boolean) fieldScaffolding.get("status");
        		 if(scaffold !=null){
        	            fieldScaffold.setScaffold(scaffold);
        	        }
    }

    void changePermission(Map fieldScaffolding, FieldScaffold fieldScaffold){
        Map permissionScaffolding = (Map) fieldScaffolding.get("permission");
        if(permissionScaffolding!=null) {
            if(fieldScaffold.getPermission()==null) {
            	PermissionField permissionField = new PermissionField();
            	permissionField.setFieldScaffold(fieldScaffold);            	
                fieldScaffold.setPermission(permissionField);
            }
            permissionScaffoldingExtrator.changePermissionField(permissionScaffolding, fieldScaffold.getPermission());
        }
    }

    void changeId(Map fieldScaffolding, FieldScaffold fieldScaffold){
        String idValue = (String) fieldScaffolding.get("id");
        if(idValue !=null){
            fieldScaffold.setElementId(idValue);
        }
    }

    void changeOrder(Map fieldScaffolding, FieldScaffold fieldScaffold){
        Integer orderValue = (Integer) fieldScaffolding.get("order");
        if(orderValue !=null){
            fieldScaffold.setOrder(orderValue);
        }
    }

    void changeIcon(Map fieldScaffolding, FieldScaffold fieldScaffold){
        IconScaffold icon = iconScaffoldingExtrator.getIcon(fieldScaffolding);
        if(icon !=null){
            fieldScaffold.setIcon(icon);
        }
    }

    void changeLabel(Map fieldScaffolding, FieldScaffold fieldScaffold){
        String labelValue = (String) fieldScaffolding.get("label");
        if(labelValue !=null){
            fieldScaffold.setLabel(labelValue); 
        }
    }

    void changeInsertable(Map fieldScaffolding, FieldScaffold fieldScaffold){
        Boolean insertable = (Boolean) fieldScaffolding.get("insertable");
        if(insertable !=null){
            fieldScaffold.setInsertable(insertable);
        }
    }

    void changeUpdateable(Map fieldScaffolding, FieldScaffold fieldScaffold){
    	Boolean updateable = (Boolean) fieldScaffolding.get("updateable");
        if(updateable !=null){
            fieldScaffold.setUpdateable(updateable);
        }
    }

    void changeDefaultValue(Map fieldScaffolding, FieldScaffold fieldScaffold){
    	Boolean defaultValue = (Boolean) fieldScaffolding.get("defaultValue");
        if(defaultValue !=null){
            fieldScaffold.setDefaultValue(defaultValue);
        }
    }



}

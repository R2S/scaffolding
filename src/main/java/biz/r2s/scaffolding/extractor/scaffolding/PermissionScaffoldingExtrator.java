package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.security.Permission;
import biz.r2s.scaffolding.meta.security.PermissionField;

/**
 * Created by raphael on 26/08/15.
 */
class PermissionScaffoldingExtrator {

    List<String> actionsField = Arrays.asList("create", "edit", "show");

    void changePermissionField(Map<String,Object> permissionScaffolding, PermissionField permissionField){
        if(permissionScaffolding!=null){
            if(permissionScaffolding instanceof Map){
            	for(String key: permissionScaffolding.keySet()){
            		Object value = permissionScaffolding.get(key);
            		if(actionsField.contains(key)){
                        TypeActionScaffold  typeActionScaffold =  getActionPermission(key);
                        List<String> roles = getRoles(value);
                        permissionField.getActionRoles().put(typeActionScaffold, roles);
                    }
            		
            	}
            }
            this.changePermission(permissionScaffolding, permissionField);
        }
    }

    private TypeActionScaffold getActionPermission(String key) {
        TypeActionScaffold typeActionScaffold = null;
        switch (key) {
            case "create":
                typeActionScaffold = TypeActionScaffold.CREATE;
                break;
            case "edit":
                typeActionScaffold = TypeActionScaffold.EDIT;
                break;
            case "show":
                typeActionScaffold = TypeActionScaffold.VIEW;
                break;
        }
        return typeActionScaffold;
    }


    void changePermission(Map<String, Object> permissionScaffolding, Permission permission){
        if(permissionScaffolding != null){
            if(permissionScaffolding instanceof Map){
                Object permissionRolesScaffolding = permissionScaffolding.get("roles");
                if(permissionRolesScaffolding != null){
                    permission.setRoles(this.getRoles(permissionRolesScaffolding));
                }                
            }else{
                permission.setRoles(this.getRoles(permissionScaffolding));                
            }
        }
    }

    List<String> getRoles(Object permissionRolesScaffolding){
    	List<String> roles = new java.util.ArrayList();
        if(permissionRolesScaffolding instanceof String){
            roles.add((String) permissionRolesScaffolding);
        }else if(permissionRolesScaffolding instanceof List){
            roles.addAll((Collection<? extends String>) permissionRolesScaffolding);
        }
        return roles;
    }
}

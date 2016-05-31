package biz.r2s.scaffolding.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.RulesFacade;
import biz.r2s.scaffolding.interceptor.DomainResource;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.button.Button;

/**
 * Created by raphael on 02/09/15.
 */
public class PermissionFacade {

    RolesFacade rolesFacade;

    public PermissionFacade(){
        rolesFacade = new RolesFacade();
    }
    public Map<String, Object> getPermissionScaffold(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields){
    	return this.getPermissionScaffold(classScaffold, actions, fields, null);
    }
    
    public Map<String, Object> getPermissionScaffold(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields, List<Button> buttons) {
    	Map<String, Object> metaRolesClass = rolesFacade.getMetaRoles(classScaffold);
        Map<String, Object>  permission = Collections.emptyMap();
        permission.put("fields", getPermissionScaffoldField(metaRolesClass, actions, fields));
        permission.put("actions", getPermissionScaffoldAction(metaRolesClass, actions));
        permission.put("buttons", getPermissionScaffoldButton(metaRolesClass, buttons));
        return permission;
    }

    public Map<String, Object>  getPermissionScaffoldField(
    		Map<String, Object> metaRolesClass, List<TypeActionScaffold> actions, List<String> fields) {
    	Map<String, Object> permissionFields = Collections.emptyMap();
    	Map<String, Object> fieldsMap = (Map<String, Object>) metaRolesClass.get("fields");
        for(String key:fieldsMap.keySet()){
        	if((fields!=null && fields.contains(key)) || fields==null){
        		Map<String, Object>  metaField = Collections.emptyMap();
        		List<TypeActionScaffold> listActions = Arrays.asList(TypeActionScaffold.CREATE, TypeActionScaffold.EDIT, TypeActionScaffold.VIEW, TypeActionScaffold.LIST);
        		if (actions!=null) {
                    listActions = actions;
                }
        		for(TypeActionScaffold action:listActions){
        			Map<String, Object> value = (Map<String, Object>) fieldsMap.get(key);
        			Map<String, Object> actionRoles = (Map<String, Object>) value.get("actionRoles");
        			metaField.put(action, isPermission((Map<String, Object>) actionRoles.get(action)));
        		}
        		permissionFields.put(key, metaField);
         
        	}
        }
        return permissionFields;
    }

    public Map<String, Object> getPermissionScaffoldAction(Map<String, Object> metaRolesClass, List<TypeActionScaffold> actions) {
    	Map<String, Object> permissionAction = Collections.emptyMap();
    	Map<String, Object> actionsMap = (Map<String, Object>) metaRolesClass.get("actions");
        for(String key:actionsMap.keySet()){
        	if ((actions!=null && (actions.contains(key)))
                    || actions==null) {
        		Map<String, Object> value = (Map<String, Object>) actionsMap.get(key);
        		permissionAction.put(key, isPermission((Map<String, Object>) value
        				.get("roles")));
        		
        	}
        }
        return permissionAction;
    }

    public Map<String, Object> getPermissionScaffoldButton(Map<String, Object> metaRolesClass, List<Button> buttons) {
    	Map<String, Object> permissionButton = Collections.emptyMap();
    	Map<String, Object> buttonsMap = (Map<String, Object>) metaRolesClass.get("buttons");
    	 for(String key:buttonsMap.keySet()){
         	if ((buttons!=null && (buttons.contains(key)))
                     || buttons==null) {
         		permissionButton.put(key, isPermission((Map<String, Object>) buttonsMap.get(key)));
         	}    	
        }
        return permissionButton;

    }

    public boolean isPermission(Map<String, Object> roles) {
        if(!RulesFacade.instance.enablePermission()){
            return true;
        }        
        //TODO: pegar bean de segurança
        return true;
    }

    public boolean isPermissionDomainResource(DomainResource domainResource){
        if(domainResource.isHasMamy()){
            return true;
        }
        return isPermission(domainResource.getRoles());
    }

    public boolean hasPermissionScaffold(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields) {
    	Map<String, Object> metaPermissions = getPermissionScaffold(classScaffold, actions, fields, null);

        boolean fieldPermission = true, actionPermission = true;
        
        Map<String, Object> fieldsMap = (Map<String, Object>) metaPermissions.get("fields");
        Map<String, Object> actionsMap = (Map<String, Object>) metaPermissions.get("actions");
        
        for(String key:fieldsMap.keySet()){
        	Map<TypeActionScaffold, Boolean> value = (Map<TypeActionScaffold, Boolean>) fieldsMap.get(key);
        	 for(TypeActionScaffold actionScaffold:value.keySet()){
        		 if (!value.get(actionScaffold)) {
                     fieldPermission = false;
                 }   		 
        	 }
        }
        
        for(String key:actionsMap.keySet()){
        	Boolean value = (Boolean) actionsMap.get(key);
        	if (!value) {
        		actionPermission = false;
        	 }
        }

        return fields!=null ? (fieldPermission && actionPermission) : actionPermission;
    }
}

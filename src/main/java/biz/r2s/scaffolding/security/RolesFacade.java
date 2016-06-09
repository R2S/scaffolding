package biz.r2s.scaffolding.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.action.ActionScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.button.Button;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.security.PermissionField;

/**
 * Created by raphael on 02/09/15.
 */
public class RolesFacade {
	private Map<TypeActionScaffold, List<String>> actionMethods = Collections.emptyMap();

	private Map<TypeActionScaffold, String> actionPermissionDefaults = Collections.emptyMap();

	public RolesFacade() {

		actionMethods.put(TypeActionScaffold.CREATE, Arrays.asList("save"));
		actionMethods.put(TypeActionScaffold.EDIT, Arrays.asList("update"));
		actionMethods.put(TypeActionScaffold.VIEW, Arrays.asList("show"));
		actionMethods.put(TypeActionScaffold.DELETE, Arrays.asList("delete"));
		actionMethods.put(TypeActionScaffold.LIST, Arrays.asList("index", "ascendentes", "descendentes", "children"));

		actionPermissionDefaults.put(TypeActionScaffold.CREATE, "CREATE");
		actionPermissionDefaults.put(TypeActionScaffold.EDIT, "EDIT");
		actionPermissionDefaults.put(TypeActionScaffold.VIEW, "VIEW");
		actionPermissionDefaults.put(TypeActionScaffold.DELETE, "DELETE");
		actionPermissionDefaults.put(TypeActionScaffold.LIST, "LIST");
	}

	public TypeActionScaffold getTypeActionByActionName(String actionName) {
		TypeActionScaffold actionScaffold = null;
		for (TypeActionScaffold key : actionMethods.keySet()) {
			if (actionMethods.get(key).contains(actionName)) {
				actionScaffold = key;
			}
		}

		return actionScaffold;
	}

	public Map<String, Object> getRolesDefault(Class domain) {
		Map<String, Object> roles = Collections.emptyMap();
		for(List<String> methods:actionMethods.values()){
			for(String method:methods){
				roles.put(method, this.getRoleDefault(domain, method));
			}			
		}
        return roles;
    }

	public String getRoleDefault(Class domain, String action) {
        String actionName = this.formatNameAction(action);
        return this.getRoleDefaultByName(domain, actionName);
    }

	public String getRoleDefault(Class domain, TypeActionScaffold typeActionScaffold) {
        String actionName = actionPermissionDefaults.get(typeActionScaffold);
        return this.getRoleDefaultByName(domain, actionName);
    }

	public String getRoleDefaultByName(Class domain, String actionName) {
        String moduloName = this.getModulo(domain);
        String domainName = this.formatDomainName(domain);
        String role = "ROLE_"+moduloName+"_"+domainName+"_"+actionName;
        return role.toUpperCase();
    }

	public String getModulo(Class domain) {
        return "scaffolding";
    }

	public String formatNameAction(String action) {
		TypeActionScaffold tas = null;
		for(TypeActionScaffold typeActionScaffold: this.actionMethods.keySet()){
			if(this.actionMethods.get(typeActionScaffold).contains(action)){
				tas = typeActionScaffold;
				break;
			}
		}
		
		if (tas!=null) {
            return this.actionPermissionDefaults.get(tas);
        } else {
            return action;
        }
    }

	public String formatDomainName(Class domain) {
        return domain.getSimpleName();
    }

	public List<String> getActions(TypeActionScaffold actionScaffold) {
        return actionMethods.get(actionScaffold);
    }

	public List<String> getRolesUsuario() {
		// TODO: BUSCAR USUARIO DO BEAN

		return null;
	}

	public Map<String, Object> getMetaRoles(ClassScaffold classScaffold) {
		Map<String, Object> meta = Collections.emptyMap();
        meta.putAll(this.getMetaRolesClass(classScaffold));
        meta.put("actions", this.getMetaRolesActions(classScaffold));
        meta.put("fields", this.getMetaRolesFields(classScaffold));
        meta.put("buttons", this.getMetaRolesButtons(classScaffold));
        return meta;
    }

	public Map<String, Object> getMetaRolesClass(ClassScaffold classScaffold) {
		Map<String, Object> obj = Collections.emptyMap();
		obj.put("roles", this.getRolesClass(classScaffold, null));
        return obj;
    }

	public Map<String, Object> getMetaRolesActions(ClassScaffold classScaffold) {
		Map<String, Object> meta = Collections.emptyMap();
        if (classScaffold!=null) {
            changeMetaRolesAction(meta, TypeActionScaffold.CREATE, classScaffold);
            changeMetaRolesAction(meta, TypeActionScaffold.EDIT, classScaffold);
            changeMetaRolesAction(meta, TypeActionScaffold.DELETE, classScaffold);
            changeMetaRolesAction(meta, TypeActionScaffold.LIST, classScaffold);
            changeMetaRolesAction(meta, TypeActionScaffold.VIEW, classScaffold);
        }
        return meta;
    }

	public void changeMetaRolesAction(Map<String, Object> meta, TypeActionScaffold typeActionScaffold, ClassScaffold classScaffold) {

        ActionScaffold actionScaffold = classScaffold.getActions().getAction(typeActionScaffold);
        Map<String, Object> metaAction = Collections.emptyMap();
        List<String> roles = Collections.emptyList();
        roles.addAll(this.getRolesAction(actionScaffold, typeActionScaffold, classScaffold));
        metaAction.put("roles", roles);        
        meta.put(typeActionScaffold.toString(), metaAction);
    }

	public Map<String, Object> getMetaRolesButtons(ClassScaffold classScaffold) {
		Map<String, Object> meta = Collections.emptyMap();
		for(Button button:classScaffold.getButtons()){
			meta.put(button.getName(), getMetaRolesButton(classScaffold, button));
		}
        return meta;
    }

	public List<String> getMetaRolesButton(ClassScaffold classScaffold, Button button) {
		List<String> roles = Collections.emptyList();
        if (button.getPermission()!=null && button.getPermission().getRoles()!=null) {
            roles = button.getPermission().getRoles();
        } else {
            roles = getRolesAction(classScaffold, button.getActionScaffold());
        }
        return roles;
    }

	public Map<String, Object> getMetaRolesFields(ClassScaffold classScaffold) {
		Map<String, Object> meta = Collections.emptyMap();
        for(FieldScaffold field:classScaffold.getFields()){
        	meta.put(field.getKey(), getMetaRolesField(classScaffold, field.getPermission()));
        }
        return meta;
    }

	public Map<String, Object> getMetaRolesField(ClassScaffold classScaffold, PermissionField permissionField) {
		Map<String, Object> meta = Collections.emptyMap();
		boolean acl = false;
		List<String> roles = Collections.emptyList();
		Map<String, Object> actionRoles = Collections.emptyMap();
		
        if (permissionField!=null) {
        	roles.addAll(permissionField.getRoles());
        }

        actionRoles.put(TypeActionScaffold.CREATE.name(), getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.CREATE));
        actionRoles.put(TypeActionScaffold.EDIT.name(), getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.EDIT));
        actionRoles.put(TypeActionScaffold.VIEW.name(), getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.VIEW));
        actionRoles.put(TypeActionScaffold.LIST.name(), getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.LIST));
        meta.put("roles", roles);
        meta.put("actionRoles", actionRoles);
        
        return meta;

    }

	public List<String> getRolesFieldAction(ClassScaffold classScaffold, PermissionField field, TypeActionScaffold typeActionScaffold) {
		List<String> roles = Collections.emptyList();
        List<String> roleAction = field.getActionRoles().get(typeActionScaffold);
        if (roleAction!=null) {
            roles = roleAction;
        } else {
            roles = getRolesField(classScaffold, field, typeActionScaffold);
        }
        return roles;
    }

	public List<String> getRolesField(ClassScaffold classScaffold, PermissionField field, TypeActionScaffold typeActionScaffold) {
		List<String> roles = Collections.emptyList();
        if (field.getRoles()!=null) {
            roles = field.getRoles();
        } else {
            roles = getRolesAction(classScaffold, typeActionScaffold);
        }
        return roles;
    }

	public List<String> getRolesAction(ClassScaffold classScaffold, TypeActionScaffold typeActionScaffold) {
        return getRolesAction(classScaffold.getActions().getAction(typeActionScaffold), typeActionScaffold, classScaffold);
    }

	public List<String> getRolesAction(ActionScaffold actionScaffold, TypeActionScaffold typeActionScaffold, ClassScaffold classScaffold) {
		List<String> roles = Collections.emptyList();
        if (actionScaffold.getPermission().getRoles()!=null) {
            roles = actionScaffold.getPermission().getRoles();
        } else {
            roles = getRolesClass(classScaffold, typeActionScaffold);
        }
        return roles;
    }

	public List<String> getRolesClass(ClassScaffold classScaffold, TypeActionScaffold typeActionScaffold) {
		List<String> roles = Collections.emptyList();
        if (classScaffold.getPermission().getRoles()!=null) {
            roles = classScaffold.getPermission().getRoles();
        } else if (typeActionScaffold!=null) {
            roles.add(getRoleDefault(classScaffold.getClazz(), typeActionScaffold));
        } else{
            for(TypeActionScaffold typeActionScaffold2:TypeActionScaffold.values()){
                roles.add(getRoleDefault(classScaffold.getClazz(), typeActionScaffold2));
            }
        }
        return roles;
    }
}

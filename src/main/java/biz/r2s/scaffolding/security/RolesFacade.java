package biz.r2s.scaffolding.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.action.ActionScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.button.Button;
import biz.r2s.scaffolding.meta.security.PermissionField;

/**
 * Created by raphael on 02/09/15.
 */
class RolesFacade {
	private Map<TypeActionScaffold, List<String>> actionMethods = Collections.emptyMap();

	private Map actionPermissionDefaults = Collections.emptyMap();

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

	TypeActionScaffold getTypeActionByActionName(String actionName) {
		TypeActionScaffold actionScaffold = null;
		for (TypeActionScaffold key : actionMethods.keySet()) {
			if (actionMethods.get(key).contains(actionName)) {
				actionScaffold = key;
			}
		}

		return actionScaffold;
	}

	def getRolesDefault(Class domain) {
        def roles = [:]
        actionMethods.values().flatten().each { String method ->
            roles.put(method, this.getRoleDefault(domain, method))
        }
        return roles
    }

	def getRoleDefault(Class domain, String action) {
        String actionName = this.formatNameAction(action)
        this.getRoleDefaultByName(domain, actionName)
    }

	def getRoleDefault(Class domain, TypeActionScaffold typeActionScaffold) {
        String actionName = actionPermissionDefaults.get(typeActionScaffold)
        this.getRoleDefaultByName(domain, actionName)
    }

	def getRoleDefaultByName(Class domain, String actionName) {
        String moduloName = this.getModulo(domain)
        String domainName = this.formatDomainName(domain)

        return "ROLE_${moduloName}_${domainName}_${actionName}".toUpperCase()
    }

	def getModulo(Class domain) {
        GrailsUtil.getNameModulo(domain)
    }

	def formatNameAction(String action) {
        def tas = this.actionMethods.get(action)
        if (tas) {
            return this.actionPermissionDefaults.get(tas)
        } else {
            return action
        }
    }

	def formatDomainName(Class domain) {
        return domain.simpleName
    }

	public List<String> getActions(TypeActionScaffold actionScaffold) {
        return actionMethods.get(actionScaffold)
    }

	public List<String> getRolesUsuario() {
		// TODO: BUSCAR USUARIO DO BEAN

		return null;
	}

	def getMetaRoles(ClassScaffold classScaffold) {
        def meta = [:]
        meta.putAll(this.getMetaRolesClass(classScaffold))
        meta.actions = this.getMetaRolesActions(classScaffold)
        meta.fields = this.getMetaRolesFields(classScaffold)
        meta.buttons = this.getMetaRolesButtons(classScaffold)
        return meta
    }

	def getMetaRolesClass(ClassScaffold classScaffold) {
        def obj = [acl: classScaffold.permission?.acl!=null?:false]
        obj.roles = this.getRolesClass(classScaffold, null)
        return obj
    }

	def getMetaRolesActions(ClassScaffold classScaffold) {
        def meta = [:]
        if (classScaffold) {
            changeMetaRolesAction(meta, TypeActionScaffold.CREATE, classScaffold)
            changeMetaRolesAction(meta, TypeActionScaffold.EDIT, classScaffold)
            changeMetaRolesAction(meta, TypeActionScaffold.DELETE, classScaffold)
            changeMetaRolesAction(meta, TypeActionScaffold.LIST, classScaffold)
            changeMetaRolesAction(meta, TypeActionScaffold.VIEW, classScaffold)
        }
        return meta
    }

	void changeMetaRolesAction(def meta, TypeActionScaffold typeActionScaffold, ClassScaffold classScaffold) {

        ActionScaffold actionScaffold = classScaffold.actions?.getAction(typeActionScaffold)
        def metaAction = [
                acl  : actionScaffold?.permission?.acl ?: false,
                roles: []]
        metaAction.roles << this.getRolesAction(actionScaffold, typeActionScaffold, classScaffold)

        meta.put typeActionScaffold, metaAction
    }

	def getMetaRolesButtons(ClassScaffold classScaffold) {
        def meta = [:]
        classScaffold.buttons?.each { Button button ->
            meta.put button.name, getMetaRolesButton(classScaffold, button)
        }
        return meta
    }

	def getMetaRolesButton(ClassScaffold classScaffold, Button button) {
        def roles = []
        if (button?.permission?.roles) {
            roles = button.permission.roles
        } else {
            roles = getRolesAction(classScaffold, button.actionScaffold)
        }
        return roles
    }

	def getMetaRolesFields(ClassScaffold classScaffold) {
        def meta = [:]
        classScaffold.fields?.each {
            meta.put it.key, getMetaRolesField(classScaffold, it.permission)
        }
        return meta
    }

	def getMetaRolesField(ClassScaffold classScaffold, PermissionField permissionField) {
        def meta = [acl: false, roles: [], actionRoles: [:]]

        if (permissionField) {
            meta.roles = permissionField.roles
            meta.acl = permissionField.acl
        }

        meta.actionRoles.put(TypeActionScaffold.CREATE, getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.CREATE))
        meta.actionRoles.put(TypeActionScaffold.EDIT, getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.EDIT))
        meta.actionRoles.put(TypeActionScaffold.VIEW, getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.VIEW))
        meta.actionRoles.put(TypeActionScaffold.LIST, getRolesFieldAction(classScaffold, permissionField, TypeActionScaffold.LIST))
        return meta

    }

	def getRolesFieldAction(ClassScaffold classScaffold, PermissionField field, TypeActionScaffold typeActionScaffold) {
        def roles = []
        def roleAction = field?.actionRoles?.get(typeActionScaffold)
        if (roleAction) {
            roles = roleAction
        } else {
            roles = getRolesField(classScaffold, field, typeActionScaffold)
        }
        return roles
    }

	def getRolesField(ClassScaffold classScaffold, PermissionField field, TypeActionScaffold typeActionScaffold) {
        def roles = []
        if (field?.roles) {
            roles = field.roles
        } else {
            roles = getRolesAction(classScaffold, typeActionScaffold)
        }
        return roles
    }

	def getRolesAction(ClassScaffold classScaffold, TypeActionScaffold typeActionScaffold) {
        getRolesAction(classScaffold.actions?.getAction(typeActionScaffold), typeActionScaffold, classScaffold)
    }

	def getRolesAction(ActionScaffold actionScaffold, TypeActionScaffold typeActionScaffold, ClassScaffold classScaffold) {
        def roles = []
        if (actionScaffold?.permission?.roles) {
            roles = actionScaffold?.permission?.roles
        } else {
            roles = getRolesClass(classScaffold, typeActionScaffold)
        }
        return roles
    }

	def getRolesClass(ClassScaffold classScaffold, TypeActionScaffold typeActionScaffold) {
        def roles = []
        if (classScaffold.permission?.roles) {
            roles = classScaffold.permission.roles
        } else if (typeActionScaffold) {
            roles = getRoleDefault(classScaffold.clazz, typeActionScaffold)
        } else{
            TypeActionScaffold.values().each {
                roles << getRoleDefault(classScaffold.clazz, it)
            }
        }
        return roles
    }
}

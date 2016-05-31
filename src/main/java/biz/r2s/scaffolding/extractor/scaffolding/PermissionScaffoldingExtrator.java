package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.security.Permission
import br.ufscar.sagui.scaffolding.meta.security.PermissionField

/**
 * Created by raphael on 26/08/15.
 */
class PermissionScaffoldingExtrator {

    def actionsField = ["create", "edit", "show"]

    void changePermissionField(def permissionScaffolding, PermissionField permissionField){
        if(permissionScaffolding){
            if(permissionScaffolding instanceof Map){
                permissionScaffolding.each {key, value ->
                    if(key in actionsField){
                        TypeActionScaffold  typeActionScaffold =  getActionPermission(key)
                        List<String> roles = getRoles(value)
                        permissionField.actionRoles.put(typeActionScaffold, roles)
                    }
                }
            }
            this.changePermission(permissionScaffolding, permissionField)
        }
    }

    private TypeActionScaffold getActionPermission(key) {
        TypeActionScaffold typeActionScaffold
        switch (key) {
            case "create":
                typeActionScaffold = TypeActionScaffold.CREATE
                break;
            case "edit":
                typeActionScaffold = TypeActionScaffold.EDIT
                break;
            case "show":
                typeActionScaffold = TypeActionScaffold.VIEW
                break;
        }
        return typeActionScaffold
    }


    void changePermission(def permissionScaffolding, Permission permission){
        if(permissionScaffolding){
            if(permissionScaffolding instanceof Map){
                def permissionRolesScaffolding = permissionScaffolding.get("roles")
                if(permissionRolesScaffolding){
                    permission.roles = this.getRoles(permissionRolesScaffolding)
                }
                def permissionAclScaffolding = permissionScaffolding.get("acl")
                if(permissionAclScaffolding){
                    permission.acl = permissionAclScaffolding
                }
            }else{
                permission.roles = this.getRoles(permissionScaffolding)
                permission.acl = false
            }
        }
    }

    List getRoles(def permissionRolesScaffolding){
        def roles = []
        if(permissionRolesScaffolding instanceof String){
            roles << permissionRolesScaffolding
        }else if(permissionRolesScaffolding instanceof List){
            roles.addAll(permissionRolesScaffolding)
        }
        return roles
    }
}

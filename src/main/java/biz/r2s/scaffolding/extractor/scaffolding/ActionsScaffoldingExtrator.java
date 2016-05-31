package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.extractor.clazz.ActionsClassExtractor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionsScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.security.PermissionAction

/**
 * Created by raphael on 06/08/15.
 */
class ActionsScaffoldingExtrator {
    TitleScaffoldingExtrator titleScaffoldingExtrator
    PermissionScaffoldingExtrator permissionScaffoldingExtrator

    public ActionsScaffoldingExtrator() {
        titleScaffoldingExtrator = new TitleScaffoldingExtrator()
        permissionScaffoldingExtrator = new PermissionScaffoldingExtrator()
    }

    void changeActions(def classScaffolding, ClassScaffold classScaffold) {
        changeActions(classScaffolding, classScaffold.actions)
    }

    void changeActions(def classScaffolding, ActionsScaffold actionsScaffold) {
        def actionsValue = classScaffolding.get("actions")
        if (actionsValue) {
            if (actionsValue instanceof List) {
                actionsValue.each { actionName ->
                    ActionScaffold actionScaffold = actionsScaffold."$actionName"
                    if (actionScaffold) {
                        actionScaffold.parent = actionsScaffold
                        actionsScaffold."$actionName" = actionScaffold
                    }
                }
            } else if (actionsValue instanceof Map) {
                actionsValue.each { actionName, value ->
                    ActionScaffold actionScaffold = actionsScaffold."$actionName"
                    if (value instanceof String) {
                        TitleScaffold titleScaffold = new TitleScaffold()
                        titleScaffold.name = value
                        actionScaffold.title = titleScaffold
                    }else
                    if (value instanceof Boolean ) {
                        actionScaffold.enabled = value
                    } else if (value instanceof Map) {
                        this.changeTitle(value, actionScaffold)
                        this.changePermission(value, actionScaffold)
                        this.changeEnabled(value, actionScaffold)
                    }
                    if (actionScaffold) {
                        actionScaffold.parent = actionsScaffold
                        actionsScaffold."$actionName" = actionScaffold
                    }
                }
            }
        }
    }

    ActionScaffold getActionDefault(String actionName, ActionsScaffold actionsScaffold) {
        ActionScaffold actionScaffold = null
        ActionsClassExtractor actionsClassExtractor = new ActionsClassExtractor()
        TypeActionScaffold typeActionScaffold = actionsClassExtractor.actionsToName.find { it.value == actionName }?.key
        if (typeActionScaffold) {
            actionScaffold = actionsClassExtractor.getAction(null, typeActionScaffold, actionsScaffold)
        }
        return actionScaffold
    }

    void changeTitle(def actionScaffolding, ActionScaffold actionScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(actionScaffolding)
        if (titleScaffold) {
            actionScaffold.title = titleScaffold
        }
    }

    void changePermission(def actionScaffolding, ActionScaffold actionScaffold){
        def permissionScaffolding = actionScaffolding.get("permission")
        if(permissionScaffolding) {
            if(!actionScaffold.permission) {
                actionScaffold.permission = new PermissionAction(actionScaffold: actionScaffold)
            }
            permissionScaffoldingExtrator.changePermission(permissionScaffolding, actionScaffold.permission)
        }

    }

    void changeEnabled(def actionScaffolding, ActionScaffold actionScaffold){
        def enabledAction = actionScaffolding.get("enabled")
        if(enabledAction!=null) {
            actionScaffold.enabled = enabledAction
        }
    }
}

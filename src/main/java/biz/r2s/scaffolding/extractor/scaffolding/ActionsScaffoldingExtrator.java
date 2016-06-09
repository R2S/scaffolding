package  biz.r2s.scaffolding.extractor.scaffolding;

import java.util.List;
import java.util.Map;

import  biz.r2s.scaffolding.extractor.clazz.ActionsClassExtractor;
import  biz.r2s.scaffolding.meta.ClassScaffold;
import  biz.r2s.scaffolding.meta.TitleScaffold;
import  biz.r2s.scaffolding.meta.action.ActionScaffold;
import  biz.r2s.scaffolding.meta.action.ActionsScaffold;
import  biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import  biz.r2s.scaffolding.meta.security.PermissionAction;

/**
 * Created by raphael on 06/08/15.
 */
public class ActionsScaffoldingExtrator {
    TitleScaffoldingExtrator titleScaffoldingExtrator;
    PermissionScaffoldingExtrator permissionScaffoldingExtrator;

    public ActionsScaffoldingExtrator() {
        titleScaffoldingExtrator = new TitleScaffoldingExtrator();
        permissionScaffoldingExtrator = new PermissionScaffoldingExtrator();
    }

    void changeActions(Map<String, Object> classScaffolding, ClassScaffold classScaffold) {;
        changeActions(classScaffolding, classScaffold.getActions());
    }

    void changeActions(Map<String, Object> classScaffolding, ActionsScaffold actionsScaffold) {
    	Object actionsValue = classScaffolding.get("actions");
        if (actionsValue!=null) {
            if (actionsValue instanceof List) {
            	List<String> actionsValueList = (List<String>) actionsValue;
            	for(String actionName:actionsValueList){
            		ActionScaffold actionScaffold = actionsScaffold.getAction(TypeActionScaffold.valueOf(actionName.toUpperCase()));
                            if (actionScaffold!=null) {
                                actionScaffold.setParent(actionsScaffold); 
                                //actionsScaffold."$actionName" = actionScaffold
                            }
            	}                
            } else if (actionsValue instanceof Map) {
            	Map<String, Object> actionsValueMap = (Map) actionsValue;
            	for(String key:actionsValueMap.keySet()){
            		Object value= actionsValueMap.get(key);
            		ActionScaffold actionScaffold = actionsScaffold.getAction(TypeActionScaffold.valueOf(key.toUpperCase()));
                            if (value instanceof String) {
                                TitleScaffold titleScaffold = new TitleScaffold();
                                titleScaffold.setName((String) value); 
                                actionScaffold.setTitle(titleScaffold);
                            }else
                            if (value instanceof Boolean ) {
                                actionScaffold.setEnabled((boolean) value);
                            } else if (value instanceof Map) {
                            	Map<String, Object> valueMap = (Map<String, Object>) value;
                                this.changeTitle(valueMap, actionScaffold);
                                this.changePermission(valueMap, actionScaffold);
                                this.changeEnabled(valueMap, actionScaffold);
                            }
                            if (actionScaffold!=null) {
                                actionScaffold.setParent(actionsScaffold);
                                //actionsScaffold."$actionName" = actionScaffold
                            }
            	}
            }
        }
    }

    ActionScaffold getActionDefault(String actionName, ActionsScaffold actionsScaffold) {
        ActionScaffold actionScaffold = null;
        ActionsClassExtractor actionsClassExtractor = new ActionsClassExtractor();
        TypeActionScaffold typeActionScaffold = null;
        for(TypeActionScaffold typeActionScaffoldkey:actionsClassExtractor.actionsToName.keySet()){
        	if(actionsClassExtractor.actionsToName.get(typeActionScaffoldkey) == actionName ){
        		typeActionScaffold = typeActionScaffoldkey;
        	}
        }
        if (typeActionScaffold!=null) {
            actionScaffold = actionsClassExtractor.getAction(null, typeActionScaffold, actionsScaffold);
        }
        return actionScaffold;
    }

    public void changeTitle(Map<String, Object> actionScaffolding, ActionScaffold actionScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(actionScaffolding);
        if (titleScaffold!=null) {
            actionScaffold.setTitle(titleScaffold);
        }
    }

    public void changePermission(Map<String, Object> actionScaffolding, ActionScaffold actionScaffold){
    	Map<String, Object> permissionScaffolding = (Map<String, Object>) actionScaffolding.get("permission");
        if(permissionScaffolding!=null) {
            if(actionScaffold.getPermission()==null) {
            	PermissionAction permissionAction = new PermissionAction();
            	permissionAction.setActionScaffold(actionScaffold);
                actionScaffold.setPermission(permissionAction); 
            }
            permissionScaffoldingExtrator.changePermission(permissionScaffolding, actionScaffold.getPermission());
        }

    }

    public void changeEnabled(Map<String, Object> actionScaffolding, ActionScaffold actionScaffold){
        Boolean enabledAction = (Boolean) actionScaffolding.get("enabled");
        if(enabledAction!=null) {
            actionScaffold.setEnabled(enabledAction);
        }
    }
}

package biz.r2s.scaffolding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.action.ActionsScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.button.Button;
import biz.r2s.scaffolding.meta.button.PositionButton;
import biz.r2s.scaffolding.meta.datatatable.CampoDatatable;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
import biz.r2s.scaffolding.meta.field.params.StaticParamsFieldScaffold;
import biz.r2s.scaffolding.security.PermissionFacade;

/**
 * Created by raphael on 02/09/15.
 */
public class RulesFacade {

    static List<String> FIELD_EXCLUDE_CREATE = Collections.emptyList();
    static List<String> FIELD_INCLUDE_CREATE = Collections.emptyList();
    static List<String> FIELD_EXCLUDE_EDIT = Collections.emptyList();
    static List<String> FIELD_INCLUDE_EDIT = Collections.emptyList();
    static List<String> FIELD_EXCLUDE_LIST = Collections.emptyList();
    static List<String> FIELD_INCLUDE_LIST = Collections.emptyList();

    static RulesFacade _instance;

    PermissionFacade permissionFacade;

    public RulesFacade() {
    	FIELD_EXCLUDE_CREATE.addAll(Arrays.asList("id", "version","dateCreated", "lastUpdated"));
    	//FIELD_INCLUDE_CREATE
    	FIELD_EXCLUDE_EDIT.addAll(Arrays.asList("dateCreated", "lastUpdated"));
    	FIELD_INCLUDE_EDIT.addAll(Arrays.asList("id", "version"));
    	FIELD_EXCLUDE_LIST.addAll(Arrays.asList("dateCreated", "lastUpdated"));
    	FIELD_INCLUDE_LIST.addAll(Arrays.asList("id", "version"));
        permissionFacade = new PermissionFacade();
    }

    public static RulesFacade getInstance() {
        if (_instance==null) {
            _instance = new RulesFacade();
        }

        return _instance;
    }

    public List<CampoDatatable> listColumns(Map<String, Object> permission, List<CampoDatatable> campoDatatableList) {
    	List<CampoDatatable>  columns = new ArrayList<CampoDatatable>();
    	for(CampoDatatable campoDatatable: campoDatatableList){
    		if (!(FIELD_EXCLUDE_LIST.contains(campoDatatable.getName())) && (FIELD_INCLUDE_LIST.contains(campoDatatable.getName())||  isPermissionColumns(permission, campoDatatable))) {
    			CampoDatatable campoDatatableClone = campoDatatable.clone();
    			if(campoDatatable.getName() == "id"){
                    campoDatatable.setOrder(0);
                }
                columns.add(campoDatatable);
            }
    	}      
    	Collections.sort(columns);
        return columns;
    }



    public Map<FieldScaffold, Boolean> getFieldsCreate(Map<String, Object> permission, List<FieldScaffold> fields) {
        Map<FieldScaffold, Boolean> fieldScaffoldBooleanMap = Collections.emptyMap();
        for(FieldScaffold field:fields){
           if (!(FIELD_EXCLUDE_CREATE.contains(field.getKey())) && (hasPermissionField(permission, field, TypeActionScaffold.CREATE) || FIELD_INCLUDE_CREATE.contains(field.getKey()))) {
                    fieldScaffoldBooleanMap.put(field, !field.isInsertable());
           }
        }
        return fieldScaffoldBooleanMap;
    }

    public boolean isBidirecional(FieldScaffold field, Class fatherClass){
        return field.isBidirecional()&&field.getClazzType().isAssignableFrom(fatherClass);
    }

    public Map<FieldScaffold, Boolean> getFieldsEdit(Map<String, Object> permission, List<FieldScaffold> fields) {
        Map<FieldScaffold, Boolean> fieldScaffoldBooleanMap = Collections.emptyMap();
        for( FieldScaffold field: fields){
            if(!(FIELD_EXCLUDE_EDIT.contains(field.getKey()))){
                if (hasPermissionField(permission, field, TypeActionScaffold.EDIT) || FIELD_INCLUDE_EDIT.contains(field.getKey())) {
                    fieldScaffoldBooleanMap.put(field, !field.isUpdateable());
                } else if (hasPermissionField(permission, field, TypeActionScaffold.VIEW)) {
                    fieldScaffoldBooleanMap.put(field, true);
                }
            }
        }
        return fieldScaffoldBooleanMap;
    }

    public List<FieldScaffold> getFieldsShow(Map<String, Object> permission, List<FieldScaffold> fields) {
        List<FieldScaffold> fieldScaffolds = Collections.emptyList();
        for(FieldScaffold field: fields){
        	if (hasPermissionField(permission, field, TypeActionScaffold.VIEW) || hasPermissionField(permission, field, TypeActionScaffold.CREATE) || hasPermissionField(permission, field, TypeActionScaffold.EDIT)) {
                if(!field.isTypeHasMany()&&field.getType()!=TypeFieldScaffold.STATIC){
                    field.setType(TypeFieldScaffold.STATIC);
                    field.setParams(new StaticParamsFieldScaffold());
                }
                fieldScaffolds.add(field);
            }
        }
        return fieldScaffolds;
    }
    public Map<PositionButton, List<Button>> getButtons(Map<String, Object> permission, List<Button> buttons) {
    	return getButtons(permission, buttons, null);
    }
    public Map<PositionButton, List<Button>> getButtons(Map<String, Object> permission, List<Button> buttons, TypeActionScaffold typeActionScaffold) {
        Map<PositionButton, List<Button>> buttonScaffolds = Collections.emptyMap();
        List<PositionButton> positionsButtons = PositionButton.listByTypeActionScaffold(typeActionScaffold);
        List<Button> buttonsFilter = filterButtons((Map<TypeActionScaffold, Boolean>) permission.get("buttons"), typeActionScaffold, buttons);
        for(Button button:buttonsFilter){
        	if (hasPermissionButton(permission, button)) {
        		for(PositionButton positionButton:button.getPositionsButton()){
        			if(positionsButtons.contains(positionButton)){
                        List<Button> buttonsMap =  (List<Button>) buttonScaffolds.get(positionButton);
                        if(buttonsMap!=null){
                            buttonsMap.add(button);
                        }else{
                            buttonScaffolds.put(positionButton, Arrays.asList(button));
                        }
                    }
        		}
            }
        }       
        return buttonScaffolds;
    }
    
    private List<Button> filterButtons(Map<TypeActionScaffold, Boolean> actionsPermission, TypeActionScaffold typeActionScaffold, List<Button> buttons){
    	List<Button> buttonsFilter = Collections.emptyList();
    	for(Button button: buttons){
    		
    		List<TypeActionScaffold> typeActionScaffolds = Collections.emptyList();
    		
    		for(PositionButton positionButton: button.getPositionsButton()){
    			typeActionScaffolds.add(positionButton.getTypeActionScaffold());
    		}   				
    		
    		if(actionsPermission.get(button.getActionScaffold()) &&  (typeActionScaffold!=null && typeActionScaffolds.contains(typeActionScaffold))){
    			buttonsFilter.add(button);
    		}
    	}
    	return buttonsFilter;
    }
    

    public Map<String, TypeActionScaffold> getActions(Map<String, Object> permission) {
        Map<String, TypeActionScaffold> actionScaffoldMap = Collections.emptyMap();
        Map<String, Boolean> actions = (Map<String, Boolean>) permission.get("actions");
        
        for(String key:actions.keySet()){
        	boolean isPermission = actions.get(key);
        	TypeActionScaffold typeActionScaffold = TypeActionScaffold.valueOf(key);
        	if ((typeActionScaffold == TypeActionScaffold.VIEW && isPermissionShow(actions)) || isPermission || !enablePermission()) {
                actionScaffoldMap.put(ActionsScaffold.getNameAction(typeActionScaffold), typeActionScaffold);
            }
        }
        return actionScaffoldMap;
    }

    private boolean isPermissionShow(Map<String, Boolean> permissionActions) {
        if(!enablePermission()){
            return true;
        }

        boolean isShow = false;

        if (permissionActions.get(TypeActionScaffold.CREATE.toString()) == true) {
            isShow = true;
        } else if (permissionActions.get(TypeActionScaffold.EDIT.toString()) == true) {
            isShow = true;
        } else if (permissionActions.get(TypeActionScaffold.DELETE.toString()) == true) {
            isShow = true;
        }
        return isShow;
    }


    private boolean isPermissionColumns(Map<String, Object> permission, CampoDatatable column) {
    	FieldScaffold fieldScaffold = getByCampoDatatable(column);
    	if (!hasManyDatatable() && fieldScaffold!=null && fieldScaffold.isTypeHasMany()) {
            return false;
        }

        if(!enablePermission()){
            return true;
        }

        Map<String, Object> fields = (Map<String, Object>) permission.get("fields");
        Object campoField = ((Map<String, Object>) fields.get(column.getName())).get(TypeActionScaffold.LIST);

        return campoField!=null ? true : false;
    }

    private FieldScaffold getByCampoDatatable(CampoDatatable campoDatatable) {
    	for(FieldScaffold fieldScaffold:campoDatatable.getParent().getClassScaffold().getFields()){
    		if(fieldScaffold.getKey() == campoDatatable.getName()){
    			return fieldScaffold;
    		}
    	}    	
        return null;
    }

    public boolean hasPermissionButton(Map<String, Object> permission, Button button){
    	Map<String, Object>  buttons = (Map<String, Object>) permission.get("buttons");
        Object buttonPermission = buttons.get(button.getName());

        return buttonPermission!=null ? true : false;
    }

    public boolean hasPermissionField(Map<String, Object> permission, FieldScaffold field, TypeActionScaffold actionScaffold) {
    	List<String> transiendsShow = field.getParent().getTransiendsShow();
    	
        if(actionScaffold == TypeActionScaffold.VIEW&&field.isTransients()&&!field.isMandatory()&&transiendsShow.contains(field.getKey())){
            return true;
        }

        if(field.isTransients()){
            return false;
        }

        if (!hasManyForm() && field.isTypeHasMany()) {
            return false;
        }

        if (!field.isScaffold()) {
            return false;
        }

        if (actionScaffold == TypeActionScaffold.CREATE && field.isMandatory()) {
            return true;
        }

        if(!enablePermission()){
            return true;
        }
        Map<String, Object> fields = (Map<String, Object>) permission.get("permission");
        Map<String, Object> fieldMap = (Map<String, Object>) fields.get(field.getKey());
        
        Object fieldPermission = fieldMap.get(actionScaffold);

        return fieldPermission!=null ? true : false;
    }

    private boolean hasManyDatatable() {
        return false;
    }

    private boolean hasManyForm() {
        return true;
    }

    public boolean enablePermission(){
        return true;
    }

    public boolean enablePermissionMenu(){
        return true;
    }
}

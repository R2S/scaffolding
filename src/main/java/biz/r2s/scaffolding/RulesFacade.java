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

    static List<String> FIELD_EXCLUDE_CREATE = Collections.EMPTY_LIST;
    static List<String> FIELD_INCLUDE_CREATE = Collections.EMPTY_LIST;
    static List<String> FIELD_EXCLUDE_EDIT = Collections.EMPTY_LIST;
    static List<String> FIELD_INCLUDE_EDIT = Collections.EMPTY_LIST;
    static List<String> FIELD_EXCLUDE_LIST = Collections.EMPTY_LIST;
    static List<String> FIELD_INCLUDE_LIST = Collections.EMPTY_LIST;

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

    static RulesFacade getInstance() {
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



    Map<FieldScaffold, Boolean> getFieldsCreate(def permission, List<FieldScaffold> fields) {
        Map<FieldScaffold, Boolean> fieldScaffoldBooleanMap = [:]
        fields.each { FieldScaffold field ->
           if (!(field.key in FIELD_EXCLUDE_CREATE) && (hasPermissionField(permission, field, TypeActionScaffold.CREATE) || field.key in FIELD_INCLUDE_CREATE)) {
                    fieldScaffoldBooleanMap.put(field, !field.insertable)
           }
        }
        return fieldScaffoldBooleanMap.sort({it.key.order})
    }

    boolean isBidirecional(FieldScaffold field, Class fatherClass){
        field.bidirecional&&field.clazzType.isAssignableFrom(fatherClass)

    }

    Map<FieldScaffold, Boolean> getFieldsEdit(def permission, List<FieldScaffold> fields) {
        Map<FieldScaffold, Boolean> fieldScaffoldBooleanMap = [:]
        fields.each { FieldScaffold field ->
            if(!(field.key in FIELD_EXCLUDE_EDIT)){
                if (hasPermissionField(permission, field, TypeActionScaffold.EDIT) || field.key in FIELD_INCLUDE_EDIT) {
                    fieldScaffoldBooleanMap.put(field, !field.updateable)
                } else if (hasPermissionField(permission, field, TypeActionScaffold.VIEW)) {
                    fieldScaffoldBooleanMap.put(field, true)
                }
            }
        }
        return fieldScaffoldBooleanMap.sort({it.key.order})
    }

    List<FieldScaffold> getFieldsShow(def permission, List<FieldScaffold> fields) {
        List<FieldScaffold> fieldScaffolds = []
        fields.each { FieldScaffold field ->
            if (hasPermissionField(permission, field, TypeActionScaffold.VIEW) || hasPermissionField(permission, field, TypeActionScaffold.CREATE) || hasPermissionField(permission, field, TypeActionScaffold.EDIT)) {
                if(!field.isTypeHasMany()&&field.type!=TypeFieldScaffold.STATIC){
                    field.type = TypeFieldScaffold.STATIC
                    field.params = new StaticParamsFieldScaffold()
                }
                fieldScaffolds << field
            }
        }
        return fieldScaffolds
    }

    Map<PositionButton, Button> getButtons(def permission, List<Button> buttons, TypeActionScaffold typeActionScaffold = null) {
        Map<PositionButton, Button> buttonScaffolds = [:]
        List<PositionButton> positionsButtons = PositionButton.values().findAll{it.typeActionScaffold == typeActionScaffold}
        List<Button> buttonsFilter = buttons.findAll({permission.actions.get(it.actionScaffold) &&  (typeActionScaffold && it.positionsButton.typeActionScaffold.contains(typeActionScaffold))})
        buttonsFilter.each { Button button->
            if (hasPermissionButton(permission, button)) {
                button.positionsButton.each {PositionButton positionButton->
                    if(positionButton in positionsButtons){
                        List<Button> buttonsMap =  buttonScaffolds.get(positionButton)
                        if(buttonsMap){
                            buttonsMap << button
                        }else{
                            buttonScaffolds.put(positionButton, [button])
                        }
                    }
                }
            }
        }
        return buttonScaffolds
    }

    Map<String, TypeActionScaffold> getActions(def permission) {
        Map<String, TypeActionScaffold> actionScaffoldMap = [:]
        permission.actions.each { TypeActionScaffold typeActionScaffold, boolean isPermission ->
            if ((typeActionScaffold == TypeActionScaffold.VIEW && isPermissionShow(permission.actions)) || isPermission || !enablePermission()) {
                actionScaffoldMap.put(ActionsScaffold.getNameAction(typeActionScaffold), typeActionScaffold)
            }
        }
        return actionScaffoldMap
    }

    private isPermissionShow(def permissionActions) {
        if(!enablePermission()){
            return true
        }

        boolean isShow = false

        if (permissionActions.get(TypeActionScaffold.CREATE) == true) {
            isShow = true
        } else if (permissionActions.get(TypeActionScaffold.EDIT) == true) {
            isShow = true
        } else if (permissionActions.get(TypeActionScaffold.DELETE) == true) {
            isShow = true
        }
        return isShow
    }


    private boolean isPermissionColumns(Map<String, Object> permission, CampoDatatable column) {
    	FieldScaffold fieldScaffold = getByCampoDatatable(column);
    	if (!hasManyDatatable() && fieldScaffold!=null && fieldScaffold.isTypeHasMany()) {
            return false;
        }

        if(!enablePermission()){
            return true;
        }

        Object campoField = permission.fields.get(column.name).get(TypeActionScaffold.LIST);

        return campoField ? true : false;
    }

    private FieldScaffold getByCampoDatatable(CampoDatatable campoDatatable) {
        return null;//campoDatatable.parent.getClassScaffold().fields.find({ it.key == campoDatatable.name })
    }

    def hasPermissionButton(def permission, Button button){
        def buttonPermission = permission.buttons.get(button.name)

        return buttonPermission ? true : false
    }

    def hasPermissionField(def permission, FieldScaffold field, TypeActionScaffold actionScaffold) {
        if(actionScaffold == TypeActionScaffold.VIEW&&field.transients&&!field.isMandatory()&&field.parent?.transiendsShow?.find({field.key==it})){
            return true
        }

        if(field.transients){
            return false
        }

        if (!hasManyForm() && field.isTypeHasMany()) {
            return false
        }

        if (!field.scaffold) {
            return false
        }

        if (actionScaffold == TypeActionScaffold.CREATE && field.isMandatory()) {
            return true
        }

        if(!enablePermission()){
            return true
        }

        def fieldPermission = permission.fields.get(field.key)?.get(actionScaffold)

        return fieldPermission ? true : false
    }

    private boolean hasManyDatatable() {
        return false
    }

    private boolean hasManyForm() {
        return true
    }

    public boolean enablePermission(){
        return true
    }

    public boolean enablePermissionMenu(){
        return true
    }
}

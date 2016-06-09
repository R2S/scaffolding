package biz.r2s.scaffolding.meta;

import java.util.ArrayList;
import java.util.List;

import biz.r2s.scaffolding.meta.action.ActionsScaffold;
import biz.r2s.scaffolding.meta.button.Button;
import biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.security.PermissionClass;

/**
 * Created by raphael on 24/07/15.
 */
public class ClassScaffold {
    String name;
    TitleScaffold title;
    ActionsScaffold actions;
    List<FieldScaffold> fields;
    PermissionClass permission;
    DatatableScaffold datatable;
    StatusClassScaffold status;
    Class clazz;
    MenuScaffold menu;
    List<String> transiendsShow;
    String serviceClass;
    String controllerClass;
    List<Button> buttons;
    boolean isHasMany;

    public void setStatus(StatusClassScaffold status){
        this.status = status;
    }

    List<FieldScaffold> getFieldHasMany(){
        List<FieldScaffold> fieldsHM = new ArrayList<FieldScaffold>();
        for(FieldScaffold fieldScaffold:fields){
        	if(fieldScaffold.isTypeHasMany()){
                fieldsHM.add(fieldScaffold);
            }
        }
        return fieldsHM;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TitleScaffold getTitle() {
		return title;
	}

	public void setTitle(TitleScaffold title) {
		this.title = title;
	}

	public ActionsScaffold getActions() {
		return actions;
	}

	public void setActions(ActionsScaffold actions) {
		this.actions = actions;
	}

	public List<FieldScaffold> getFields() {
		return fields;
	}

	public void setFields(List<FieldScaffold> fields) {
		this.fields = fields;
	}

	public PermissionClass getPermission() {
		return permission;
	}

	public void setPermission(PermissionClass permission) {
		this.permission = permission;
	}

	public DatatableScaffold getDatatable() {
		return datatable;
	}

	public void setDatatable(DatatableScaffold datatable) {
		this.datatable = datatable;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public MenuScaffold getMenu() {
		return menu;
	}

	public void setMenu(MenuScaffold menu) {
		this.menu = menu;
	}

	public List<String> getTransiendsShow() {
		return transiendsShow;
	}

	public void setTransiendsShow(List<String> transiendsShow) {
		this.transiendsShow = transiendsShow;
	}

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	public String getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(String controllerClass) {
		this.controllerClass = controllerClass;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public boolean isHasMany() {
		return isHasMany;
	}

	public void setHasMany(boolean isHasMany) {
		this.isHasMany = isHasMany;
	}

	public StatusClassScaffold getStatus() {
		return status;
	}
    
}

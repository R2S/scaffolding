package biz.r2s.scaffolding.meta.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class PermissionField extends Permission{
    HashMap<TypeActionScaffold, List<String>> actionRoles = new HashMap<TypeActionScaffold, List<String>>();
    FieldScaffold fieldScaffold;
    
    @Override
    public Map<String, Object> format() {
    	Map<String, Object> map = super.format();
        map.put("actionRoles", actionRoles);
        return map;
    }

	public HashMap<TypeActionScaffold, List<String>> getActionRoles() {
		return actionRoles;
	}

	public void setActionRoles(HashMap<TypeActionScaffold, List<String>> actionRoles) {
		this.actionRoles = actionRoles;
	}

	public FieldScaffold getFieldScaffold() {
		return fieldScaffold;
	}

	public void setFieldScaffold(FieldScaffold fieldScaffold) {
		this.fieldScaffold = fieldScaffold;
	}   
    
}

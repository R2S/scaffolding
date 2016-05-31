package biz.r2s.scaffolding.meta.field;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.field.params.ParamsFieldScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.security.PermissionField;


/**
 * Created by raphael on 24/07/15.
 */
public class FieldScaffold {
    String key;
    String elementId;
    String label;
    PermissionField permission;
    boolean insertable;
    boolean updateable;
    //List<Constraint> constraints = new ArrayList<Constraint>();
    TypeFieldScaffold type;
    ParamsFieldScaffold params;
    IconScaffold icon;
    Object defaultValue;
    boolean scaffold;
    ClassScaffold parent;
    int order;
    Class clazzType;
    boolean bidirecional;
    boolean transients;

    public boolean isMandatory(){
        /*Constraint obj = null;//constraints.find({it.type==TypeConstraint.NULLABLE});
        if(obj!=null){
            return obj.getValue()==null;
        }*/
        return true;
    }

    public boolean isTypeHasMany(){
        return type == TypeFieldScaffold.DATATABLE;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public PermissionField getPermission() {
		return permission;
	}

	public void setPermission(PermissionField permission) {
		this.permission = permission;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	/*public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}
*/
	public TypeFieldScaffold getType() {
		return type;
	}

	public void setType(TypeFieldScaffold type) {
		this.type = type;
	}

	public ParamsFieldScaffold getParams() {
		return params;
	}

	public void setParams(ParamsFieldScaffold params) {
		this.params = params;
	}

	public IconScaffold getIcon() {
		return icon;
	}

	public void setIcon(IconScaffold icon) {
		this.icon = icon;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isScaffold() {
		return scaffold;
	}

	public void setScaffold(boolean scaffold) {
		this.scaffold = scaffold;
	}

	public ClassScaffold getParent() {
		return parent;
	}

	public void setParent(ClassScaffold parent) {
		this.parent = parent;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Class getClazzType() {
		return clazzType;
	}

	public void setClazzType(Class clazzType) {
		this.clazzType = clazzType;
	}

	public boolean isBidirecional() {
		return bidirecional;
	}

	public void setBidirecional(boolean bidirecional) {
		this.bidirecional = bidirecional;
	}

	public boolean isTransients() {
		return transients;
	}

	public void setTransients(boolean transients) {
		this.transients = transients;
	}    

}

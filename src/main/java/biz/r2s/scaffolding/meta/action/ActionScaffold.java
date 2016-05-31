package biz.r2s.scaffolding.meta.action;

import biz.r2s.scaffolding.meta.TitleScaffold;
import biz.r2s.scaffolding.meta.security.PermissionAction;

/**
 * Created by raphael on 24/07/15.
 */
public class ActionScaffold {
    TitleScaffold title;
    PermissionAction permission;
    TypeActionScaffold typeActionScaffold;
    ActionsScaffold parent;
    boolean enabled;
	public TitleScaffold getTitle() {
		return title;
	}
	public void setTitle(TitleScaffold title) {
		this.title = title;
	}
	public PermissionAction getPermission() {
		return permission;
	}
	public void setPermission(PermissionAction permission) {
		this.permission = permission;
	}
	public TypeActionScaffold getTypeActionScaffold() {
		return typeActionScaffold;
	}
	public void setTypeActionScaffold(TypeActionScaffold typeActionScaffold) {
		this.typeActionScaffold = typeActionScaffold;
	}
	public ActionsScaffold getParent() {
		return parent;
	}
	public void setParent(ActionsScaffold parent) {
		this.parent = parent;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
    
}

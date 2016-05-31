package biz.r2s.scaffolding.meta.security;

import biz.r2s.scaffolding.meta.action.ActionScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class PermissionAction extends Permission{
    ActionScaffold actionScaffold;

	public ActionScaffold getActionScaffold() {
		return actionScaffold;
	}

	public void setActionScaffold(ActionScaffold actionScaffold) {
		this.actionScaffold = actionScaffold;
	}
    
    
}

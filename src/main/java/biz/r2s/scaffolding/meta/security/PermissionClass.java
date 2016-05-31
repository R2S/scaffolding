package biz.r2s.scaffolding.meta.security;

import biz.r2s.scaffolding.meta.ClassScaffold;
/**
 * Created by raphael on 24/07/15.
 */
public class PermissionClass extends Permission{
    ClassScaffold classScaffold;

	public ClassScaffold getClassScaffold() {
		return classScaffold;
	}

	public void setClassScaffold(ClassScaffold classScaffold) {
		this.classScaffold = classScaffold;
	}
    
}

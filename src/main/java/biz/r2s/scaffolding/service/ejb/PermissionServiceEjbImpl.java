package biz.r2s.scaffolding.service.ejb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.security.PermissionFacade;
import biz.r2s.scaffolding.service.PermissionService;

public class PermissionServiceEjbImpl implements PermissionService{
	private PermissionFacade  permissionFacade;

    public PermissionServiceEjbImpl(){
        permissionFacade = new PermissionFacade();
    }

    public Map<String, Object>  getPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields) {
        return permissionFacade.getPermissionScaffold(classScaffold, actions, fields);
    }

    public Map<String, Object> getPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions) {
    	return permissionFacade.getPermissionScaffold(classScaffold, actions, null);
    }

    public Map<String, Object> getPermission(ClassScaffold classScaffold, TypeActionScaffold action, String field) {
    	return permissionFacade.getPermissionScaffold(classScaffold, Arrays.asList(action), Arrays.asList(field));
    }

    public Map<String, Object> getPermission(ClassScaffold classScaffold, TypeActionScaffold action) {
    	return permissionFacade.getPermissionScaffold(classScaffold, Arrays.asList(action), null);
    }

    public Map<String, Object> getPermission(ClassScaffold classScaffold) {
        return permissionFacade.getPermissionScaffold(classScaffold, null, null);
    }

    public Boolean hasPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields) {
    	return permissionFacade.hasPermissionScaffold(classScaffold, actions, fields);
    }

    public Boolean hasPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions) {
    	return permissionFacade.hasPermissionScaffold(classScaffold, actions, null);
    }

    public Boolean hasPermission(ClassScaffold classScaffold, TypeActionScaffold action, String field) {
    	return permissionFacade.hasPermissionScaffold(classScaffold, Arrays.asList(action), Arrays.asList(field));
    }

    public Boolean hasPermission(ClassScaffold classScaffold, TypeActionScaffold action) {
    	return permissionFacade.hasPermissionScaffold(classScaffold, Arrays.asList(action), null);
    }

    public Boolean hasPermission(ClassScaffold classScaffold) {
    	return permissionFacade.hasPermissionScaffold(classScaffold, null, null);
    }
}

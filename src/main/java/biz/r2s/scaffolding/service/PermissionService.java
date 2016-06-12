package biz.r2s.scaffolding.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;

public interface PermissionService {
	public Map<String, Object>  getPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields);

    public Map<String, Object> getPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions);

    public Map<String, Object> getPermission(ClassScaffold classScaffold, TypeActionScaffold action, String field);

    public Map<String, Object> getPermission(ClassScaffold classScaffold, TypeActionScaffold action);

    public Map<String, Object> getPermission(ClassScaffold classScaffold);

    public Boolean hasPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions, List<String> fields);

    public Boolean hasPermission(ClassScaffold classScaffold, List<TypeActionScaffold> actions);

    public Boolean hasPermission(ClassScaffold classScaffold, TypeActionScaffold action, String field);

    public Boolean hasPermission(ClassScaffold classScaffold, TypeActionScaffold action);

    public Boolean hasPermission(ClassScaffold classScaffold);
}

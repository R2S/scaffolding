package biz.r2s.scaffolding.service.ejb;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import biz.r2s.core.util.ObjectUtil;
import biz.r2s.scaffolding.builder.ClassScaffoldBuilder;
import biz.r2s.scaffolding.format.ButtonFormat;
import biz.r2s.scaffolding.format.CommonFormat;
import biz.r2s.scaffolding.format.DatatableFormat;
import biz.r2s.scaffolding.format.FieldsFormat;
import biz.r2s.scaffolding.format.MenuRootFormat;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.action.ActionScaffold;
import biz.r2s.scaffolding.meta.action.ActionsScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.params.DataTableParamsFieldScaffold;
import biz.r2s.scaffolding.security.PermissionFacade;

public class MetaServiceEjbImpl {
	CommonFormat commonFormat;
	DatatableFormat datatableFormat;
	FieldsFormat fieldsFormat;
	PermissionFacade permissionFacade;
	ButtonFormat buttonFormat;
	MenuRootFormat menuRootFormat;

	public MetaServiceEjbImpl() {
		commonFormat = new CommonFormat();
		datatableFormat = new DatatableFormat();
		fieldsFormat = new FieldsFormat();
		permissionFacade = new PermissionFacade();
		buttonFormat = new ButtonFormat();
		menuRootFormat = new MenuRootFormat();
	}

	public Map getMetaList(Class domainClass, String propertyName, Object fatherId) {
		ClassScaffold classScaffold = getMeta(domainClass);
		Map meta = Collections.emptyMap();
		if (classScaffold != null) {
			Map config = getConfigsMeta(domainClass, propertyName, fatherId, classScaffold, TypeActionScaffold.LIST);
			ActionsScaffold actions = (ActionsScaffold) config.get("actions");
			DatatableScaffold datatableScaffold = (DatatableScaffold) config.get("datatableScaffold");
			Map permission = (Map) config.get("permission");
			ClassScaffold classScaffoldAction = (ClassScaffold) config.get("classScaffoldAction");
			Class fatherClass = (Class) config.get("fatherClass");
			TypeActionScaffold typeActionScaffold = (TypeActionScaffold) config.get("typeActionScaffold");
			String name = (String) config.get("name");

			Map<String, Object> dataTableMap = datatableFormat.formatarDatatable(permission, datatableScaffold,
					fatherId);
			meta.put("title", formatTitle(actions.getList(), name, classScaffoldAction));
			meta.put("dataTable", datatableFormat.formatarDatatable((Map) config.get("permission"),
					(DatatableScaffold) config.get("datatableScaffold"), fatherId));

			if (!(Boolean) config.get("isHasMany")) {
				dataTableMap.put("title", meta.get("title"));
			}

			meta.put("dataTable", dataTableMap);
			meta.put("actions", commonFormat.formatActions(permission, actions,
					datatableScaffold.getResourceUrlScaffold(), fatherId));
			meta.put("buttons", buttonFormat.formatButtons(permission, classScaffoldAction.getButtons(),
					typeActionScaffold, fatherId));
		}
		return meta;
	}

	public Map getMetaCreate(Class domainClass, String propertyName, Object fatherId) {
		ClassScaffold classScaffold = getMeta(domainClass);
		Map meta = Collections.emptyMap();
		if (classScaffold != null) {
			Map config = getConfigsMeta(domainClass, propertyName, fatherId, classScaffold, TypeActionScaffold.CREATE);
			ActionsScaffold actions = (ActionsScaffold) config.get("actions");
			DatatableScaffold datatableScaffold = (DatatableScaffold) config.get("datatableScaffold");
			Map permission = (Map) config.get("permission");
			ClassScaffold classScaffoldAction = (ClassScaffold) config.get("classScaffoldAction");
			Class fatherClass = (Class) config.get("fatherClass");
			TypeActionScaffold typeActionScaffold = (TypeActionScaffold) config.get("typeActionScaffold");
			String name = (String) config.get("name");

			meta.put("title", formatTitle(actions.getCreate(), name, classScaffoldAction));
			meta.putAll(fieldsFormat.formatFieldsAndHasMany(permission, classScaffoldAction,
					classScaffoldAction.getFields(), TypeActionScaffold.CREATE, fatherClass));
			meta.put("url", datatableScaffold.getResourceUrlScaffold().resolver(TypeActionScaffold.CREATE, fatherId)
					.formatUrl());
			meta.put("actions", commonFormat.formatActions(permission, actions,
					datatableScaffold.getResourceUrlScaffold(), fatherId));
			meta.put("buttons", buttonFormat.formatButtons(permission, classScaffoldAction.getButtons(),
					typeActionScaffold, fatherId));
		}
		return meta;
	}

	public Map getMetaEdit(Class domainClass, String propertyName, Object fatherId) {
		ClassScaffold classScaffold = getMeta(domainClass);
		Map meta = Collections.emptyMap();
		if (classScaffold != null) {
			Map config = getConfigsMeta(domainClass, propertyName, fatherId, classScaffold, TypeActionScaffold.EDIT);
			ActionsScaffold actions = (ActionsScaffold) config.get("actions");
			DatatableScaffold datatableScaffold = (DatatableScaffold) config.get("datatableScaffold");
			Map permission = (Map) config.get("permission");
			ClassScaffold classScaffoldAction = (ClassScaffold) config.get("classScaffoldAction");
			Class fatherClass = (Class) config.get("fatherClass");
			TypeActionScaffold typeActionScaffold = (TypeActionScaffold) config.get("typeActionScaffold");
			String name = (String) config.get("name");
			meta.put("title", formatTitle(actions.getEdit(), name, classScaffoldAction));
			meta.putAll(fieldsFormat.formatFieldsAndHasMany(permission, classScaffoldAction,
					classScaffoldAction.getFields(), TypeActionScaffold.EDIT, fatherClass));
			meta.put("url",
					datatableScaffold.getResourceUrlScaffold().resolver(TypeActionScaffold.EDIT, fatherId).formatUrl());
			meta.put("actions", commonFormat.formatActions(permission, actions,
					datatableScaffold.getResourceUrlScaffold(), fatherId));
			meta.put("buttons", buttonFormat.formatButtons(permission, classScaffoldAction.getButtons(),
					typeActionScaffold, fatherId));
		}
		return meta;

	}

	public Map getMetaShow(Class domainClass, String propertyName, Object fatherId) {
		ClassScaffold classScaffold = getMeta(domainClass);
		Map meta = Collections.emptyMap();
		if (classScaffold != null) {
			Map config = getConfigsMeta(domainClass, propertyName, fatherId, classScaffold, TypeActionScaffold.VIEW);
			ActionsScaffold actions = (ActionsScaffold) config.get("actions");
			DatatableScaffold datatableScaffold = (DatatableScaffold) config.get("datatableScaffold");
			Map permission = (Map) config.get("permission");
			ClassScaffold classScaffoldAction = (ClassScaffold) config.get("classScaffoldAction");
			Class fatherClass = (Class) config.get("fatherClass");
			TypeActionScaffold typeActionScaffold = (TypeActionScaffold) config.get("typeActionScaffold");
			String name = (String) config.get("name");
			meta.put("title", formatTitle(actions.getShow(), name, classScaffoldAction));
			meta.putAll(fieldsFormat.formatFieldsAndHasMany(permission, classScaffoldAction,
					classScaffoldAction.getFields(), TypeActionScaffold.VIEW, fatherClass));
			meta.put("url",
					datatableScaffold.getResourceUrlScaffold().resolver(TypeActionScaffold.VIEW, fatherId).formatUrl());
			meta.put("actions", commonFormat.formatActions(permission, actions,
					datatableScaffold.getResourceUrlScaffold(), fatherId));
			meta.put("buttons", buttonFormat.formatButtons(permission, classScaffoldAction.getButtons(),
					typeActionScaffold, fatherId));
			
		}
        return meta;

    }

	private ClassScaffold getMeta(Class domainClass) {
		return getMeta(domainClass, false);
	}

	private ClassScaffold getMeta(Class domainClass, boolean isHasMany) {
		return ClassScaffoldBuilder.getInstance().builder(domainClass, isHasMany);
	}

	private Map getConfigsMeta(Class domainClass, final String propertyName, Object fatherId,
			ClassScaffold classScaffold, TypeActionScaffold typeActionScaffold) {
		ActionsScaffold actions;
		DatatableScaffold datatableScaffold;
		Map permission;
		ClassScaffold classScaffoldAction;
		Class fatherClass = null;
		String name;

		name = classScaffold.getName();
		if (propertyName != null) {
			FieldScaffold fieldScaffold = (FieldScaffold) CollectionUtils.find(classScaffold.getFields(),
					new Predicate() {
						@Override
						public boolean evaluate(Object arg0) {
							return ((FieldScaffold) arg0).getKey() == propertyName;
						}
					});
			DataTableParamsFieldScaffold dataTableParamsFieldScaffold = (DataTableParamsFieldScaffold) fieldScaffold
					.getParams();

			Class domainClassChildrem = ObjectUtil.getField(propertyName, domainClass).getDeclaringClass();
			ClassScaffold classScaffoldChildrem = getMeta(domainClassChildrem, true);
			name = classScaffoldChildrem.getName();
			permission = permissionFacade.getPermissionScaffold(classScaffoldChildrem, null, null, null);
			datatableScaffold = dataTableParamsFieldScaffold;
			actions = dataTableParamsFieldScaffold.getActions();
			classScaffoldAction = classScaffoldChildrem;
			fatherClass = domainClass;
		} else {
			actions = classScaffold.getActions();
			permission = permissionFacade.getPermissionScaffold(classScaffold, null, null, null);
			datatableScaffold = classScaffold.getDatatable();
			classScaffoldAction = classScaffold;
		}
		Map meta = Collections.emptyMap();

		meta.put("actions", actions);
		meta.put("datatableScaffold", datatableScaffold);
		meta.put("permission", permission);
		meta.put("classScaffoldAction", classScaffoldAction);
		meta.put("fatherClass", fatherClass);
		meta.put("name", name);
		meta.put("typeActionScaffold", typeActionScaffold);
		return meta;
	}

	private String formatTitle(ActionScaffold action, String name, ClassScaffold classScaffoldAction){
        return this.getRootTitle(classScaffoldAction)+" - "+commonFormat.formatTitle(action.getTitle()) + " " + name;
    }

	private String getRootTitle(ClassScaffold classScaffoldAction){
        return (String) menuRootFormat.getMenuRoot(classScaffoldAction.getMenu().getRoot()).get("name");
    }
}

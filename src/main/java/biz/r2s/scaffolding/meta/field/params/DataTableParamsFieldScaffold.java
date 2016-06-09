package biz.r2s.scaffolding.meta.field.params;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import biz.r2s.scaffolding.format.DatatableFormat;
import biz.r2s.scaffolding.meta.action.ActionsScaffold;
import biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
import biz.r2s.scaffolding.meta.security.Permission;

/**
 * Created by raphael on 29/07/15.
 */
public class DataTableParamsFieldScaffold extends DatatableScaffold implements ParamsFieldScaffold  {

	ActionsScaffold actions;

    private DatatableFormat datatableFormat;

    FieldScaffold parent;

    public DataTableParamsFieldScaffold(){
        datatableFormat = new DatatableFormat();
    }

    @Override
    public boolean validate(Map<String, Object> params) {
        if(Arrays.asList("pagination", "searchable", "ordenate", "sortable",
                               "sort", "order", "title", "paginateDefault", "url",
                               "columns","buttonCreate", "buttons", "actions").containsAll(params.keySet()))
            return true;
        return false;
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.DATATABLE;
    }

    @Override
    public Map<String, Object> format(){
        return datatableFormat.formatarDatatable(new HashMap(), this);
    }

    public boolean hasRoles(Permission permission){
        return true;
    }

	public ActionsScaffold getActions() {
		return actions;
	}

	public void setActions(ActionsScaffold actions) {
		this.actions = actions;
	}

	public DatatableFormat getDatatableFormat() {
		return datatableFormat;
	}

	public void setDatatableFormat(DatatableFormat datatableFormat) {
		this.datatableFormat = datatableFormat;
	}

	public FieldScaffold getParent() {
		return parent;
	}

	public void setParent(FieldScaffold parent) {
		this.parent = parent;
	}
}

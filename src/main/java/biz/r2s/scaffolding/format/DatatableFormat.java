package biz.r2s.scaffolding.format;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.RulesFacade;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.datatatable.CampoDatatable;
import biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import biz.r2s.scaffolding.meta.datatatable.OrderDatatable;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
import biz.r2s.scaffolding.security.PermissionFacade;

/**
 * Created by raphael on 11/08/15.
 */
public class DatatableFormat {

	PermissionFacade permissionFacade;
	CommonFormat commonFormat;

	public DatatableFormat() {
		commonFormat = new CommonFormat();
		permissionFacade = new PermissionFacade();
	}
	public Map<String, Object> formatarDatatable(Map<String, Object> permission, DatatableScaffold dt) {
		return formatarDatatable(permission, dt, null);
	}
	public Map<String, Object> formatarDatatable(Map<String, Object> permission, DatatableScaffold dt, Object fatherId) {
		Map<String, Object> meta = Collections.emptyMap();
		meta.put("pagination", dt.isPagination());
		meta.put("searchable", dt.isSearchable());
		meta.put("ordenate", dt.isOrdenate());
		meta.put("sortable", dt.isSortable());
		if (dt.getOrder() != null) {
			meta.put("order", dt.getOrder() == OrderDatatable.DESC ? "desc" : "asc");
		}
		meta.put("sort", dt.getSort());
		meta.put("title", commonFormat.formatTitle(dt.getTitle()));
		meta.put("numPaginate", dt.getNumMaxPaginate());
		meta.put("url", this.formatUrlDataTable(dt.getResourceUrlScaffold(), fatherId));
		meta.put("columns", this.formatColumns(permission, dt.getColumns(), dt.getClassScaffold()));
		return meta;
	}

	public List<Map<String, Object>> formatColumns(Map<String, Object> permission, List<CampoDatatable> columns,
			ClassScaffold ClassScaffold) {
		List<Map<String, Object>> columnsMeta = Collections.emptyList();

		for (CampoDatatable campoDatatable : RulesFacade.getInstance().listColumns(permission, columns)) {
			columnsMeta.add(formatColumn(campoDatatable));
		}

		return columnsMeta;
	}

	public Map<String, Object> formatColumn(CampoDatatable it) {
		Map<String, Object> columnMeta = Collections.emptyMap();
		columnMeta.put("name", it.getName());
		columnMeta.put("title", it.getTitle());
		columnMeta.put("key", it.getKey());
		columnMeta.put("length", it.getLength());
		columnMeta.put("type", getType(it).name());
		columnMeta.put("order", it.getOrder());

		if (it.getIcon() != null) {
			columnMeta.put("icon", commonFormat.formatIcon(it.getIcon()));
		}
		return columnMeta;
	}

	public TypeFieldScaffold getType(CampoDatatable campoDatatable) {
		for (FieldScaffold fieldScaffold : campoDatatable.getParent().getClassScaffold().getFields()) {
			if (fieldScaffold.getKey() == campoDatatable.getName()) {
				return fieldScaffold.getType() != null ? fieldScaffold.getType() : TypeFieldScaffold.INPUT;
			}
		}
		return null;
	}

	public Map<String, Object> formatUrlDataTable(ResourceUrlScaffold resourceUrlScaffold, Object fatherId) {
		return resourceUrlScaffold.resolver(TypeActionScaffold.LIST, fatherId).formatUrl();
	}
}

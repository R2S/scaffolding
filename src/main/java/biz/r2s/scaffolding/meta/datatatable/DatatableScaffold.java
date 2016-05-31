package biz.r2s.scaffolding.meta.datatatable;

import java.util.List;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.TitleScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;


/**
 * Created by raphael on 29/07/15.
 */
public class DatatableScaffold {
    private boolean pagination;
    private boolean searchable;
    private boolean ordenate;
    private boolean sortable;
    private OrderDatatable order;
    private String sort;
    private TitleScaffold title;
    private int numMaxPaginate;
    private ResourceUrlScaffold resourceUrlScaffold;
    private List<CampoDatatable> columns;
    private Object parent;

    public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isOrdenate() {
		return ordenate;
	}

	public void setOrdenate(boolean ordenate) {
		this.ordenate = ordenate;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public OrderDatatable getOrder() {
		return order;
	}

	public void setOrder(OrderDatatable order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public TitleScaffold getTitle() {
		return title;
	}

	public void setTitle(TitleScaffold title) {
		this.title = title;
	}

	public int getNumMaxPaginate() {
		return numMaxPaginate;
	}

	public void setNumMaxPaginate(int numMaxPaginate) {
		this.numMaxPaginate = numMaxPaginate;
	}

	public ResourceUrlScaffold getResourceUrlScaffold() {
		return resourceUrlScaffold;
	}

	public void setResourceUrlScaffold(ResourceUrlScaffold resourceUrlScaffold) {
		this.resourceUrlScaffold = resourceUrlScaffold;
	}

	public List<CampoDatatable> getColumns() {
		return columns;
	}

	public void setColumns(List<CampoDatatable> columns) {
		this.columns = columns;
	}

	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}

	public ClassScaffold getClassScaffold(){
        if(parent instanceof ClassScaffold){
            return (ClassScaffold) parent;
        }else if(parent instanceof FieldScaffold){
            return ((FieldScaffold)parent).getParent();
        }
		return null;
    }
}

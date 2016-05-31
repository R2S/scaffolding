package biz.r2s.scaffolding.meta.datatatable;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold; 
import biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 29/07/15.
 */
public class CampoDatatable implements Comparable<CampoDatatable>, Cloneable{
	private String name;
	private String title;
	private String key;
	private String length;
	private IconScaffold icon;
	private TypeFieldScaffold typeFieldScaffold;
	private DatatableScaffold parent;
	private Integer order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public IconScaffold getIcon() {
		return icon;
	}

	public void setIcon(IconScaffold icon) {
		this.icon = icon;
	}

	public TypeFieldScaffold getTypeFieldScaffold() {
		return typeFieldScaffold;
	}

	public void setTypeFieldScaffold(TypeFieldScaffold typeFieldScaffold) {
		this.typeFieldScaffold = typeFieldScaffold;
	}

	public DatatableScaffold getParent() {
		return parent;
	}

	public void setParent(DatatableScaffold parent) {
		this.parent = parent;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public int compareTo(CampoDatatable o) {
		return this.order.compareTo(o.order);
	}

	public CampoDatatable clone() {
		try {
			return (CampoDatatable) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

package biz.r2s.scaffolding.meta.button;

import java.util.List;

import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.security.Permission;

/**
 * Created by raphael on 30/03/16.
 */
public abstract class Button implements Cloneable{
    String name;
    String label;
    IconScaffold icon;
    String classCss;
    List<PositionButton> positionsButton;
    TypeActionScaffold actionScaffold;
    Permission permission;
    Object parent;
    public abstract ButtonType getType();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public IconScaffold getIcon() {
		return icon;
	}
	public void setIcon(IconScaffold icon) {
		this.icon = icon;
	}
	public String getClassCss() {
		return classCss;
	}
	public void setClassCss(String classCss) {
		this.classCss = classCss;
	}
	public List<PositionButton> getPositionsButton() {
		return positionsButton;
	}
	public void setPositionsButton(List<PositionButton> positionsButton) {
		this.positionsButton = positionsButton;
	}
	public TypeActionScaffold getActionScaffold() {
		return actionScaffold;
	}
	public void setActionScaffold(TypeActionScaffold actionScaffold) {
		this.actionScaffold = actionScaffold;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public Object getParent() {
		return parent;
	}
	public void setParent(Object parent) {
		this.parent = parent;
	}       
}

package biz.r2s.scaffolding.meta.button;

import java.util.Collections;
import java.util.List;

import biz.r2s.scaffolding.meta.action.TypeActionScaffold;

/**
 * Created by raphael on 31/03/16.
 */
public enum PositionButton {
	DATATABLE_LINE("datatable-line", TypeActionScaffold.LIST), 
	DATATABLE_TOP("datatable-top",	TypeActionScaffold.LIST), 
	INSERT_TOP("insert-top", TypeActionScaffold.CREATE), 
	INSERT_BOTTON("insert-button", TypeActionScaffold.CREATE), 
	UPDATE_TOP("update-top",TypeActionScaffold.EDIT), 
	UPDATE_BOTTON("update-botton", TypeActionScaffold.EDIT), 
	SHOW_TOP("show-top",TypeActionScaffold.VIEW), 
	SHOW_BOTTON("show-botton", TypeActionScaffold.VIEW);

	String position;
	TypeActionScaffold typeActionScaffold;

	PositionButton(String position, TypeActionScaffold typeActionScaffold) {
		this.position = position;
		this.typeActionScaffold = typeActionScaffold;
	}

	public static PositionButton getPosition(String positionA) {
		for (PositionButton positionButton : PositionButton.values()) {
			if (positionButton.equals(positionA)) {
				return positionButton;
			}
		}
		return null;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public TypeActionScaffold getTypeActionScaffold() {
		return typeActionScaffold;
	}

	public void setTypeActionScaffold(TypeActionScaffold typeActionScaffold) {
		this.typeActionScaffold = typeActionScaffold;
	}
	
	public static List<PositionButton> listByTypeActionScaffold(TypeActionScaffold actionScaffold){
		List<PositionButton> positionButtons = new java.util.ArrayList();
		for(PositionButton positionButton: PositionButton.values()){
			if(positionButton.getTypeActionScaffold() == actionScaffold){
				positionButtons.add(positionButton);
			}
		}
		return positionButtons;
	}

}

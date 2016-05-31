package biz.r2s.scaffolding.meta.action;

import biz.r2s.scaffolding.meta.ClassScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class ActionsScaffold {
    ActionScaffold edit;
    ActionScaffold create;
    ActionScaffold delete;
    ActionScaffold show;
    ActionScaffold list;
    ClassScaffold parent;

    public ActionScaffold getEdit() {
		return edit;
	}

	public void setEdit(ActionScaffold edit) {
		this.edit = edit;
	}

	public ActionScaffold getCreate() {
		return create;
	}

	public void setCreate(ActionScaffold create) {
		this.create = create;
	}

	public ActionScaffold getDelete() {
		return delete;
	}

	public void setDelete(ActionScaffold delete) {
		this.delete = delete;
	}

	public ActionScaffold getShow() {
		return show;
	}

	public void setShow(ActionScaffold show) {
		this.show = show;
	}

	public ActionScaffold getList() {
		return list;
	}

	public void setList(ActionScaffold list) {
		this.list = list;
	}

	public ClassScaffold getParent() {
		return parent;
	}

	public void setParent(ClassScaffold parent) {
		this.parent = parent;
	}

	public ActionScaffold getAction(TypeActionScaffold typeActionScaffold) {

        ActionScaffold actionScaffold;
        switch (typeActionScaffold) {
            case CREATE:
                actionScaffold = create;
                break;
            case EDIT:
                actionScaffold = edit;
                break;
            case DELETE:
                actionScaffold = delete;
                break;
            case LIST:
                actionScaffold = list;
                break;
            case VIEW:
                actionScaffold = show;
                break;
            default:
                actionScaffold = null;
        }
        return actionScaffold;
    }

    static String getNameAction(TypeActionScaffold typeActionScaffold) {

        String actionScaffold;
        switch (typeActionScaffold) {
            case CREATE:
                actionScaffold = "create";
                break;
            case EDIT:
                actionScaffold = "edit";
                break;
            case DELETE:
                actionScaffold = "delete";
                break;
            case LIST:
                actionScaffold = "list";
                break;
            case VIEW:
                actionScaffold = "show";
                break;
            default:
                actionScaffold = null;
        }
        return actionScaffold;
    }
}

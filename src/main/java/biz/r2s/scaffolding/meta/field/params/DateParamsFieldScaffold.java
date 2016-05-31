package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class DateParamsFieldScaffold  extends InputParamsFieldScaffold{

    boolean dateEmpty;

    boolean  dateEnd;

    public DateParamsFieldScaffold(){
        this.setCampos("dateEmpty");
        this.setCampos("dateEnd");
    }


    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.DATE;
    }


	public boolean isDateEmpty() {
		return dateEmpty;
	}


	public void setDateEmpty(boolean dateEmpty) {
		this.dateEmpty = dateEmpty;
	}


	public boolean isDateEnd() {
		return dateEnd;
	}


	public void setDateEnd(boolean dateEnd) {
		this.dateEnd = dateEnd;
	}
}

package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class TextParamsFieldScaffold  extends InputParamsFieldScaffold{
    int rows;

    public TextParamsFieldScaffold(){
        this.setCampos("rows");
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.TEXT;
    }

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
    
}

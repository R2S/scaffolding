package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
/**
 * Created by raphael on 24/07/15.
 */
public class NumberPrecisionParamsFieldScaffold extends InputParamsFieldScaffold {

    int precision = 0;

    public NumberPrecisionParamsFieldScaffold() {
        this.setCampos("precision");
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.NUMBER_PRECISION;
    }

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}
    
}

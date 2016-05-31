package biz.r2s.scaffolding.meta.field.params;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class StaticParamsFieldScaffold implements ParamsFieldScaffold {

    public FieldScaffold getParent() {
		return parent;
	}

	public void setParent(FieldScaffold parent) {
		this.parent = parent;
	}

	FieldScaffold parent;

    @Override
    public boolean validate(HashMap<String,Object> params) {
    	return true;
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.STATIC;
    }
    
    @Override
    public Map<String, Object> format() {
    	return Collections.emptyMap();
    }
}

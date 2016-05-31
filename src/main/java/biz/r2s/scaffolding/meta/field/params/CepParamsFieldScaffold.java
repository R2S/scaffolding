package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class CepParamsFieldScaffold  extends InputParamsFieldScaffold{
    
    public CepParamsFieldScaffold(){
        this.setMask("99999-999");
    }
    
    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.INPUT;
    }
}

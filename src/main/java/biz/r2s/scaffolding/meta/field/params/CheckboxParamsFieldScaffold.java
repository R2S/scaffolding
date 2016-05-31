package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class CheckboxParamsFieldScaffold extends InputParamsFieldScaffold{
    IconScaffold on;
    IconScaffold of;
    public CheckboxParamsFieldScaffold(){
        this.setCampos("on");
        this.setCampos("of");
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.CHECKBOX;
    }
}

package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
class HiddenParamsFieldScaffold extends InputParamsFieldScaffold {

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.HIDDEN;
    }
}

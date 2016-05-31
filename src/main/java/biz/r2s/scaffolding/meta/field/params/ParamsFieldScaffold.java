package biz.r2s.scaffolding.meta.field.params;

import java.util.HashMap;

import biz.r2s.scaffolding.format.ScaffoldFormattable;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public interface ParamsFieldScaffold extends ScaffoldFormattable {
    boolean validate(HashMap<String, Object>  params);
    TypeFieldScaffold getType();
    FieldScaffold getParent();
    void setParent(FieldScaffold parent);
}

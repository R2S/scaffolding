package biz.r2s.scaffolding.format;

import java.util.Collections;
import java.util.Map;

import biz.r2s.scaffolding.meta.MenuScaffold;

/**
 * Created by raphael on 25/09/15.
 */
public class MenuFormat {
    CommonFormat commonFormat;
    public MenuFormat(){
        commonFormat = new CommonFormat();
    }

    public Map formatMenu(MenuScaffold menuScaffold){
        Map meta = new java.util.HashMap();
        meta.put("root", menuScaffold.getRoot());
        meta.put("title", commonFormat.formatTitle(menuScaffold.getTitle()));
        meta.put("icon", commonFormat.formatIcon(menuScaffold.getIcon()));
        return meta;
    }
}

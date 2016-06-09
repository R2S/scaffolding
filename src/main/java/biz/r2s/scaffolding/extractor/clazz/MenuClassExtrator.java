package  biz.r2s.scaffolding.extractor.clazz;

import  biz.r2s.scaffolding.meta.MenuScaffold;
import  biz.r2s.scaffolding.meta.TitleScaffold;
import  biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 25/09/15.
 */
public class MenuClassExtrator {
    TitleClassExtractor titleClassExtractor;
    public MenuClassExtrator(){
        titleClassExtractor = new TitleClassExtractor();
    }

    public MenuScaffold getMenu(Class domainClass){
        MenuScaffold menuScaffold = new MenuScaffold();
        menuScaffold.setIcon(this.getIcon(domainClass));
        menuScaffold.setRoot(this.getRoot(domainClass));
        menuScaffold.setTitle(this.getTitle(domainClass));
        menuScaffold.setEnabled(true);
        return menuScaffold;
    }

    private TitleScaffold getTitle(Class domainClass){
        return titleClassExtractor.getTitle(domainClass);
    }

    private IconScaffold getIcon(Class domainClass){
        IconScaffold icon = new IconScaffold();
        return icon;
    }

    String getRoot(Class domainClass){
        return "";
    }
}

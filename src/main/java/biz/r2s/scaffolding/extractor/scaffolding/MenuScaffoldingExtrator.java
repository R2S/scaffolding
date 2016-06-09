package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Map;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.MenuScaffold;
import biz.r2s.scaffolding.meta.TitleScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 06/08/15.
 */
class MenuScaffoldingExtrator {
    TitleScaffoldingExtrator titleScaffoldingExtrator;
    IconScaffoldingExtrator iconScaffoldingExtrator;

    public MenuScaffoldingExtrator() {
        titleScaffoldingExtrator = new TitleScaffoldingExtrator();
        iconScaffoldingExtrator = new IconScaffoldingExtrator();
    }

    public void changeMenu(Map<String, Object> classScaffolding, ClassScaffold classScaffold) {
        Object menuValue = classScaffolding.get("menu");
        if (menuValue!=null) {
            MenuScaffold menuScaffold = classScaffold.getMenu();
            if (menuValue instanceof String) {
                menuScaffold.getTitle().setName((String) menuValue);
            } else if (menuValue instanceof Boolean) {
                menuScaffold.setEnabled((boolean) menuValue);
            } else if (menuValue instanceof Map) {
                changeTitle((Map) menuValue, menuScaffold);
                changeIcon((Map) menuValue, menuScaffold);
                changeRoot((Map) menuValue, menuScaffold);
                changeEnabled((Map) menuValue, menuScaffold);
                changeKey((Map) menuValue, menuScaffold);
            }
        }else{
            TitleScaffold titleScaffold = classScaffold.getMenu().getTitle();

            if(classScaffold.getName()!=null){
                titleScaffold = new TitleScaffold();
                		titleScaffold.setName(classScaffold.getName());
            }else if(classScaffold.getTitle().getName()!=null){
                titleScaffold = classScaffold.getTitle();
            }
            classScaffold.getMenu().setTitle(titleScaffold);
        }
    }

    void changeTitle(Map menuScaffolding, MenuScaffold menuScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(menuScaffolding);
        if (titleScaffold!=null) {
            menuScaffold.setTitle(titleScaffold);
        }
    }

    void changeIcon(Map menuScaffolding, MenuScaffold menuScaffold){
        IconScaffold icon = iconScaffoldingExtrator.getIcon(menuScaffolding);
        if(icon !=null){
            menuScaffold.setIcon(icon);
        }
    }

    void changeRoot(Map menuScaffolding, MenuScaffold menuScaffold){
        String rootValue = (String) menuScaffolding.get("root");
        if(rootValue !=null){
            menuScaffold.setRoot(rootValue);
        }
    }

    void changeKey(Map menuScaffolding, MenuScaffold menuScaffold){
        String keyValue = (String) menuScaffolding.get("key");
        if(keyValue !=null){
            menuScaffold.setKey(keyValue);
        }
    }

    void changeEnabled(Map menuScaffolding, MenuScaffold menuScaffold){
        Boolean enabledValue = (Boolean) menuScaffolding.get("enabled");
        if(enabledValue !=null){
            menuScaffold.setEnabled(enabledValue); 
        }
    }
}

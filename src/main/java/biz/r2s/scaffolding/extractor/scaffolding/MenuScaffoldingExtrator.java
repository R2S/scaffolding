package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.MenuScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold

/**
 * Created by raphael on 06/08/15.
 */
class MenuScaffoldingExtrator {
    TitleScaffoldingExtrator titleScaffoldingExtrator
    IconScaffoldingExtrator iconScaffoldingExtrator

    public MenuScaffoldingExtrator() {
        titleScaffoldingExtrator = new TitleScaffoldingExtrator()
        iconScaffoldingExtrator = new IconScaffoldingExtrator()
    }

    void changeMenu(def classScaffolding, ClassScaffold classScaffold) {
        def menuValue = classScaffolding.get("menu")
        if (menuValue) {
            MenuScaffold menuScaffold = classScaffold.menu
            if (menuValue instanceof String) {
                menuScaffold.title.name = menuValue
            } else if (menuValue instanceof Boolean) {
                menuScaffold.enabled = menuValue
            } else if (menuValue instanceof Map) {
                changeTitle(menuValue, menuScaffold)
                changeIcon(menuValue, menuScaffold)
                changeRoot(menuValue, menuScaffold)
                changeEnabled(menuValue, menuScaffold)
                changeKey(menuValue, menuScaffold)
            }
        }else{
            TitleScaffold titleScaffold = classScaffold.menu?.title

            if(classScaffold.name){
                titleScaffold = new TitleScaffold(name: classScaffold.name)
            }else if(classScaffold?.title?.name){
                titleScaffold = classScaffold?.title
            }
            classScaffold.menu?.title = titleScaffold
        }
    }

    void changeTitle(def menuScaffolding, MenuScaffold menuScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(menuScaffolding)
        if (titleScaffold) {
            menuScaffold.title = titleScaffold
        }
    }

    void changeIcon(def menuScaffolding, MenuScaffold menuScaffold){
        def icon = iconScaffoldingExtrator.getIcon(menuScaffolding)
        if(icon !=null){
            menuScaffold.icon = icon
        }
    }

    void changeRoot(def menuScaffolding, MenuScaffold menuScaffold){
        def rootValue = menuScaffolding.get("root")
        if(rootValue !=null){
            menuScaffold.root = rootValue
        }
    }

    void changeKey(def menuScaffolding, MenuScaffold menuScaffold){
        def keyValue = menuScaffolding.get("key")
        if(keyValue !=null){
            menuScaffold.key = keyValue
        }
    }

    void changeEnabled(def menuScaffolding, MenuScaffold menuScaffold){
        def enabledValue = menuScaffolding.get("enabled")
        if(enabledValue !=null){
            menuScaffold.enabled = enabledValue
        }
    }
}

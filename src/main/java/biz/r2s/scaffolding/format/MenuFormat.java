package br.ufscar.sagui.scaffolding.format

import br.ufscar.sagui.scaffolding.meta.MenuScaffold

/**
 * Created by raphael on 25/09/15.
 */
class MenuFormat {
    CommonFormat commonFormat
    public MenuFormat(){
        commonFormat = new CommonFormat()
    }

    def formatMenu(MenuScaffold menuScaffold){
        def meta = [:]
        meta.root = menuScaffold.root
        meta.title = commonFormat.formatTitle(menuScaffold.title)
        meta.icon = commonFormat.formatIcon(menuScaffold.icon)
        return meta
    }
}

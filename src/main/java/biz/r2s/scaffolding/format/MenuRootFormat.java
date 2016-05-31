package br.ufscar.sagui.scaffolding.format

import br.ufscar.sagui.scaffolding.meta.icon.IconScaffold
import br.ufscar.sagui.scaffolding.meta.icon.PositionIcon
import br.ufscar.sagui.scaffolding.meta.icon.TypeIcon
import br.ufscar.sagui.util.I18nUtils
import grails.util.GrailsNameUtils

/**
 * Created by raphael on 25/09/15.
 */
class MenuRootFormat {
    CommonFormat commonFormat

    static messageKeyName = "menu.:name.title"
    static messageKeyIconName = "menu.:name.icon.name"
    static messageKeyIconType = "menu.:name.icon.type"
    static messageKeyIconPosicion = "menu.:name.icon.position"

    public MenuRootFormat(){
        commonFormat = new CommonFormat()
    }
    public def formatMenus(List<Map> menus){
        menus.groupBy {it.root}.collect {
            def obj = getMenuRoot(it.key)
            obj.items = it.value.sort{item -> item.name}
            return obj
        }
    }

    public def getMenuRoot(String key){
        [name : this.getNameMenu(key),
         key  : key,
         icon: getIconMenu(key)]
    }

    private String getNameMenu(String key){
        String name = I18nUtils.getMessage(messageKeyName, key)

        if(!name){
            name = GrailsNameUtils.getNaturalName(key)
        }
        return name
    }

    private def getIconMenu(String key){
        String nameIcon = I18nUtils.getMessage(messageKeyIconName, key)
        String typeIcon = I18nUtils.getMessage(messageKeyIconType, key)
        String positionIcon = I18nUtils.getMessage(messageKeyIconPosicion, key)

        IconScaffold iconScaffold = new IconScaffold(
                name: nameIcon,
                type: typeIcon?getTypeIcon(typeIcon.toUpperCase()):null,
                position: positionIcon?getPositionIcon(positionIcon.toUpperCase()):null
        )

        return commonFormat.formatIcon(iconScaffold)

    }

    TypeIcon getTypeIcon(String typeIcon){
        try{
            TypeIcon.valueOf(typeIcon.toUpperCase())
        }catch (IllegalArgumentException e){
            return null
        }
    }

    PositionIcon getPositionIcon(String typeIcon){
        try{
            PositionIcon.valueOf(typeIcon.toUpperCase())
        }catch (IllegalArgumentException e){
            return null
        }
    }
}

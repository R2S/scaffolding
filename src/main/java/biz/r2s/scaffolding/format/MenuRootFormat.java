package biz.r2s.scaffolding.format;

import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.icon.PositionIcon;
import biz.r2s.scaffolding.meta.icon.TypeIcon;

/**
 * Created by raphael on 25/09/15.
 */
class MenuRootFormat {
    CommonFormat commonFormat;

    static String messageKeyName = "menu.:name.title";
    static String messageKeyIconName = "menu.:name.icon.name";
    static String messageKeyIconType = "menu.:name.icon.type";
    static String messageKeyIconPosicion = "menu.:name.icon.position";

    public MenuRootFormat(){
        commonFormat = new CommonFormat();
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
            return TypeIcon.valueOf(typeIcon.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    PositionIcon getPositionIcon(String typeIcon){
        try{
        	return PositionIcon.valueOf(typeIcon.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}

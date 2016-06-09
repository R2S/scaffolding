package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Map;

import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.icon.PositionIcon;
import biz.r2s.scaffolding.meta.icon.TypeIcon;

/**
 * Created by raphael on 06/08/15.
 */
public class IconScaffoldingExtrator {
    public IconScaffold getIcon(Map<String, Object> iconConfig){
        IconScaffold icon = null;
        Object iconValue = iconConfig.get("icon");
        if(iconValue!=null){
            icon = new IconScaffold();
            if(iconValue instanceof String){
                icon.setName((String) iconValue);
                icon.setPosition(PositionIcon.LEFT);
                icon.setType(TypeIcon.SLI);
            }
            else if(iconValue instanceof Map){
            	Map iconMap = (Map) iconValue;
                icon.setName((String) iconMap.get("name"));
                icon.setPosition((PositionIcon) iconMap.get("position"));
                icon.setType(getType(iconMap.get("type")));
            }
        }
        return icon;
    }

    private TypeIcon getType(Object typeIconE){
        TypeIcon typeIcon = TypeIcon.SLI;
        if(typeIconE!=null){
            if(typeIconE instanceof String && TypeIcon.valueOf(((String) typeIconE).toUpperCase())!=null){
                typeIcon = TypeIcon.valueOf(((String) typeIconE).toUpperCase());
            }else if(typeIconE instanceof TypeIcon){
                typeIcon = (TypeIcon) typeIconE;
            }
        }

        return typeIcon;
    }

    private PositionIcon getPosition(Object positionIconE){
        PositionIcon positionIcon = PositionIcon.LEFT;
        if(positionIconE != null){
            if(positionIconE instanceof String&&PositionIcon.valueOf(((String) positionIconE).toUpperCase())!=null){
                positionIcon = PositionIcon.valueOf(((String) positionIconE).toUpperCase());
            }else if(positionIconE instanceof PositionIcon){
                positionIcon = (PositionIcon) positionIconE;
            }
        }
        return positionIcon;
    }
}

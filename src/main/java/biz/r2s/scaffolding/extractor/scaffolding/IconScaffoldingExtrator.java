package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.icon.IconScaffold
import br.ufscar.sagui.scaffolding.meta.icon.PositionIcon
import br.ufscar.sagui.scaffolding.meta.icon.TypeIcon

/**
 * Created by raphael on 06/08/15.
 */
class IconScaffoldingExtrator {
    IconScaffold getIcon(def iconConfig){
        IconScaffold icon = null
        def iconValue = iconConfig.get("icon")
        if(iconValue){
            icon = new IconScaffold()
            if(iconValue instanceof String){
                icon.name = iconValue
                icon.position = PositionIcon.LEFT
                icon.type = TypeIcon.SLI
            }
            else if(iconValue instanceof Map){
                icon.name = iconValue.name
                icon.position = getPosition(iconValue.position)
                icon.type = getType(iconValue.type)
            }
        }
        return icon
    }

    private TypeIcon getType(def typeIconE){
        TypeIcon typeIcon = TypeIcon.SLI
        if(typeIconE){
            if(typeIconE instanceof String&&TypeIcon.valueOf(typeIconE.toUpperCase())){
                typeIcon = TypeIcon.valueOf(typeIconE.toUpperCase())
            }else if(typeIconE instanceof TypeIcon){
                typeIcon = typeIconE
            }
        }

        return typeIcon
    }

    private PositionIcon getPosition(def positionIconE){
        PositionIcon positionIcon = PositionIcon.LEFT
        if(positionIconE){
            if(positionIconE instanceof String&&PositionIcon.valueOf(positionIconE.toUpperCase())){
                positionIcon = TypeIcon.valueOf(positionIconE.toUpperCase())
            }else if(positionIconE instanceof PositionIcon){
                positionIcon = positionIconE
            }
        }
        return positionIcon
    }
}

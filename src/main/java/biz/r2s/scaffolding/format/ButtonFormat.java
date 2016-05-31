package br.ufscar.sagui.scaffolding.format

import br.ufscar.sagui.scaffolding.RulesFacade
import br.ufscar.sagui.scaffolding.meta.ResourceUrl
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.button.*
import org.apache.commons.codec.language.bm.Rule

/**
 * Created by raphael on 04/04/16.
 */
class ButtonFormat {
    CommonFormat commonFormat

    public ButtonFormat(){
        commonFormat=  new CommonFormat()
    }

    def formatButtons(def permission, List<Button> buttons, TypeActionScaffold actionScaffold, def fatherId) {
        def positionButtonMeta = [:]

        RulesFacade.getInstance().getButtons(permission, buttons, actionScaffold).each { PositionButton positionButton, List<Button> buttonsF ->
            positionButtonMeta.put(positionButton, buttonsF.collect({this.formatButton(it, fatherId)}))
        }
        return positionButtonMeta
    }

    def formatUrlActionButton(ButtonAction action, def fatherId = null) {
        ResourceUrl.formatUrl(action.url, action.httpMethod)
    }

    def formatButton(Button button, def fatherId = null){
        Map buttonMap = formatButtonBasic(button)

        switch (button.getType()){
            case ButtonType.ACTION:
                buttonMap.putAll(this.formatButtonAction(button, fatherId))
                break;
            case ButtonType.REDIRECT:
                buttonMap.putAll(this.formatButtonRedirect(button, fatherId))
                break;
            case ButtonType.INTERNAL:
                buttonMap.putAll(this.formatButtonInternal(button, fatherId))
                break;
            case ButtonType.HASMANY_EDIT:
                buttonMap.putAll(this.formatButtonHasManyEdit(button, fatherId))
                break;
        }
        return buttonMap
    }

    def formatButtonBasic(Button button){
        def meta = [:]
        meta.name = button.name
        meta.label = button.label
        meta.type = button.type.toString()
        meta.classCss = button.classCss
        //meta.positions = button.positionsButton.collect({it.toString()})
        if (button.icon) {
            meta.icon = commonFormat.formatIcon(button.icon)
        }
        return meta
    }

    def formatButtonAction(ButtonAction button, def fatherId = null){
        def meta = [:]
        meta.url = this.formatUrlActionButton(button, fatherId)
        meta.confirmation = button.confirmation
        return meta
    }

    def formatButtonRedirect(ButtonRedirect button, def fatherId = null){
        def meta = [:]
        meta.rota = button.rota
        return meta
    }

    def formatButtonInternal(ButtonInternal button, def fatherId = null){
        def meta = [:]
        meta.function = [name: button.function, params: button.params]
        return meta
    }

    def formatButtonHasManyEdit(ButtonHasManyEdit button, def fatherId = null){
        return [:]
    }

}

package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.button.*
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.security.Permission
import br.ufscar.sagui.scaffolding.meta.security.PermissionField
import br.ufscar.sagui.util.ClosureUtil
import org.springframework.http.HttpMethod

/**
 * Created by raphael on 05/04/16.
 */
class ButtonsScaffoldingExtrator {
    IconScaffoldingExtrator iconScaffoldingExtrator
    PermissionScaffoldingExtrator permissionScaffoldingExtrator

    public ButtonsScaffoldingExtrator() {
        iconScaffoldingExtrator = new IconScaffoldingExtrator()
    }

    void changeAndInsertButtons(def classScaffolding, ClassScaffold classScaffold) {
        def buttonsValue = classScaffolding.get("buttons")
        if (buttonsValue) {
            Map buttonsMap = ClosureUtil.toMapConfig(buttonsValue)
            buttonsMap.each { key, value ->
                Button button = classScaffold.buttons.find({ it.name.equals(key) })
                String typeValue = value.get("type")
                if(typeValue){
                    ButtonType buttonType = ButtonType.valueOf(typeValue.toUpperCase())
                    Button button2 = this.getByType(buttonType)
                    if(button){
                        this.copyButton(button, button2)
                        int index = classScaffold.getButtons().indexOf(button)
                        classScaffold.getButtons().remove(index)
                        classScaffold.getButtons().add(index,button2)
                    }else{
                        button2.setName(key)
                        classScaffold.getButtons().add(button2)
                    }
                    button = button2
                }
                this.changeActionScaffold(value, button)
                this.changeClassCss(value, button)
                this.changeIcon(value, button)
                this.changeLabel(value, button)
                this.changePosition(value, button)
                this.changeByType(value, button)
                this.changePermission(value, button)
            }
        }
    }

    void copyButton(Button buttonCopy, Button buttonPaste){
        buttonPaste.name = buttonCopy.name
        buttonPaste.label = buttonCopy.label
        buttonPaste.icon = buttonCopy.icon
        buttonPaste.classCss = buttonCopy.classCss
        buttonPaste.positionsButton = buttonCopy.positionsButton
        buttonPaste.actionScaffold = buttonCopy.actionScaffold
        buttonPaste.parent = buttonCopy.parent
    }

    Button getByType(ButtonType buttonType) {
        Button button2 = null
        if (buttonType == ButtonType.REDIRECT) {
            button2 = new ButtonRedirect()
        } else if (buttonType == ButtonType.INTERNAL) {
            button2 = new ButtonInternal()
        } else {
            button2 = new ButtonAction()
        }
        button2.setActionScaffold(TypeActionScaffold.LIST)
        return button2
    }

    void changeByType(def buttonScaffolding, Button button) {
        if (button.getType() == ButtonType.REDIRECT) {
            this.changeByTypeRedirect(buttonScaffolding, button)
        } else if (button.getType() == ButtonType.INTERNAL) {
            this.changeByTypeInternal(buttonScaffolding, button)
        } else {
            this.changeByTypeAction(buttonScaffolding, button)
        }
    }

    void changeByTypeAction(def buttonScaffolding, ButtonAction button) {
        this.changeUrl(buttonScaffolding, button)
        this.changeMethod(buttonScaffolding, button)
        this.changeConfirmation(buttonScaffolding, button)
    }

    void changeByTypeRedirect(def buttonScaffolding, ButtonRedirect button) {
        this.changeRota(buttonScaffolding, button)
    }

    void changeByTypeInternal(def buttonScaffolding, ButtonInternal button) {
        this.changeParams(buttonScaffolding, button)
        this.changeFunction(buttonScaffolding, button)
    }

    void changeFunction(def buttonScaffolding, ButtonInternal button) {
        def functionValue = buttonScaffolding.get("function")
        if (functionValue) {
            button.function = functionValue
        }
    }

    void changeParams(def buttonScaffolding, ButtonInternal button) {
        def paramsValue = buttonScaffolding.get("params")
        if (paramsValue) {
            button.params = paramsValue
        }
    }

    void changeRota(def buttonScaffolding, ButtonRedirect button) {
        def rotaValue = buttonScaffolding.get("rota")
        if (rotaValue) {
            button.rota = rotaValue
        }
    }

    void changeUrl(def buttonScaffolding, ButtonAction button) {
        def urlValue = buttonScaffolding.get("url")
        if (urlValue) {
            button.url = urlValue
        }
    }

    void changeConfirmation(def buttonScaffolding, ButtonAction button) {
        def confirmationValue = buttonScaffolding.get("confirmation")
        if (confirmationValue) {
            button.confirmation = confirmationValue
        }
    }

    void changeMethod(def buttonScaffolding, ButtonAction button) {
        String methodValue = buttonScaffolding.get("method")
        if (methodValue) {
            button.httpMethod = HttpMethod.valueOf(methodValue.toUpperCase())
        }
    }

    void changeLabel(def buttonScaffolding, Button button) {
        def labelValue = buttonScaffolding.get("label")
        if (labelValue) {
            button.label = labelValue
        }
    }

    void changeActionScaffold(def buttonScaffolding, Button button) {
        String actionValue = buttonScaffolding.get("action")
        if (actionValue) {
            button.actionScaffold = TypeActionScaffold.valueOf(actionValue.toUpperCase())
        }

    }

    void changeClassCss(def buttonScaffolding, Button button) {
        def classCssValue = buttonScaffolding.get("classCss")
        if (classCssValue) {
            button.classCss = classCssValue
        }
    }

    void changeIcon(def buttonScaffolding, Button button) {
        if(buttonScaffolding.get("icon")){
            button.icon = iconScaffoldingExtrator.getIcon(buttonScaffolding)
        }
    }

    void changePosition(def buttonScaffolding, Button button) {
        def positionsValue = buttonScaffolding.get("positions")
        if (positionsValue && positionsValue instanceof Collection) {
            List<PositionButton> positionButtons = []
            for (String position : positionsValue) {
                PositionButton positionButton = PositionButton.getPosition(position)
                if (positionButton) {
                    positionButtons << positionButton
                }
            }
            button.positionsButton = positionButtons
        }
    }

    void changePermission(def buttonScaffolding, Button button){
        def permissionScaffolding = buttonScaffolding.get("permission")
        if(permissionScaffolding) {
            if(!button.permission) {
                button.permission = new Permission()
            }
            permissionScaffoldingExtrator.changePermission(permissionScaffolding, button.permission)
        }
    }
}


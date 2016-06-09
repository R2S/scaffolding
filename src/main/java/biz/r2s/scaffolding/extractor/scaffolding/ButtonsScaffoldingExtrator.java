package biz.r2s.scaffolding.extractor.scaffolding;

import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.HttpMethod;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.button.*;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.security.Permission;
import biz.r2s.scaffolding.meta.security.PermissionField;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Created by raphael on 05/04/16.
 */
public class ButtonsScaffoldingExtrator {
    IconScaffoldingExtrator iconScaffoldingExtrator;
    PermissionScaffoldingExtrator permissionScaffoldingExtrator;

    public ButtonsScaffoldingExtrator() {
        iconScaffoldingExtrator = new IconScaffoldingExtrator();
    }

    public void changeAndInsertButtons(Map<String, Object> classScaffolding, ClassScaffold classScaffold) {
        Object buttonsValue = classScaffolding.get("buttons");
        if (buttonsValue!=null) {
        	Map<String, Object> buttonsMap = (Map<String, Object>) buttonsValue;
        	for(final String key:buttonsMap.keySet()){
        		Map<String, Object> value = (Map<String, Object>) buttonsMap.get(key);
        		Button button = (Button) CollectionUtils.find(classScaffold.getButtons(), new Predicate() {					
					@Override
					public boolean evaluate(Object arg0) {
						Button button = (Button) arg0;
						return button.getName().equals(key);
					}
				});
                String typeValue = (String) value.get("type");
                        if(typeValue!=null){
                            ButtonType buttonType = ButtonType.valueOf(typeValue.toUpperCase());
                            Button button2 = this.getByType(buttonType);
                            if(button!=null){
                                this.copyButton(button, button2);
                                int index = classScaffold.getButtons().indexOf(button);
                                classScaffold.getButtons().remove(index);
                                classScaffold.getButtons().add(index,button2);
                            }else{
                                button2.setName(key);
                                classScaffold.getButtons().add(button2);
                            }
                            button = button2;
                        }
                        this.changeActionScaffold(value, button);
                        this.changeClassCss(value, button);
                        this.changeIcon(value, button);
                        this.changeLabel(value, button);
                        this.changePosition(value, button);
                        this.changeByType(value, button);
                        this.changePermission(value, button);	
        	}
        	
        }
    }

    public void copyButton(Button buttonCopy, Button buttonPaste){
        buttonPaste.setName(buttonCopy.getName());
        buttonPaste.setLabel(buttonCopy.getLabel());
        buttonPaste.setIcon(buttonCopy.getIcon());
        buttonPaste.setClassCss(buttonCopy.getClassCss());
        buttonPaste.setPositionsButton(buttonCopy.getPositionsButton());
        buttonPaste.setActionScaffold(buttonCopy.getActionScaffold());
        buttonPaste.setParent(buttonCopy.getParent());
    }

    public Button getByType(ButtonType buttonType) {
        Button button2 = null;
        if (buttonType == ButtonType.REDIRECT) {
            button2 = new ButtonRedirect();
        } else if (buttonType == ButtonType.INTERNAL) {
            button2 = new ButtonInternal();
        } else {
            button2 = new ButtonAction();
        }
        button2.setActionScaffold(TypeActionScaffold.LIST);
        return button2;
    }

    public void changeByType(Map<String, Object> buttonScaffolding, Button button) {
        if (button.getType() == ButtonType.REDIRECT) {
            this.changeByTypeRedirect(buttonScaffolding, (ButtonRedirect) button);
        } else if (button.getType() == ButtonType.INTERNAL) {
            this.changeByTypeInternal(buttonScaffolding, (ButtonInternal) button);
        } else {
            this.changeByTypeAction(buttonScaffolding, (ButtonAction) button);
        }
    }

    public void changeByTypeAction(Map<String, Object> buttonScaffolding, ButtonAction button) {
        this.changeUrl(buttonScaffolding, button);
        this.changeMethod(buttonScaffolding, button);
        this.changeConfirmation(buttonScaffolding, button);
    }

    public void changeByTypeRedirect(Map<String, Object> buttonScaffolding, ButtonRedirect button) {
        this.changeRota(buttonScaffolding, button);
    }

    public void changeByTypeInternal(Map<String, Object> buttonScaffolding, ButtonInternal button) {
        this.changeParams(buttonScaffolding, button);
        this.changeFunction(buttonScaffolding, button);
    }

    public void changeFunction(Map<String, Object> buttonScaffolding, ButtonInternal button) {
    	String functionValue = (String) buttonScaffolding.get("function");
        if (functionValue!=null) {
            button.setFunction(functionValue);
        }
    }

    public void changeParams(Map<String, Object> buttonScaffolding, ButtonInternal button) {
        List<String> paramsValue = (List<String>) buttonScaffolding.get("params");
        if (paramsValue!=null) {
            button.setParams(paramsValue);
        }
    }

    public void changeRota(Map<String, Object> buttonScaffolding, ButtonRedirect button) {
        String rotaValue = (String) buttonScaffolding.get("rota");
        if (rotaValue!=null) {
            button.setRota(rotaValue);
        }
    }

    public void changeUrl(Map<String, Object> buttonScaffolding, ButtonAction button) {
        String urlValue = (String) buttonScaffolding.get("url");
        if (urlValue!=null) {
            button.setUrl(urlValue);
        }
    }

    public void changeConfirmation(Map<String, Object> buttonScaffolding, ButtonAction button) {
        Boolean confirmationValue = (Boolean) buttonScaffolding.get("confirmation");
        if (confirmationValue!=null) {
            button.setConfirmation(confirmationValue);
        }
    }

    public void changeMethod(Map<String, Object> buttonScaffolding, ButtonAction button) {
        String methodValue = (String) buttonScaffolding.get("method");
        if (methodValue!=null) {
            button.setHttpMethod(HttpMethod.valueOf(methodValue.toUpperCase()));
        }
    }

    public void changeLabel(Map<String, Object> buttonScaffolding, Button button) {
        String labelValue = (String) buttonScaffolding.get("label");
        if (labelValue!=null) {
            button.setLabel(labelValue);
        }
    }

    public void changeActionScaffold(Map<String, Object> buttonScaffolding, Button button) {
        String actionValue = (String) buttonScaffolding.get("action");
        if (actionValue!=null) {
            button.setActionScaffold(TypeActionScaffold.valueOf(actionValue.toUpperCase()));
        }

    }

    public void changeClassCss(Map<String, Object> buttonScaffolding, Button button) {
        String classCssValue = (String) buttonScaffolding.get("classCss");
        if (classCssValue!=null) {
            button.setClassCss(classCssValue);
        }
    }

    public void changeIcon(Map<String, Object> buttonScaffolding, Button button) {
        if(buttonScaffolding.get("icon") != null){
            button.setIcon(iconScaffoldingExtrator.getIcon(buttonScaffolding));
        }
    }

    public void changePosition(Map<String, Object> buttonScaffolding, Button button) {
        List<String> positionsValue = (List<String>) buttonScaffolding.get("positions");
        if (positionsValue!=null && positionsValue instanceof Collection) {
            List<PositionButton> positionButtons = Collections.emptyList();
            for (String position : positionsValue) {
                PositionButton positionButton = PositionButton.getPosition(position);
                if (positionButton!=null) {
                    positionButtons.add(positionButton);
                }
            }
            button.setPositionsButton(positionButtons);
        }
    }

    public void changePermission(Map<String, Object> buttonScaffolding, Button button){
    	Map<String, Object> permissionScaffolding = (Map<String, Object>) buttonScaffolding.get("permission");
        if(permissionScaffolding!=null) {
            if(button.getPermission()==null) {
                button.setPermission(new Permission());
            }
            permissionScaffoldingExtrator.changePermission(permissionScaffolding, button.getPermission());
        }
    }
}


package biz.r2s.scaffolding.format;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import biz.r2s.scaffolding.RulesFacade;
import biz.r2s.scaffolding.meta.ResourceUrl;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.button.*;

/**
 * Created by raphael on 04/04/16.
 */
public class ButtonFormat {
    CommonFormat commonFormat;

    public ButtonFormat(){
        commonFormat=  new CommonFormat();
    }

    public Map formatButtons(Map permission, List<Button> buttons, TypeActionScaffold actionScaffold, final Object fatherId) {
        Map positionButtonMeta = Collections.emptyMap();

        Map<PositionButton, List<Button>> buttonsPositions = RulesFacade.getInstance().getButtons(permission, buttons, actionScaffold);
        for(PositionButton positionButton:buttonsPositions.keySet()){
        	List<Button> buttonsF = buttonsPositions.get(positionButton);
        	positionButtonMeta.put(positionButton, CollectionUtils.collect(buttonsF, new Transformer() {				
				@Override
				public Object transform(Object arg0) {
					return formatButton((Button)arg0, fatherId);
				}
			}));
        	
        }
        return positionButtonMeta;
    }
    Map formatUrlActionButton(ButtonAction action) {
        return ResourceUrl.formatUrl(action.getUrl(), action.getHttpMethod());
    }
    Map formatUrlActionButton(ButtonAction action, Object fatherId) {
    	return ResourceUrl.formatUrl(action.getUrl(), action.getHttpMethod());
    }

    Map formatButton(Button button){
    	return formatButton(button, null);
    }
    Map formatButton(Button button, Object fatherId){
        Map buttonMap = formatButtonBasic(button);
        switch (button.getType()){
            case ACTION:
                buttonMap.putAll(this.formatButtonAction((ButtonAction) button, fatherId));
                break;
            case REDIRECT:
                buttonMap.putAll(this.formatButtonRedirect((ButtonRedirect) button, fatherId));
                break;
            case INTERNAL:
                buttonMap.putAll(this.formatButtonInternal((ButtonInternal) button, fatherId));
                break;
            case HASMANY_EDIT:
                buttonMap.putAll(this.formatButtonHasManyEdit((ButtonHasManyEdit) button, fatherId));
                break;
        }
        return buttonMap;
    }

    Map formatButtonBasic(Button button){
    	Map meta = Collections.emptyMap();
        meta.put("name", button.getName());
        meta.put("label", button.getLabel());
        meta.put("type", button.getType().toString());
        meta.put("classCss", button.getClassCss());
        //meta.positions = button.positionsButton.collect({it.toString()})
        if (button.getIcon()!=null) {
            meta.put("icon",commonFormat.formatIcon(button.getIcon()));
        }
        return meta;
    }

    Map formatButtonAction(ButtonAction button, Object fatherId){
    	Map meta = Collections.emptyMap();
        meta.put("url", this.formatUrlActionButton(button, fatherId));
        meta.put("confirmation",button.getConfirmation());
        return meta;
    }

    Map formatButtonRedirect(ButtonRedirect button, Object fatherId){
    	Map meta = Collections.emptyMap();
        meta.put("rota", button.getRota());
        return meta;
    }

    Map formatButtonInternal(ButtonInternal button, Object fatherId){
    	Map meta = Collections.emptyMap();
    	Map function = Collections.emptyMap();
    	function.put("params",button.getFunction());
    	function.put("name", button.getParams());
        meta.put("function", function); 
        return meta;
    }

    Map formatButtonHasManyEdit(ButtonHasManyEdit button, Object fatherId){
        return Collections.emptyMap();
    }

}

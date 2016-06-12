package biz.r2s.scaffolding.format;

import java.util.Collections;
import java.util.Map;

import biz.r2s.core.util.ObjectUtil;
import biz.r2s.scaffolding.RulesFacade;
import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.TitleScaffold;
import biz.r2s.scaffolding.meta.action.ActionScaffold;
import biz.r2s.scaffolding.meta.action.ActionsScaffold;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.icon.TypeIcon;

/**
 * Created by raphael on 11/08/15.
 */
public class CommonFormat {
	public String formatTitle(TitleScaffold ts){
        return ts.getName()+(ts.getSubTitle()!=null?" ("+ts.getSubTitle()+")":"");
    }

    public Map formatIcon(IconScaffold iconScaffold){
    	Map map = new java.util.HashMap();
    	map.put("class", getClassIcon(iconScaffold));
    	map.put("position",iconScaffold.getPosition()!=null?iconScaffold.getPosition().name().toLowerCase():null);
    	return map;
    }

    String getClassIcon(IconScaffold iconScaffold){
        return TypeIcon.format(iconScaffold.getType(), iconScaffold.getName());
    }

    public Map formatActions(Map permission, ActionsScaffold actionsScaffold, ResourceUrlScaffold resourceUrlScaffold, Object fatherId ){
        Map meta = new java.util.HashMap();
        
        meta.put("create",null);
        meta.put("edit",null);
        meta.put("delete",null);
        meta.put("show",null);
        meta.put("list",null);
        Map<String, TypeActionScaffold> actions = RulesFacade.getInstance().getActions(permission);
        for(String nomeAction:actions.keySet()){
        	TypeActionScaffold typeActionScaffold =  actions.get(nomeAction);
        	ActionScaffold actionScaffold = (ActionScaffold) ObjectUtil.getValue(nomeAction, actionsScaffold);
        	if(actionScaffold.isEnabled()){
        		meta.put(nomeAction,formatAction(actionScaffold, resourceUrlScaffold, typeActionScaffold, fatherId));
        	}        	
        }
        return meta;
    }

    Map formatAction(ActionScaffold actionScaffold,  ResourceUrlScaffold resourceUrlScaffold, TypeActionScaffold typeActionScaffold, Object fatherId ){
    	 
    	if(actionScaffold==null){
            return null;
        }

    	Map meta = new java.util.HashMap();
        meta.put("title",this.formatTitle(actionScaffold.getTitle()));
        meta.put("url",resourceUrlScaffold.resolver(typeActionScaffold, fatherId).formatUrl());
        return meta;
    }

    String tratarUrl(String url){
        return url.replace("(*)", ":id");
    }
}

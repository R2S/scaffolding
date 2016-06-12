package  biz.r2s.scaffolding.extractor.clazz;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.r2s.core.util.ClassUtils;
import  biz.r2s.scaffolding.meta.TitleScaffold;
import  biz.r2s.scaffolding.meta.action.ActionScaffold;
import  biz.r2s.scaffolding.meta.action.ActionsScaffold;
import  biz.r2s.scaffolding.meta.action.TypeActionScaffold;

/**
 * Created by raphael on 28/07/15.
 */
public class ActionsClassExtractor {
	public List<String> keyTitleAction = Arrays.asList(":name.:action.title", ":action.title");
    public Map<TypeActionScaffold, String> actionsToName = new HashMap();

    public ActionsClassExtractor(){
        actionsToName.put(TypeActionScaffold.CREATE,"create");
        actionsToName.put(TypeActionScaffold.EDIT,"edit");
        actionsToName.put(TypeActionScaffold.LIST,"list");
        actionsToName.put(TypeActionScaffold.DELETE,"delete");
        actionsToName.put(TypeActionScaffold.VIEW,"show");
    }

    public ActionsScaffold getActions(Class domainClass){
        ActionsScaffold actions = new ActionsScaffold();
        actions.setList(this.getAction(domainClass, TypeActionScaffold.LIST, actions));
        if(!ClassUtils.ehClasseAbstrata(domainClass)){
            actions.setCreate(this.getAction(domainClass, TypeActionScaffold.CREATE, actions));
            actions.setEdit(this.getAction(domainClass, TypeActionScaffold.EDIT, actions));
            actions.setDelete(this.getAction(domainClass, TypeActionScaffold.DELETE, actions));
            actions.setShow(this.getAction(domainClass, TypeActionScaffold.VIEW, actions));
        }

        return actions;
    }


    public ActionScaffold getAction(Class domainClass, TypeActionScaffold action, ActionsScaffold actions){
        ActionScaffold actionScaffold = new ActionScaffold();
        String nameAction = this.actionsToName.get(action);
        actionScaffold.setTitle(this.getTitle(nameAction));
        actionScaffold.setEnabled(true);
        actionScaffold.setParent(actions);
        actionScaffold.setTypeActionScaffold(action);
        return actionScaffold;
    }


    private TitleScaffold getTitle(String nameAction){
        TitleScaffold title = new TitleScaffold();
        title.setName(nameAction);//I18nUtils.getMessage(keyTitleAction*.replaceAll(":action", nameAction))
        title.setSubTitle(null);
        return title;
    }
}

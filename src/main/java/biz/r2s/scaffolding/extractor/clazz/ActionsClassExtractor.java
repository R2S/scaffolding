package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionsScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold as TA
import br.ufscar.sagui.util.ClassUtils
import br.ufscar.sagui.util.I18nUtils
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 28/07/15.
 */
class ActionsClassExtractor {
    private def keyTitleAction = [":name.:action.title", ":action.title"]
    private HashMap<TA, String> actionsToName = [:]

    public ActionsClassExtractor(){
        actionsToName.put(TA.CREATE,"create")
        actionsToName.put(TA.EDIT,"edit")
        actionsToName.put(TA.LIST,"list")
        actionsToName.put(TA.DELETE,"delete")
        actionsToName.put(TA.VIEW,"show")
    }

    public ActionsScaffold getActions(GrailsDomainClass domainClass, ClassScaffold classScaffold=null){
        ActionsScaffold actions = new ActionsScaffold()
        actions.list = this.getAction(domainClass, TypeActionScaffold.LIST, actions)
        if(!ClassUtils.ehClasseAbstrata(domainClass.clazz)){
            actions.create = this.getAction(domainClass, TypeActionScaffold.CREATE, actions)
            actions.edit = this.getAction(domainClass, TypeActionScaffold.EDIT, actions)
            actions.delete = this.getAction(domainClass, TypeActionScaffold.DELETE, actions)
            actions.show = this.getAction(domainClass, TypeActionScaffold.VIEW, actions)
        }

        return actions
    }


    public ActionScaffold getAction(GrailsDomainClass domainClass, TA action, ActionsScaffold actions){
        ActionScaffold actionScaffold = new ActionScaffold()
        String nameAction = this.actionsToName.get(action)
        actionScaffold.setTitle(this.getTitle(nameAction))
        actionScaffold.enabled = true
        actionScaffold.parent = actions
        actionScaffold.typeActionScaffold = action
        return actionScaffold
    }


    private TitleScaffold getTitle(String nameAction){
        TitleScaffold title = new TitleScaffold()
        title.name = I18nUtils.getMessage(keyTitleAction*.replaceAll(":action", nameAction))
        title.subTitle = null
        return title
    }
}

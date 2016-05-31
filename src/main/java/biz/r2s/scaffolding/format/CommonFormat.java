package br.ufscar.sagui.scaffolding.format

import br.ufscar.sagui.scaffolding.RulesFacade
import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionScaffold
import br.ufscar.sagui.scaffolding.meta.action.ActionsScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.icon.IconScaffold
import br.ufscar.sagui.scaffolding.meta.icon.TypeIcon

/**
 * Created by raphael on 11/08/15.
 */
class CommonFormat {
    def formatTitle(TitleScaffold ts){
        return "$ts.name${ts.subTitle?' ('+ts.subTitle+')':''}"
    }

    def formatIcon(IconScaffold iconScaffold){
        return [class:getClassIcon(iconScaffold), position:iconScaffold?.position?.name()?.toLowerCase()]
    }

    def getClassIcon(IconScaffold iconScaffold){
        return TypeIcon.format(iconScaffold.type, iconScaffold.name)
    }

    def formatActions(def permission, ActionsScaffold actionsScaffold, ResourceUrlScaffold resourceUrlScaffold, def fatherId ){
        def meta = [create:null, edit:null, delete:null, show:null, list:null]
        RulesFacade.instance.getActions(permission).each {String nomeAction, TypeActionScaffold typeActionScaffold ->
            ActionScaffold actionScaffold = actionsScaffold."$nomeAction"
           if(actionScaffold.enabled){
                meta."$nomeAction" = formatAction(actionScaffold, resourceUrlScaffold, typeActionScaffold, fatherId)
            }
        }
        return meta
    }

    def formatAction(ActionScaffold actionScaffold,  ResourceUrlScaffold resourceUrlScaffold, TypeActionScaffold typeActionScaffold, def fatherId ){
        if(!actionScaffold){
            return null
        }

        def meta = [:]
        meta.title = this.formatTitle(actionScaffold.title)
        meta.url = resourceUrlScaffold.resolver(typeActionScaffold, fatherId).formatUrl()
        return meta
    }



    def tratarUrl(String url){
        url.replace("(*)", ":id")
    }
}

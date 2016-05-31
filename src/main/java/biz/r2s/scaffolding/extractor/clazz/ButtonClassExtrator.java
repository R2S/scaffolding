package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.scaffolding.interceptor.DomainResource
import br.ufscar.sagui.scaffolding.interceptor.DomainScaffoldStore
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.ResourceUrl
import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.button.*
import br.ufscar.sagui.scaffolding.meta.icon.*
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 05/04/16.
 */
class ButtonClassExtrator {
    List<Button> getButtons(GrailsDomainClass domainClass, ClassScaffold classScaffold){
        ArrayList<Button> buttons = new ArrayList<Button>()
        if(classScaffold.isHasMany){
            buttons.add(this.buttonActionHasManyEdit(domainClass, classScaffold))
            buttons.add(this.buttonActionHasManyDelete(domainClass, classScaffold))
        }else{
            buttons.add(this.buttonActionEdit(domainClass, classScaffold))
            buttons.add(this.buttonActionDelete(domainClass, classScaffold))
        }

        return buttons
    }
    ButtonRedirect buttonActionEdit(GrailsDomainClass clazz, ClassScaffold classScaffold){
        DomainResource domainResource = DomainScaffoldStore.getDomainResourse(clazz)

        ButtonRedirect redirectEdit = new ButtonRedirect()
        redirectEdit.setLabel("")
        redirectEdit.setName("button-edit")
        redirectEdit.setActionScaffold(TypeActionScaffold.EDIT)
        redirectEdit.positionsButton = [PositionButton.DATATABLE_LINE]
        redirectEdit.rota = "/scaffolding/"+domainResource?.key+"/editar/:id"
        IconScaffold iconScaffold = new IconScaffold()
        iconScaffold.name = IconsFA.PENCIL
        iconScaffold.type = TypeIcon.FA
        iconScaffold.position = PositionIcon.LEFT
        redirectEdit.setIcon(iconScaffold)
        return redirectEdit
    }

    ButtonHasManyEdit buttonActionHasManyEdit(GrailsDomainClass clazz, ClassScaffold classScaffold){
        ButtonHasManyEdit hasManyEdit = new ButtonHasManyEdit()
        hasManyEdit.setLabel("")
        hasManyEdit.setName("button-edit-has-many")
        hasManyEdit.setActionScaffold(TypeActionScaffold.EDIT)
        hasManyEdit.positionsButton = [PositionButton.DATATABLE_LINE]
        IconScaffold iconScaffold = new IconScaffold()
        iconScaffold.name = IconsFA.PENCIL
        iconScaffold.type = TypeIcon.FA
        iconScaffold.position = PositionIcon.LEFT
        hasManyEdit.setIcon(iconScaffold)
        return hasManyEdit
    }

    ButtonAction buttonActionDelete(GrailsDomainClass clazz, ClassScaffold classScaffold){
        ButtonAction actionDelete = new ButtonAction()
        actionDelete.setLabel("Deletar")
        actionDelete.setName("button-delete")
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE)
        actionDelete.positionsButton = [PositionButton.UPDATE_TOP]
        actionDelete.setHttpMethod(ResourceUrlScaffold.getHttpMethod(TypeActionScaffold.DELETE))
        ResourceUrlScaffold resourceUrlScaffold = ResourceUrlScaffold.builder(clazz, null)
        ResourceUrl resourceUrl = resourceUrlScaffold.resolver(TypeActionScaffold.DELETE)
        actionDelete.setUrl(resourceUrl.url.toString())
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE)
        IconScaffold iconScaffold = new IconScaffold()
        iconScaffold.name = IconsFA.TRASH
        iconScaffold.type = TypeIcon.FA
        iconScaffold.position = PositionIcon.LEFT
        actionDelete.setIcon(iconScaffold)
        actionDelete.confirmation = true
        return actionDelete
    }

    ButtonAction buttonActionHasManyDelete(GrailsDomainClass clazz, ClassScaffold classScaffold){
        ButtonAction actionDelete = new ButtonAction()
        actionDelete.setLabel("")
        actionDelete.setName("button-delete-has-many")
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE)
        actionDelete.positionsButton = [PositionButton.DATATABLE_LINE]
        actionDelete.setHttpMethod(ResourceUrlScaffold.getHttpMethod(TypeActionScaffold.DELETE))
        ResourceUrlScaffold resourceUrlScaffold = ResourceUrlScaffold.builder(clazz, null)
        ResourceUrl resourceUrl = resourceUrlScaffold.resolver(TypeActionScaffold.DELETE)
        actionDelete.setUrl(resourceUrl.url.toString())
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE)
        IconScaffold iconScaffold = new IconScaffold()
        iconScaffold.name = IconsFA.TRASH
        iconScaffold.type = TypeIcon.FA
        iconScaffold.position = PositionIcon.LEFT
        actionDelete.setIcon(iconScaffold)
        actionDelete.confirmation = true
        return actionDelete
    }
}

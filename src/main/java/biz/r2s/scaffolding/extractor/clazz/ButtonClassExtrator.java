package  biz.r2s.scaffolding.extractor.clazz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import  biz.r2s.scaffolding.interceptor.DomainResource;
import  biz.r2s.scaffolding.interceptor.DomainScaffoldStore;
import  biz.r2s.scaffolding.meta.ClassScaffold;
import  biz.r2s.scaffolding.meta.ResourceUrl;
import  biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import  biz.r2s.scaffolding.meta.action.TypeActionScaffold;
import  biz.r2s.scaffolding.meta.button.*;
import  biz.r2s.scaffolding.meta.icon.*;

/**
 * Created by raphael on 05/04/16.
 */
public class ButtonClassExtrator {
	public List<Button> getButtons(Class domainClass, ClassScaffold classScaffold){
        ArrayList<Button> buttons = new ArrayList<Button>();
        if(classScaffold.isHasMany()){
            buttons.add(this.buttonActionHasManyEdit(domainClass, classScaffold));
            buttons.add(this.buttonActionHasManyDelete(domainClass, classScaffold));
        }else{
            buttons.add(this.buttonActionEdit(domainClass, classScaffold));
            buttons.add(this.buttonActionDelete(domainClass, classScaffold));
        }

        return buttons;
    }
	public ButtonRedirect buttonActionEdit(Class clazz, ClassScaffold classScaffold){
        DomainResource domainResource = DomainScaffoldStore.getDomainResourse(clazz);

        ButtonRedirect redirectEdit = new ButtonRedirect();
        redirectEdit.setLabel("");
        redirectEdit.setName("button-edit");
        redirectEdit.setActionScaffold(TypeActionScaffold.EDIT);
        redirectEdit.setPositionsButton(Arrays.asList(PositionButton.DATATABLE_LINE));
        redirectEdit.setRota("/scaffolding/"+domainResource.getKey()+"/editar/:id");
        IconScaffold iconScaffold = new IconScaffold();
        iconScaffold.setName(IconsFA.PENCIL);
        iconScaffold.setType(TypeIcon.FA);
        iconScaffold.setPosition(PositionIcon.LEFT);
        redirectEdit.setIcon(iconScaffold);
        return redirectEdit;
    }

	public ButtonHasManyEdit buttonActionHasManyEdit(Class clazz, ClassScaffold classScaffold){
        ButtonHasManyEdit hasManyEdit = new ButtonHasManyEdit();
        hasManyEdit.setLabel("");
        hasManyEdit.setName("button-edit-has-many");
        hasManyEdit.setActionScaffold(TypeActionScaffold.EDIT);
        hasManyEdit.setPositionsButton(Arrays.asList(PositionButton.DATATABLE_LINE));
        IconScaffold iconScaffold = new IconScaffold();
        iconScaffold.setName(IconsFA.PENCIL);
        iconScaffold.setType(TypeIcon.FA);
        iconScaffold.setPosition(PositionIcon.LEFT);
        hasManyEdit.setIcon(iconScaffold);
        return hasManyEdit;
    }

	public ButtonAction buttonActionDelete(Class clazz, ClassScaffold classScaffold){
        ButtonAction actionDelete = new ButtonAction();
        actionDelete.setLabel("Deletar");
        actionDelete.setName("button-delete");
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE);
        actionDelete.setPositionsButton(Arrays.asList(PositionButton.UPDATE_TOP));
        actionDelete.setHttpMethod(ResourceUrlScaffold.getHttpMethod(TypeActionScaffold.DELETE));
        ResourceUrlScaffold resourceUrlScaffold = ResourceUrlScaffold.builder(clazz, null);
        ResourceUrl resourceUrl = resourceUrlScaffold.resolver(TypeActionScaffold.DELETE);
        actionDelete.setUrl(resourceUrl.getUrl().toString());
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE);
        IconScaffold iconScaffold = new IconScaffold();
        iconScaffold.setName(IconsFA.TRASH);
        iconScaffold.setType(TypeIcon.FA);
        iconScaffold.setPosition(PositionIcon.LEFT);
        actionDelete.setIcon(iconScaffold);
        actionDelete.setConfirmation(true);
        return actionDelete;
    }

	public ButtonAction buttonActionHasManyDelete(Class clazz, ClassScaffold classScaffold){
        ButtonAction actionDelete = new ButtonAction();
        actionDelete.setLabel("");
        actionDelete.setName("button-delete-has-many");
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE);
        actionDelete.setPositionsButton(Arrays.asList(PositionButton.DATATABLE_LINE));
        actionDelete.setHttpMethod(ResourceUrlScaffold.getHttpMethod(TypeActionScaffold.DELETE));
        ResourceUrlScaffold resourceUrlScaffold = ResourceUrlScaffold.builder(clazz, null);
        ResourceUrl resourceUrl = resourceUrlScaffold.resolver(TypeActionScaffold.DELETE);
        actionDelete.setUrl(resourceUrl.getUrl().toString());
        actionDelete.setActionScaffold(TypeActionScaffold.DELETE);
        IconScaffold iconScaffold = new IconScaffold();
        iconScaffold.setName(IconsFA.TRASH);
        iconScaffold.setType(TypeIcon.FA);
        iconScaffold.setPosition(PositionIcon.LEFT);
        actionDelete.setIcon(iconScaffold);
        actionDelete.setConfirmation(true);
        return actionDelete;
    }
}

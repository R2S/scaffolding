package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.extractor.MetaDomainExtractor;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.StatusClassScaffold;
import biz.r2s.scaffolding.meta.TitleScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import biz.r2s.scaffolding.meta.security.PermissionClass;

/**
 * Created by raphael on 28/07/15.
 */
public class ScaffoldingExtractor implements MetaDomainExtractor {

	static List<String> fieldsConfigInclude = Arrays.asList("name", "title", "actions", "permission", "datatable",
			"menu", "showTransients", "service", "controller", "buttons");

	FieldScaffoldingExtractor fieldScaffoldingExtractor;
	ActionsScaffoldingExtrator actionsScaffoldingExtrator;
	TitleScaffoldingExtrator titleScaffoldingExtrator;
	DataTableScaffoldingExtrator dataTableScaffoldingExtrator;
	PermissionScaffoldingExtrator permissionScaffoldingExtrator;
	MenuScaffoldingExtrator menuScaffoldingExtrator;
	ButtonsScaffoldingExtrator buttonsScaffoldingExtrator;

	public ScaffoldingExtractor() {
		fieldScaffoldingExtractor = new FieldScaffoldingExtractor();
		actionsScaffoldingExtrator = new ActionsScaffoldingExtrator();
		titleScaffoldingExtrator = new TitleScaffoldingExtrator();
		dataTableScaffoldingExtrator = new DataTableScaffoldingExtrator();
		permissionScaffoldingExtrator = new PermissionScaffoldingExtrator();
		menuScaffoldingExtrator = new MenuScaffoldingExtrator();
		buttonsScaffoldingExtrator = new ButtonsScaffoldingExtrator();
	}

	@Override
	public ClassScaffold extractor(Class domainClass, ClassScaffold classScaffold) {
		Object configScaffolding = getConfigScaffoldind(domainClass);
		try {
			if (configScaffolding != null) {
				if (configScaffolding instanceof Boolean) {
					classScaffold.setStatus(
							(boolean) configScaffolding ? StatusClassScaffold.ACTIVE : StatusClassScaffold.DISABLE);
				} else {
					Map classScaffolding = (Map) configScaffolding;
					this.changeName(classScaffolding, classScaffold);
					this.changeTitle(classScaffolding, classScaffold);
					this.actionsScaffoldingExtrator.changeActions(classScaffolding, classScaffold);
					this.changePermission(classScaffolding, classScaffold);
					this.changeDatatable(classScaffolding, classScaffold);
					this.showTransients(classScaffolding, classScaffold);
					this.changeService(classScaffolding, classScaffold);
					this.changeController(classScaffolding, classScaffold);
					this.buttonsScaffoldingExtrator.changeAndInsertButtons(classScaffolding, classScaffold);
					for (int i = 0; i < classScaffold.getFields().size(); i++) {
						FieldScaffold fieldScaffold = classScaffold.getFields().get(i);
						Object fieldScaffolding = classScaffolding.get(fieldScaffold.getKey());
						if (fieldScaffolding != null) {
							fieldScaffold.setOrder(i);
							if (fieldScaffolding instanceof Boolean) {
								fieldScaffold.setScaffold((boolean) fieldScaffolding);
							} else if (fieldScaffolding instanceof Number) {
								fieldScaffold.setOrder((int) fieldScaffolding);
							} else if (fieldScaffolding instanceof String) {
								fieldScaffold.setLabel((String) fieldScaffolding);
							} else {
								fieldScaffoldingExtractor.changeConfigField((Map) fieldScaffolding, fieldScaffold);
							}
						}
					}
					this.changeMenu(classScaffolding, classScaffold);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classScaffold;
	}

	protected Object getConfigScaffoldind(Class domainClass) {
		return null; // this.getClosureScaffoldind(domainClass.clazz)
	}

	void changeTitle(Map classScaffolding, ClassScaffold classScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(classScaffolding);
        if (titleScaffold!=null) {
            classScaffold.setTitle(titleScaffold);
        }
    }

	void changePermission(Map actionScaffolding, ClassScaffold classScaffold){
		Map<String, Object> permissionScaffolding = (Map<String, Object>) actionScaffolding.get("permission");
        if(permissionScaffolding!=null) {
            if(classScaffold.getPermission()==null) {
            	PermissionClass permissionClass = new PermissionClass();
            	permissionClass.setClassScaffold(classScaffold);
                classScaffold.setPermission(permissionClass);
            }
            permissionScaffoldingExtrator.changePermission(permissionScaffolding, classScaffold.getPermission());
        }
    }

	void changeName(Map classScaffolding, ClassScaffold classScaffold) {
        String value = (String) classScaffolding.get("name");
        if (value!=null) {
            classScaffold.setName(value);
            classScaffold.getDatatable().getTitle().setName(value);
        }
    }

	void changeService(Map classScaffolding, ClassScaffold classScaffold) {
        String value = (String) classScaffolding.get("service");
        if (value!=null) {
            classScaffold.setServiceClass(value);
        }
    }

	void changeController(Map classScaffolding, ClassScaffold classScaffold) {
        String value = (String) classScaffolding.get("controller");
        if (value!=null) {
            classScaffold.setControllerClass(value);
        }
    }

	void changeDatatable(Map classScaffolding, ClassScaffold classScaffold) {
        Map value = (Map) classScaffolding.get("datatable");
        if (value!=null) {
            this.dataTableScaffoldingExtrator.changeDatatable(value, classScaffold.getDatatable());
        }
    }

	void showTransients(Map classScaffolding, ClassScaffold classScaffold) {
        Object value = classScaffolding.get("showTransients");
        if(value!=null){
            if (value instanceof Boolean&&(Boolean)value==true) {
            	//TODO: FAZER
            	classScaffold.setTransiendsShow(new ArrayList<String>());/*
                try{
                    classScaffold.transiendsShow =  classScaffold.clazz?.transients
                }catch (e){
                    classScaffold.transiendsShow = []
                }*/
            }else if(value instanceof Collection){
                classScaffold.setTransiendsShow((List<String>) value);
            }
        }
    }

	void changeMenu(Map classScaffolding, ClassScaffold classScaffold){
        menuScaffoldingExtrator.changeMenu(classScaffolding, classScaffold);
    }

	@Override
	public int getOrder() {
		return 1;
	}
}

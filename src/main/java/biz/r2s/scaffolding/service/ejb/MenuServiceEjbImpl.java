package biz.r2s.scaffolding.service.ejb;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.RulesFacade;
import biz.r2s.scaffolding.format.MenuRootFormat;
import biz.r2s.scaffolding.interceptor.DomainResource;
import biz.r2s.scaffolding.interceptor.DomainScaffoldStore;
import biz.r2s.scaffolding.service.MenuService;

public class MenuServiceEjbImpl implements MenuService {
	
	MenuRootFormat menuRootFormat;

	public MenuServiceEjbImpl(){
        menuRootFormat = new MenuRootFormat();
    }
	
	@Override
	public List<Map> listMenu() {
		List<Map> menus = Collections.emptyList();
		for(DomainResource domainResource: DomainScaffoldStore.domainResources){
			if(!domainResource.isHasMamy()&&isPermission(domainResource)&&domainResource.isEnabledMenu()){
                menus.add(domainResource.format());
            }
		}
		return menuRootFormat.formatMenus(menus);
	}
	
	private boolean isPermission(DomainResource domainResource){
        if(!RulesFacade.getInstance().enablePermission()){
            return true;
        }

        if(!RulesFacade.getInstance().enablePermissionMenu()){
            return true;
        }
        return true;
    }

}

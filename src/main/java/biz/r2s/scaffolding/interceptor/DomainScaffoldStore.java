package biz.r2s.scaffolding.interceptor;

import biz.r2s.core.util.NameUtils;
import biz.r2s.scaffolding.builder.ClassScaffoldBuilder;
import biz.r2s.scaffolding.format.MenuFormat;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.security.RolesFacade;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Created by raphael on 07/08/15.
 */
public class DomainScaffoldStore {
	public static List<DomainResource> domainResources = new java.util.ArrayList();
	static String SUFFIX_DEFAULD = "Scaffolding";

	public static List<DomainResource> obterPorNomeResource(final String nome) {
		return (List<DomainResource>) CollectionUtils.select(domainResources, new Predicate() {
			@Override
			public boolean evaluate(Object arg0) {
				return ((DomainResource) arg0).nomeResource == nome;
			}
		});
	}

	public static String getSuffix() {
		return SUFFIX_DEFAULD;
	}

	public static void setDomainResourse(Class domainClass) {
		setDomainResourse(domainClass, null);
	}

	public static void setDomainResourse(Class domainClass, String propertyName) {

		DomainResource domainResource = getDomainResourse(domainClass, propertyName);
		ClassScaffold classScaffold = ClassScaffoldBuilder.getInstance().builder(domainClass);
		MenuFormat menuFormat = new MenuFormat();

		Map menu = menuFormat.formatMenu(classScaffold.getMenu());

		if (domainResource == null) {
			domainResource = new DomainResource();
			domainResource.setKey((String) (menu.get("key") != null ? menu.get("key") : getKey(domainClass)));
			domainResource.setDomainClass(domainClass);
			domainResource.setNomeResource((String) menu.get("title"));
			domainResource.setPropertyName(propertyName);
			domainResource.setUrl(getURL(domainClass, propertyName));
			domainResource.setIcon((Map) menu.get("icon"));
			domainResource.setRoot((String) menu.get("root"));
			domainResource.setTitle((String) menu.get("title"));
			domainResource.setRoles(getRolesAcess(classScaffold, domainClass, propertyName));
			domainResource.setEnabledMenu(classScaffold.getMenu().isEnabled());
			domainResources.add(domainResource);
		} else {
			domainResource.nomeResource = getName(domainClass);
			domainResource.setKey((String) (menu.get("key") != null ? menu.get("key") : getKey(domainClass)));
			domainResource.url = getURL(domainClass, propertyName);
			domainResource.setIcon((Map) menu.get("icon"));
			domainResource.setRoot((String) menu.get("root"));
			domainResource.setTitle((String) menu.get("title"));
			domainResource.setRoles(getRolesAcess(classScaffold, domainClass, propertyName));
			domainResource.setEnabledMenu(classScaffold.getMenu().isEnabled());
		}
	}

	public static List<String> getRolesAcess(ClassScaffold classScaffold, Class domainClass, String propertyName) {
		if (propertyName == null) {
			return null;
		}
		RolesFacade rolesFacade = new RolesFacade();

		Map objeto = rolesFacade.getMetaRoles(classScaffold);

		return (List<String>) objeto.get("roles");
	}

	public static DomainResource getDomainResourse(Class domainClass) {
		return getDomainResourse(domainClass, null);

	}

	public static DomainResource getDomainResourse(final Class domainClass, final String propertyName) {
		return (DomainResource) CollectionUtils.find(domainResources, new Predicate() {
			@Override
			public boolean evaluate(Object arg0) {
				DomainResource domainResource = (DomainResource) arg0;
				return domainResource.getDomainClass().equals(domainClass)
						&& (propertyName==null || domainResource.getPropertyName().equals(propertyName));
			}
		});
	}

	public static String getName(Class domainClass) {
		return domainClass.getName();
	}

	public static String getKey(Class domainClass) {
		return "scaffolding"+'-'+ NameUtils.getScriptName(domainClass);
	}

	public static String getURL(Class domainClass, String propertyName) {
		//String url = "/scaffolding/"+NameUtils.getPropertyName(domainClass) + getSuffix();
		String url = "/scaffolding/"+domainClass.getName();
		if (propertyName != null) {
			url = url + "/(*)/" + propertyName;
		}

		return url;
	}

	public static String getURLBase(Class clazz, String propertyName) {
		DomainResource domainResource = getDomainResourse(clazz, propertyName);
		String url = "";
		if (domainResource != null) {
			url = domainResource.getUrl();
		}
		return url;
	}

	public static String getPropertyHasManyByUrl(String url) {
		List<String> list = Arrays.asList(url.split("/"));
		int num = 0;
		for (int i = 0; i < list.size(); i++) {
			if (isNumber(list.get(i))) {
				num = i;
				break;
			}
		}
		return num != 0 && list.size() > (num + 1) ? list.get(num + 1) : null;
	}

	public static boolean isNumber(String self) {
		try {
			new BigDecimal(self.toString().trim());
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static Long getIdByUrl(String url) {
		List<String> list = Arrays.asList(url.split("/"));
		int num = 0;
		for (int i = 0; i < list.size(); i++) {
			if (isNumber(list.get(i))) {
				num = i;
			}
		}

		if (num != 0) {
			return Long.parseLong(list.get(num));
		}
		return null;
	}

	public static Long getIdFatherByUrl(String url) {
		List<String> list = Arrays.asList(url.split("/"));
		int num = 0;
		for (int i = 0; i < list.size(); i++) {
			if (isNumber(list.get(i))) {
				num = i;
				break;
			}
		}
		if (num != 0) {
			return Long.parseLong(list.get(num));
		}
		return null;
	}
}
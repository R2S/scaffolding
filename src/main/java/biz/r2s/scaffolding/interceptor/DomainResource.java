package biz.r2s.scaffolding.interceptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 17/08/15.
 */
public class DomainResource {
	String key;
	String url;
	String nomeResource;
	Class domainClass;
	String propertyName;
	List<String> roles;
	String root;
	String title;
	Map icon;
	boolean enabledMenu;

	public boolean isHasMamy() {
		return propertyName != null && !propertyName.isEmpty();
	}

	public String getUrlGeneric() {
		return ResourceUrlScaffold.getGenericUrlBase(domainClass, propertyName);
	}

	@Override
	public String toString() {
		return domainClass.getName() + " " + propertyName;
	}

	public Map<String, Object> format() {
		return formatMenu(this.key, this.title, this.url, this.roles, this.root, this.icon);
	}

	public static Map<String, Object> formatMenu(String key, String name, String url, List<String> rules, String root,
			Map icon) {
		Map<String, Object> menu = new java.util.HashMap();
		menu.put("key", key);
		menu.put("url", url);
		menu.put("name", name);
		menu.put("roles", rules);
		menu.put("root", root);
		menu.put("icon", icon);
		return menu;
	}

	public Class getDomainClassResourse() {
		/*
		 * if (propertyName) { GrailsDomainClassProperty classProperty =
		 * domainClass.getPropertyByName(propertyName)
		 * Assert.notNull(classProperty,
		 * "o campos hasMany $propertyName não existe") return
		 * classProperty.referencedDomainClass }
		 */
		return domainClass;
	}

	public Class getResourse() {
		return domainClass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNomeResource() {
		return nomeResource;
	}

	public void setNomeResource(String nomeResource) {
		this.nomeResource = nomeResource;
	}

	public Class getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(Class domainClass) {
		this.domainClass = domainClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map getIcon() {
		return icon;
	}

	public void setIcon(Map icon) {
		this.icon = icon;
	}

	public boolean isEnabledMenu() {
		return enabledMenu;
	}

	public void setEnabledMenu(boolean enabledMenu) {
		this.enabledMenu = enabledMenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
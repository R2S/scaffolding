package biz.r2s.scaffolding.format;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import biz.r2s.core.util.NameUtils;
import biz.r2s.scaffolding.meta.icon.IconScaffold;
import biz.r2s.scaffolding.meta.icon.PositionIcon;
import biz.r2s.scaffolding.meta.icon.TypeIcon;

/**
 * Created by raphael on 25/09/15.
 */
public class MenuRootFormat {
	CommonFormat commonFormat;

	static String messageKeyName = "menu.:name.title";
	static String messageKeyIconName = "menu.:name.icon.name";
	static String messageKeyIconType = "menu.:name.icon.type";
	static String messageKeyIconPosicion = "menu.:name.icon.position";

	public MenuRootFormat() {
		commonFormat = new CommonFormat();
	}

	public List<Map> formatMenus(List<Map> menus) {
		Map<String, List<Map>> menuGroupByRoot = menuGroupByRoot(menus);
		List menusList = Collections.emptyList();
		for (String key : menuGroupByRoot.keySet()) {
			List<Map> value = menuGroupByRoot.get(key);
			Map obj = getMenuRoot(key);
			Collections.sort(value, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					Map map1 = (Map) o1;
					Map map2 = (Map) o2;

					String name1 = (String) map1.get("name");
					String name2 = (String) map2.get("name");

					return name1.compareTo(name2);
				}
				@Override
				public boolean equals(Object obj) {
					return super.equals(obj);
				}

			});
			obj.put("items", value);
			menusList.add(obj);
		}
		return menusList;
	}

	Map<String, List<Map>> menuGroupByRoot(List<Map> menus) {
		Map<String, List<Map>> menusRoot = Collections.emptyMap();
		for (Map mapMenu : menus) {
			if (menusRoot.containsKey(mapMenu.get("root"))) {
				List<Map> maps = menusRoot.get(mapMenu.get("root"));
				maps.add(mapMenu);
			} else {
				List<Map> maps = Collections.emptyList();
				maps.add(mapMenu);
				menusRoot.put((String) mapMenu.get("root"), maps);
			}
		}
		return menusRoot;

	}

	public Map getMenuRoot(String key) {
		Map meta = Collections.emptyMap();
		meta.put("name", this.getNameMenu(key));
		meta.put("key", key);
		meta.put("icon", getIconMenu(key));
		return meta;
	}

	private String getNameMenu(String key) {
		String name = null;// I18nUtils.getMessage(messageKeyName, key)

		if (name == null) {
			name = NameUtils.getNaturalName(key);
		}
		return name;
	}

	private Map getIconMenu(String key) {
		/*
		 * String nameIcon = I18nUtils.getMessage(messageKeyIconName, key)
		 * String typeIcon = I18nUtils.getMessage(messageKeyIconType, key)
		 * String positionIcon = I18nUtils.getMessage(messageKeyIconPosicion,
		 * key)
		 */

		IconScaffold iconScaffold = new IconScaffold();
		/*
		 * name: nameIcon, type:
		 * typeIcon?getTypeIcon(typeIcon.toUpperCase()):null, position:
		 * positionIcon?getPositionIcon(positionIcon.toUpperCase()):null
		 */

		return commonFormat.formatIcon(iconScaffold);

	}

	TypeIcon getTypeIcon(String typeIcon) {
		try {
			return TypeIcon.valueOf(typeIcon.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	PositionIcon getPositionIcon(String typeIcon) {
		try {
			return PositionIcon.valueOf(typeIcon.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}

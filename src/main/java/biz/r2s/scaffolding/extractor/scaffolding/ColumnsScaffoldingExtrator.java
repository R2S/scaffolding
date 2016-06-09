package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.meta.datatatable.CampoDatatable;
import biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 06/08/15.
 */
public class ColumnsScaffoldingExtrator {

	IconScaffoldingExtrator iconScaffoldingExtrator;

	public ColumnsScaffoldingExtrator() {
		iconScaffoldingExtrator = new IconScaffoldingExtrator();
	}

	public void changeColumns(Map<String, Object> datatableMap, DatatableScaffold datatableScaffold) {
		Object value = datatableMap.get("columns");
		if ((boolean) value) {
			int count = 0;
			List<CampoDatatable> campoDatatables = Collections.emptyList();
			if (value instanceof List) {
				List<String> valueList = (List<String>) value;

				for (String nameColumn : valueList) {
					count++;
					CampoDatatable campoDatatable = this.getColumnsDefalt(nameColumn, datatableScaffold);
					campoDatatable.setOrder(count);
					campoDatatables.add(campoDatatable);
				}
			} else {
				Map<String, Object> valueMap = (Map<String, Object>) value;

				for (String nameColumn : valueMap.keySet()) {
					Object campoScaffolding = valueMap.get(nameColumn);
					CampoDatatable campoDatatable = this.getColumnsDefalt(nameColumn, datatableScaffold);
					count++;
					if (campoScaffolding instanceof String) {
						campoDatatable.setTitle((String) campoScaffolding);
					} else {
						this.changeTitle((Map<String, Object>) campoScaffolding, campoDatatable);
						this.changeIcon((Map<String, Object>) campoScaffolding, campoDatatable);
						this.changeLength((Map<String, Object>) campoScaffolding, campoDatatable);
					}
					campoDatatable.setOrder(count);
					campoDatatables.add(campoDatatable);

				}
			}
			datatableScaffold.setColumns(campoDatatables);
		}
	}

	CampoDatatable getColumnsDefalt(String name,DatatableScaffold datatableScaffold){
        CampoDatatable campoDatatable = new CampoDatatable();
        campoDatatable.setTitle(name);
        campoDatatable.setKey(name);
        campoDatatable.setName(name);
        campoDatatable.setParent(datatableScaffold);
        return campoDatatable;
    }

	void changeTitle(Map<String, Object> campoScaffolding, CampoDatatable campoDatatable){
        String value = (String) campoScaffolding.get("title");
        if(value != null){
            campoDatatable.setTitle(value);
        }
    }

	void changeLength(Map<String, Object> campoScaffolding, CampoDatatable campoDatatable){
        String value = (String) campoScaffolding.get("length");
        if(value!=null){
            campoDatatable.setLength(value);
        }
    }

	void changeIcon(Map<String, Object> campoScaffolding, CampoDatatable campoDatatable){
		IconScaffold icon = iconScaffoldingExtrator.getIcon(campoScaffolding);
        if(icon != null){
        	campoDatatable.setIcon(icon);
        }
    }

}

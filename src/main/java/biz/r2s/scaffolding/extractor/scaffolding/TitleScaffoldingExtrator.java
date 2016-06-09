package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Map;

import biz.r2s.scaffolding.meta.TitleScaffold;

/**
 * Created by raphael on 06/08/15.
 */
class TitleScaffoldingExtrator {
	public TitleScaffold getTitle(Map<String, Object> scaffolding) {
		TitleScaffold titleScaffold = null;
		Object title = scaffolding.get("title");

		if (title != null) {
			titleScaffold = new TitleScaffold();
			if (title instanceof String) {
				titleScaffold.setName((String) title);
			} else {
				Map<String, Object> titleMap = (Map<String, Object>) title;
				titleScaffold.setName((String) titleMap.get("title"));
				titleScaffold.setSubTitle((String) titleMap.get("subTitle"));
			}
		}
		return titleScaffold;
	}
}

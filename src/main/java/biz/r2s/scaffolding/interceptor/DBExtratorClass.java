package biz.r2s.scaffolding.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import biz.r2s.core.util.ClassUtils;
import biz.r2s.scaffolding.extractor.db.DBExtractor;
import biz.r2s.scaffolding.extractor.json.JSONExtractor;
import biz.r2s.scaffolding.model.ClassScaffolding;
import biz.r2s.scaffolding.service.ClassScaffoldingService;

public class DBExtratorClass implements ExtratorClass {
	private static List<Class> classes = new ArrayList<Class>();
	private static boolean cache = false;
	private static Gson gson = new Gson();

	public List<Class> getDomainClass() {
		if (!cache) {
			changeClass();
			cache = true;
		}
		return classes;
	}

	public boolean isScaffold(Class domainClass) {
		if (!cache) {
			changeClass();
			cache = true;
		}
		if (classes.contains(domainClass)) {
			Object obj = new DBExtractor().getConfig(domainClass);			
			if(obj instanceof Boolean){
				return (boolean) obj;
			}else if(obj instanceof Map){
				Map map = (Map)obj;
				if (map.containsKey("status")) {
					return Boolean.valueOf(map.get("status").toString());
				}
			}
		}
		return false;
	}

	private void changeClass() {
		List<ClassScaffolding> classScaffoldings = this.getService().listEnable();
		try {
			for(ClassScaffolding classScaffolding:classScaffoldings){
				classes.add(classScaffolding.getClassScaffolding());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ClassScaffoldingService getService(){
		return null;
	}
}

package biz.r2s.scaffolding.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import biz.r2s.core.util.ClassUtils;
import biz.r2s.scaffolding.extractor.json.JSONExtractor;

public class FileJsonExtratorClass implements ExtratorClass {
	private static List<Class> classes = new ArrayList<Class>();
	private static boolean cache = false;
	private static Gson gson = new Gson();
	public static final String EXT = ".scaffolding";

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
			Object obj = new JSONExtractor().getConfig(domainClass);
			
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
		try {
			classes.addAll(ClassUtils.getClassesForPackage("br.com.techne.cadastro", ".scaffolding"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

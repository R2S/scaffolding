package biz.r2s.scaffolding.extractor.db;

import java.util.Map;

import com.google.gson.Gson;

import biz.r2s.scaffolding.extractor.scaffolding.ScaffoldingExtractor;
import biz.r2s.scaffolding.model.ClassScaffolding;
import biz.r2s.scaffolding.service.ClassScaffoldingService;

public class DBExtractor extends ScaffoldingExtractor{
	private static Gson gson = new Gson();
	@Override
	protected
	Object getConfigScaffoldind(Class domainClass) {
		ClassScaffolding classScaffolding = getService().findByClass(domainClass.getName());
		if(classScaffolding!=null){
			if (classScaffolding.isEnable()&&classScaffolding.getJson() != null && !classScaffolding.getJson().isEmpty()) {
				return gson.fromJson(classScaffolding.getJson(), Map.class);			
			}
			return false;
		}
		return null;		
	}
	
	public Object getConfig(Class domainClass){
		return this.getConfigScaffoldind(domainClass);
	}
	
	public ClassScaffoldingService getService(){
		return null;
	}
	
	@Override
	public int getOrder() {
		return 3;
	}
}

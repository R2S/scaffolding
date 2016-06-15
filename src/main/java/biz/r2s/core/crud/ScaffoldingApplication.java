package biz.r2s.core.crud;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import biz.r2s.scaffolding.builder.ClassScaffoldBuilder;
import biz.r2s.scaffolding.extractor.clazz.ClazzExtractor;
import biz.r2s.scaffolding.extractor.db.DBExtractor;
import biz.r2s.scaffolding.extractor.json.JSONExtractor;
import biz.r2s.scaffolding.interceptor.DBExtratorClass;
import biz.r2s.scaffolding.interceptor.ExtratorClassStore;
import biz.r2s.scaffolding.interceptor.FileJsonExtratorClass;
import biz.r2s.scaffolding.interceptor.ScaffoldInterceptor;

@ApplicationPath("/rest")
public class ScaffoldingApplication extends Application {
	public ScaffoldingApplication(){
		ClassScaffoldBuilder.setDomainExtractor(new ClazzExtractor());
		ClassScaffoldBuilder.setDomainExtractor(new JSONExtractor());
		//ClassScaffoldBuilder.setDomainExtractor(new DBExtractor());
		
		ExtratorClassStore.setClasse(FileJsonExtratorClass.class);
		//ExtratorClassStore.setClasse(DBExtratorClass.class);
		ScaffoldInterceptor.intercept();
	}
	
}
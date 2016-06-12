package biz.r2s.core.crud;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import biz.r2s.scaffolding.interceptor.ScaffoldInterceptor;

@ApplicationPath("/rest")
public class ScaffoldingApplication extends Application {
	public ScaffoldingApplication(){
		ScaffoldInterceptor.intercept();
	}
	
}
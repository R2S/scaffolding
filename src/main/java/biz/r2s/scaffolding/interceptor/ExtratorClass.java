package biz.r2s.scaffolding.interceptor;

import java.util.List;

public interface ExtratorClass {
	
	public List<Class> getDomainClass();

    public boolean isScaffold(Class domainClass);
}

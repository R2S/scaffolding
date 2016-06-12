package biz.r2s.scaffolding.service;

import java.util.Map;

public interface MetaService {
	public Map getMetaList(Class domainClass, String propertyName, Object fatherId);
	public Map getMetaCreate(Class domainClass, String propertyName, Object fatherId);
	public Map getMetaEdit(Class domainClass, String propertyName, Object fatherId);
	public Map getMetaShow(Class domainClass, String propertyName, Object fatherId);
}

package biz.r2s.scaffolding.service;

import java.util.List;

import biz.r2s.core.crud.service.GenericService;
import biz.r2s.scaffolding.model.ClassScaffolding;

public interface ClassScaffoldingService extends GenericService<ClassScaffolding, Long>{
	public ClassScaffolding findByClass(String clazz);
	public List<ClassScaffolding> listEnable();
}

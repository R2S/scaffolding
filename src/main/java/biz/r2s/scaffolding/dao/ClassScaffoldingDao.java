package biz.r2s.scaffolding.dao;

import java.util.List;

import biz.r2s.core.crud.dao.GenericDao;
import biz.r2s.scaffolding.model.ClassScaffolding;

public interface ClassScaffoldingDao extends GenericDao<ClassScaffolding, Long>{
	ClassScaffolding findByClass(String clazz);
	List<ClassScaffolding> listEnable() ;
}

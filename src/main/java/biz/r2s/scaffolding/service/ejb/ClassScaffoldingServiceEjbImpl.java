package biz.r2s.scaffolding.service.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.management.Query;

import biz.r2s.core.crud.dao.GenericDao;
import biz.r2s.core.crud.service.ejb.impl.DefaultServiceEjbImpl;
import biz.r2s.core.crud.service.ejb.impl.GenericServiceEjbImpl;
import biz.r2s.scaffolding.dao.impl.ejb.ClassScaffoldingDaoEjbImpl;
import biz.r2s.scaffolding.model.ClassScaffolding;
import biz.r2s.scaffolding.service.ClassScaffoldingService;
@Stateless
@LocalBean
public class ClassScaffoldingServiceEjbImpl extends GenericServiceEjbImpl<ClassScaffolding, Long> implements ClassScaffoldingService{

	@EJB
	ClassScaffoldingDaoEjbImpl classScaffoldingDaoEjbImpl;
	
	@Override
	public ClassScaffolding findByClass(String clazz) {
		return classScaffoldingDaoEjbImpl.findByClass(clazz);
	}	

	@Override
	public GenericDao<ClassScaffolding, Long> getDao() {
		return classScaffoldingDaoEjbImpl;
	}

	@Override
	public List<ClassScaffolding> listEnable() {
		return classScaffoldingDaoEjbImpl.listEnable();
	}
}

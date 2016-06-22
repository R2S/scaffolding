package biz.r2s.scaffolding.dao.impl.ejb;

import java.util.List;

import javax.persistence.Query;

import biz.r2s.core.crud.dao.ejb.impl.GenericDaoEbjImpl;
import biz.r2s.scaffolding.dao.ClassScaffoldingDao;
import biz.r2s.scaffolding.model.ClassScaffolding;

public class ClassScaffoldingDaoEjbImpl extends GenericDaoEbjImpl<ClassScaffolding, Long> implements ClassScaffoldingDao {

	@Override
	public ClassScaffolding findByClass(String clazz) {
		String queryString = "select c from ClassScaffolding c where LOWER(c.clazz)= LOWER(:clazz)";
		Query query = this.getEntityManager().createQuery(queryString);
		return (ClassScaffolding) query.getSingleResult();
	}
	
	@Override
	public List<ClassScaffolding> listEnable() {
		String queryString = "select c from ClassScaffolding c where c.enable = true";
		Query query = this.getEntityManager().createQuery(queryString);
		return (List<ClassScaffolding>) query.getResultList();
	}

}

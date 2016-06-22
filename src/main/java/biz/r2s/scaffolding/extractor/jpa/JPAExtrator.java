package biz.r2s.scaffolding.extractor.jpa;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import biz.r2s.scaffolding.extractor.MetaDomainExtractor;
import biz.r2s.scaffolding.meta.ClassScaffold;

public class JPAExtrator implements MetaDomainExtractor{

	@Override
	public int getOrder() {
		return 4;
	}

	@Override
	public ClassScaffold extractor(Class domainClass, ClassScaffold classScaffold) {
		EntityType entityType = this.getEntityManager().getMetamodel().entity(domainClass);
		if(entityType!=null){
			//entityType;
		}		
		return null;
	}
	
	protected EntityManager getEntityManager() {
		return null;
	}	

}

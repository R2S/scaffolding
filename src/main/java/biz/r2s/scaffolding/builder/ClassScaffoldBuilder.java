package biz.r2s.scaffolding.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import biz.r2s.scaffolding.extractor.MetaDomainExtractor;
import  biz.r2s.scaffolding.meta.ClassScaffold;

/**
 * Created by raphael on 28/07/15.
 */
public class ClassScaffoldBuilder {
	private static List<MetaDomainExtractor> domainExtractors = new ArrayList<MetaDomainExtractor>();
	
	public static void setDomainExtractor(MetaDomainExtractor domainExtractor) {
		domainExtractors.add(domainExtractor);
	}
	
    private static ClassScaffoldBuilder _instance;

    public static ClassScaffoldBuilder getInstance() {
        if (_instance==null) {
            _instance = new ClassScaffoldBuilder();
        }
        return  _instance;
    }
    public ClassScaffold builder(Class domainClass) {
    	return this.builder(domainClass, false);
    }

    public ClassScaffold builder(Class domainClass, boolean isHasMany) {
        ClassScaffold classScaffold = new ClassScaffold();
        classScaffold.setHasMany(isHasMany);
        Collections.sort(domainExtractors, new Comparator<MetaDomainExtractor>() {
			@Override
			public int compare(MetaDomainExtractor o1, MetaDomainExtractor o2) {
				return Integer.compare(o1.getOrder(), o2.getOrder());
			}
        	 
		});
        for(MetaDomainExtractor domainExtractor:domainExtractors){
        	domainExtractor.extractor(domainClass, classScaffold);
        }
        return classScaffold;
    }
}

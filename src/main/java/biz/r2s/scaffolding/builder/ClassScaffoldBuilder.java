package biz.r2s.scaffolding.builder;

import  biz.r2s.scaffolding.extractor.clazz.ClazzExtractor;
import  biz.r2s.scaffolding.extractor.scaffolding.ScaffoldingExtractor;
import  biz.r2s.scaffolding.meta.ClassScaffold;

/**
 * Created by raphael on 28/07/15.
 */
public class ClassScaffoldBuilder {
    private ClazzExtractor clazzExtractor;
    private ScaffoldingExtractor scaffoldingExtractor;
    private static ClassScaffoldBuilder _instance;


    public ClassScaffoldBuilder() {
        this.clazzExtractor = new ClazzExtractor();
        this.scaffoldingExtractor = new ScaffoldingExtractor();
    }

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
        ClassScaffold classScaffol = new ClassScaffold();
        classScaffol.setHasMany(isHasMany);
        this.popularMetaByClass(domainClass, classScaffol);
        this.popularMetaByScaffold(domainClass, classScaffol);

        return classScaffol;
    }

    private void popularMetaByClass(Class domainClass, ClassScaffold classScaffold) {
        clazzExtractor.extractor(domainClass, classScaffold);
    }

    private void popularMetaByScaffold(Class domainClass, ClassScaffold classScaffold) {
        scaffoldingExtractor.extractor(domainClass, classScaffold);
    }
}

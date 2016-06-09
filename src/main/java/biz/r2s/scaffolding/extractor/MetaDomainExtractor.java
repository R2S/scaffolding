package  biz.r2s.scaffolding.extractor;

import  biz.r2s.scaffolding.meta.ClassScaffold;

/**
 * Created by raphael on 04/08/15.
 */
public interface MetaDomainExtractor {
    ClassScaffold extractor(Class domainClass, ClassScaffold classScaffold);
}

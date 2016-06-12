package biz.r2s.scaffolding.interceptor;

import java.util.Arrays;
import java.util.List;

import biz.r2s.scaffolding.extractor.clazz.FieldClassExtractor;
import biz.r2s.scaffolding.mapping.MappingScaffoldFacade;
import biz.r2s.scaffolding.meta.ClassScaffold;
import biz.r2s.scaffolding.meta.field.FieldScaffold;
import br.com.techne.cadastro.model.Pessoa;

/**
 * Created by raphael on 07/08/15.
 */
public class ScaffoldInterceptor {

    public static void intercept(){
        for (Class domainClass: getDomainClass()){
            if(isScaffold(domainClass)){
                DomainScaffoldStore.setDomainResourse(domainClass);
                List<FieldScaffold> fieldHasMany = getFieldHasMany(domainClass);
                for(FieldScaffold fieldScaffold:fieldHasMany){
                	DomainScaffoldStore.setDomainResourse(domainClass, fieldScaffold.getKey());
                }
            }
        }
        MappingScaffoldFacade.getInstance().mapped();
    }
    
    static List<Class> getDomainClass(){
    	return Arrays.asList(Pessoa.class);
    }

    static boolean isScaffold(Class domainClass)
    {
    	return true;
    }

    static List<FieldScaffold> getFieldHasMany(Class domainClass){
        FieldClassExtractor fieldClassExtractor = new FieldClassExtractor();
        ClassScaffold classScaffold = new ClassScaffold();
        classScaffold.setFields(fieldClassExtractor.getFields(domainClass, null));
        return classScaffold.getFieldHasMany();
    }
}

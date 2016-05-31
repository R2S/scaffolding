package br.ufscar.sagui.scaffolding.builder

import br.ufscar.sagui.scaffolding.extractor.clazz.ClazzExtractor
import br.ufscar.sagui.scaffolding.extractor.constraints.ConstraintsExtractor
import br.ufscar.sagui.scaffolding.extractor.mapping.MappingExtractor
import br.ufscar.sagui.scaffolding.extractor.scaffolding.ScaffoldingExtractor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 28/07/15.
 */
public class ClassScaffoldBuilder {
    private ClazzExtractor clazzExtractor;
    private ConstraintsExtractor constraintsExtractor
    private MappingExtractor mappingExtractor
    private ScaffoldingExtractor scaffoldingExtractor
    private static ClassScaffoldBuilder _instance


    public ClassScaffoldBuilder() {
        this.clazzExtractor = new ClazzExtractor()
        this.constraintsExtractor = new ConstraintsExtractor()
        this.mappingExtractor = new MappingExtractor()
        this.scaffoldingExtractor = new ScaffoldingExtractor()
    }

    public static ClassScaffoldBuilder getInstance() {
        if (!_instance) {
            _instance = new ClassScaffoldBuilder()
        }
        return  _instance
    }

    public ClassScaffold builder(GrailsDomainClass domainClass, boolean isHasMany=false) {
        ClassScaffold classScaffol = new ClassScaffold()
        classScaffol.isHasMany = isHasMany
        this.popularMetaByClass(domainClass, classScaffol)
        this.popularMetaByConstraints(domainClass, classScaffol)
        this.popularMetaByMapping(domainClass, classScaffol)
        this.popularMetaByScaffold(domainClass, classScaffol)

        return classScaffol
    }

    private void popularMetaByClass(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        clazzExtractor.extractor(domainClass, classScaffold)
    }

    private void popularMetaByConstraints(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        constraintsExtractor.extractor(domainClass, classScaffold)
    }

    private void popularMetaByMapping(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        mappingExtractor.extractor(domainClass, classScaffold)
    }

    private void popularMetaByScaffold(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        scaffoldingExtractor.extractor(domainClass, classScaffold)
    }
}

package br.ufscar.sagui.scaffolding.extractor

import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 04/08/15.
 */
interface MetaDomainExtractor {
    ClassScaffold extractor(GrailsDomainClass domainClass, ClassScaffold classScaffold);
}

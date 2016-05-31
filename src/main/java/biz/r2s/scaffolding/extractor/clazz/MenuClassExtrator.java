package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.MenuScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.icon.IconScaffold
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 25/09/15.
 */
class MenuClassExtrator {
    TitleClassExtractor titleClassExtractor
    MenuClassExtrator(){
        titleClassExtractor = new TitleClassExtractor()
    }

    public MenuScaffold getMenu(GrailsDomainClass domainClass, ClassScaffold classScaffold = null){
        MenuScaffold menuScaffold = new MenuScaffold()
        menuScaffold.icon = this.getIcon(domainClass)
        menuScaffold.root = this.getRoot(domainClass)
        menuScaffold.title = this.getTitle(domainClass)
        menuScaffold.enabled = true
        return menuScaffold
    }

    private TitleScaffold getTitle(GrailsDomainClass domainClass){
        return titleClassExtractor.getTitle(domainClass)
    }

    private IconScaffold getIcon(GrailsDomainClass domainClass){
        IconScaffold icon = new IconScaffold()
        return icon
    }

    String getRoot(GrailsDomainClass domainClass){
        return GrailsUtil.getNameModulo(domainClass.clazz)
    }
}

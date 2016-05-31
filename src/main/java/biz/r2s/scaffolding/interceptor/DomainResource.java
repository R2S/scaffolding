package br.ufscar.sagui.scaffolding.interceptor

import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.springframework.util.Assert

/**
 * Created by raphael on 17/08/15.
 */
class DomainResource {
    String key
    String url
    String nomeResource
    GrailsDomainClass domainClass
    String propertyName
    List<String> roles
    String root
    String title
    def icon
    boolean enabledMenu

    boolean isHasMamy() {
        return propertyName != null && !propertyName.isEmpty()
    }

    String getUrlGeneric() {
        ResourceUrlScaffold.getGenericUrlBase(domainClass.clazz, propertyName)
    }

    @Override
    String toString() {
        return "$domainClass.name $propertyName"
    }

    def format() {
        return formatMenu(this.key, this.title, this.url, this.roles, this.root, this.icon)
    }

    static def formatMenu(String key, String name, String url, def rules, String root, def icon) {
        def menu = [:]
        menu.key = key
        menu.url = url
        menu.name = name
        menu.roles = rules
        menu.root = root
        menu.icon = icon
        return menu
    }

    public GrailsDomainClass getDomainClassResourse() {
        if (propertyName) {
            GrailsDomainClassProperty classProperty = domainClass.getPropertyByName(propertyName)
            Assert.notNull(classProperty, "o campos hasMany $propertyName não existe")
            return classProperty.referencedDomainClass
        }
        return domainClass
    }

    public Class getResourse() {
        return getDomainClassResourse().clazz
    }
}
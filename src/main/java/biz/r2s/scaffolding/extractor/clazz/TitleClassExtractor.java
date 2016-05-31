package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.util.I18nUtils
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 30/07/15.
 */
class TitleClassExtractor {

    def keysPrefixoTitle = [":name.title.prefix", "title.prefix"]

    public TitleScaffold getTitle(GrailsDomainClass domainClass){
        TitleScaffold titleScaffold = new TitleScaffold()

        String prefixoTitle = ""
        String name =  domainClass.name.toLowerCase()
        for(String key:keysPrefixoTitle){
            String message = I18nUtils.getMessage(key, name)
            if(message){
                prefixoTitle = message
                break;
            }
        }

        titleScaffold.setName("${prefixoTitle?prefixoTitle+" ":""}${domainClass.naturalName}")

        return titleScaffold
    }
}

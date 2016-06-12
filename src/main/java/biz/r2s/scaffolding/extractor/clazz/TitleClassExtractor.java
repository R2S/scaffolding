package  biz.r2s.scaffolding.extractor.clazz;

import java.util.Arrays;
import java.util.List;

import biz.r2s.core.util.NameUtils;
import  biz.r2s.scaffolding.meta.TitleScaffold;

/**
 * Created by raphael on 30/07/15.
 */
public class TitleClassExtractor {

    List<String> keysPrefixoTitle;
    
    public TitleClassExtractor(){
    	keysPrefixoTitle = Arrays.asList(":name.title.prefix", "title.prefix");
    }

    public TitleScaffold getTitle(Class domainClass){
        TitleScaffold titleScaffold = new TitleScaffold();

        String prefixoTitle = null;
        String name =  domainClass.getName().toLowerCase();
        /*for(String key:keysPrefixoTitle){
            String message = I18nUtils.getMessage(key, name);
            if(message!=null){
                prefixoTitle = message;
                break;
            }
        }*/

        titleScaffold.setName(prefixoTitle!=null?prefixoTitle+" ":""+NameUtils.getNaturalName(domainClass.getSimpleName()));

        return titleScaffold;
    }
}

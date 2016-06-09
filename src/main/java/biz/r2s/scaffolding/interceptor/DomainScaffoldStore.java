package  biz.r2s.scaffolding.interceptor;

import  biz.r2s.scaffolding.builder.ClassScaffoldBuilder;
import  biz.r2s.scaffolding.format.MenuFormat;
import  biz.r2s.scaffolding.meta.ClassScaffold;
import  biz.r2s.scaffolding.security.RolesFacade;
import  biz.r2s.util.GrailsUtil;
import grails.util.GrailsNameUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 07/08/15.
 */
public class DomainScaffoldStore {
    static List<DomainResource> domainResources = Collections.emptyList();
    static String SUFFIX_DEFAULD = "Scaffolding";

    public static List<DomainResource> obterPorNomeResource(final String nome) {
        return (List<DomainResource>) CollectionUtils.select(domainResources, new Predicate() {			
			@Override 
			public boolean evaluate(Object arg0) {
				return ((DomainResource)arg0).nomeResource == nome;
			}
		});
    }

    static String getSuffix(){
        return this.SUFFIX_DEFAULD;
    }
    public static void setDomainResourse(Class domainClass) {
    	return setDomainResourse(domainClass, null);
    }
    
    public static void setDomainResourse(Class domainClass, String propertyName ) {

        DomainResource domainResource = this.getDomainResourse(domainClass, propertyName);
        ClassScaffold classScaffold = ClassScaffoldBuilder.getInstance().builder(domainClass);
        MenuFormat menuFormat = new MenuFormat();

        Map menu = menuFormat.formatMenu(classScaffold.getMenu());

        if (!domainResource) {
            domainResource = new DomainResource();
            domainResource.setKey(menu.get("key")!=null?menu.get("key"):this.getKey(domainClass));
            domainResource.setDomainClass(domainClass);
            domainResource.setNomeResource(menu.get("title")); 
            domainResource.setPropertyName(propertyName);
            domainResource.setUrl(this.getURL(domainClass, propertyName));
            domainResource.setIcon(menu.get("icon"));
            domainResource.setRoot(menu.get("root"));
            domainResource.setTitle(menu.get("title"));
            domainResource.setRoles(this.getRolesAcess(classScaffold, domainClass, propertyName));
            domainResource.setEnabledMenu(classScaffold.getMenu().isEnabled());
            domainResources.add(domainResource)
        }else{
            domainResource.nomeResource = this.getName(domainClass)
            domainResource.key = menu.key?:this.getKey(domainClass)
            domainResource.url = this.getURL(domainClass, propertyName)
            domainResource.icon = menu.icon
            domainResource.root = menu.root
            domainResource.title = menu.title
            domainResource.roles = this.getRolesAcess(classScaffold, domainClass, propertyName)
            domainResource.enabledMenu = classScaffold.menu.enabled
        }
    }

    static getRolesAcess(ClassScaffold classScaffold, GrailsDomainClass domainClass, String propertyName){
        if(propertyName){
            return null
        }
        RolesFacade rolesFacade = new RolesFacade()

        def objeto = rolesFacade.getMetaRoles(classScaffold)

        return objeto?.roles
    }

    public static DomainResource getDomainResourse(GrailsDomainClass domainClass, String propertyName = null ){
        return domainResources.find({ it.domainClass == domainClass && it.propertyName == propertyName})
    }

    static String getName(GrailsDomainClass domainClass) {
        return domainClass.name
    }

    static String getKey(GrailsDomainClass domainClass) {
        return GrailsUtil.getNameModulo(domainClass.clazz)+'-'+GrailsNameUtils.getScriptName(domainClass.clazz)
    }

    static String getURL(GrailsDomainClass domainClass, String propertyName) {
        String url = "/${GrailsUtil.getNameModulo(domainClass.clazz)}/" + GrailsNameUtils.getPropertyName(domainClass.clazz) + this.getSuffix()
        if(propertyName){
            url = url + "/(*)/" + propertyName
        }

        return url
    }

    static String getURLBase(Class clazz, String propertyName){
        domainResources?.find({
            it.domainClass.clazz == clazz && it.propertyName == propertyName
        })?.url
    }

    static String getPropertyHasManyByUrl(String url) {
        def list = url.split("/")
        def num = 0
        for(int i=0; i < list.size();i++){
            if(list[i].isNumber()){
                num = i
                break
            }
        }
        return num != 0&&list.size()>(num+1)? list[num+1]:null
    }

    static Long getIdByUrl(String url){
        def list = url.split("/")
        return list.findAll {it.isNumber()}?.last()?.toLong()
    }

    static Long getIdFatherByUrl(String url){
        def list = url.split("/")
        return list.findAll {it.isNumber()}?.first()?.toLong()
    }
}
package  biz.r2s.scaffolding.interceptor;

import  biz.r2s.scaffolding.builder.ClassScaffoldBuilder;
import  biz.r2s.scaffolding.format.MenuFormat;
import  biz.r2s.scaffolding.meta.ClassScaffold;
import  biz.r2s.scaffolding.security.RolesFacade;
import  biz.r2s.util.GrailsUtil;
import grails.util.GrailsNameUtils;

import java.util.List;

import org.codehaus.groovy.grails.commons.GrailsDomainClass

/**
 * Created by raphael on 07/08/15.
 */
public class DomainScaffoldStore {
    static List<DomainResource> domainResources = []
    static String SUFFIX_DEFAULD = "Scaffolding"

    public static Map obterPorNomeResource(String nome) {
        return domainResources.find({ it.nomeResource == nome })
    }

    static String getSuffix(){
        return this.SUFFIX_DEFAULD
    }

    public static void setDomainResourse(GrailsDomainClass domainClass, String propertyName = null ) {

        DomainResource domainResource = this.getDomainResourse(domainClass, propertyName)
        ClassScaffold classScaffold = ClassScaffoldBuilder.instance.builder(domainClass)
        MenuFormat menuFormat = new MenuFormat()

        def menu = menuFormat.formatMenu(classScaffold.menu)

        if (!domainResource) {
            domainResource = new DomainResource()
            domainResource.key = menu.key?:this.getKey(domainClass)
            domainResource.domainClass = domainClass
            domainResource.nomeResource = menu.title
            domainResource.propertyName = propertyName
            domainResource.url = this.getURL(domainClass, propertyName)
            domainResource.icon = menu.icon
            domainResource.root = menu.root
            domainResource.title = menu.title
            domainResource.roles = this.getRolesAcess(classScaffold, domainClass, propertyName)
            domainResource.enabledMenu = classScaffold.menu.enabled
            domainResources << domainResource
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
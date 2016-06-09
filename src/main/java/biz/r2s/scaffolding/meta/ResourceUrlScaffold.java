package biz.r2s.scaffolding.meta;

import biz.r2s.scaffolding.interceptor.DomainScaffoldStore;
import biz.r2s.scaffolding.meta.action.TypeActionScaffold;

import java.net.URL;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * Created by raphael on 29/07/15.
 */
public class ResourceUrlScaffold {
    Class clazz;
    String propertyName;

    public static ResourceUrlScaffold builder(Class entity) {
    	return builder(entity, null);
    }
    public static ResourceUrlScaffold builder(Class entity, String propertyName) {
    	ResourceUrlScaffold resource = new ResourceUrlScaffold();
        resource.setPropertyName(propertyName);
        resource.setClazz(entity);
        return resource;
    }

    public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Class getClazz() {
		return clazz;
	}
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	boolean isHasMamy() {
        return propertyName != null && !propertyName.isEmpty();
    }
    public ResourceUrl resolver(TypeActionScaffold action) {
    	return this.resolver2(action, null);
    }
    public ResourceUrl resolver(TypeActionScaffold action, Object fatherId) {
        return this.resolverUrl(action, this.getUrlBase(), fatherId);
    }
    
    public ResourceUrl resolver2(TypeActionScaffold action, Object fatherId) {
        return this.resolverUrl(action, this.getUrlBase(), fatherId);
    }

    public String  getUrlBase() {
        String url = DomainScaffoldStore.getURLBase(this.clazz, this.propertyName);
        if (url==null) {
            url = this.getGenericUrlBase();
        }
        return url;

    }

    public String getGenericUrlBase() {
        return this.getGenericUrlBase(clazz, propertyName);
    }

    public static String getGenericUrlBase(Class clazz, String propertyName) {
        String urlBase =  "/scaffolding/"+clazz.getName();
        if(propertyName!=null)
        	urlBase=urlBase.concat("/(*)/").concat(propertyName);
        return urlBase;
    }

    public static String getUrlPath() {
        /*try {
            //def webUtils = WebUtils.retrieveGrailsWebRequest()

            //return webUtils.getBaseUrl()
        } catch (e) {
            
        }*/
        return "http://localhost:8080/";
    }
    public static ResourceUrl resolverUrl(TypeActionScaffold action, String urlBase) {
    	return resolverUrl(action, urlBase, null);
    }
    public static ResourceUrl resolverUrl(TypeActionScaffold action, String urlBase, Object fatherId) {
        if (urlBase==null) {
            return null;
        }

        String urlPath = tratarUrlFather(getUrlPath() + urlBase, fatherId);

        URL url = new URL(urlPath);

        return resolver(action, url, fatherId);
    }

    public static ResourceUrl resolver(TypeActionScaffold action, URL url) {
    	return resolver(action, url, null);
    }
    
    public static ResourceUrl resolver(TypeActionScaffold action, URL url, Object fatherId) {
        String urlStr = getUrl(action, url, fatherId);
        return resolver(action, urlStr, fatherId);
    }
    public static ResourceUrl resolver(TypeActionScaffold action, String urlStr) {
    	return resolver(action, urlStr, null);
    }
    
    public static ResourceUrl resolver(TypeActionScaffold action, String urlStr, Object fatherId) {
        ResourceUrl resourceUrl = new ResourceUrl();

        resourceUrl.setUrl(new URL(urlStr));
        resourceUrl.setHttpMethod(getHttpMethod(action));

        return resourceUrl;
    }

    static String getUrl(TypeActionScaffold action, URL url) {
    	return getUrl(action, null);    	
    }
    static String getUrl(TypeActionScaffold action, URL url, Object fatherId) {

        String urlStr = tratarUrlFather(url.toString(), fatherId);

        switch (action) {
            case EDIT:
                urlStr = urlStr + "/:id";
                break;
            case VIEW:
                urlStr = urlStr + "/:id";
                break;
            case DELETE:
                urlStr = urlStr + "/:id";
                break;
        }
        return urlStr;
    }

    public static HttpMethod getHttpMethod(TypeActionScaffold action) {
        HttpMethod httpMethod;
        switch (action) {
            case CREATE:
                httpMethod = HttpMethod.POST;
                break;
            case EDIT:
                httpMethod = HttpMethod.PUT;
                break;
            case VIEW:
                httpMethod = HttpMethod.GET;
                break;
            case DELETE:
                httpMethod = HttpMethod.DELETE;
                break;
            default:
                httpMethod = HttpMethod.GET;
                break;
        }
        return httpMethod;
    }

    static String tratarUrlFather(String url, Object fatherId) {
        if (fatherId!=null) {
            return url.replace("(*)", fatherId.toString());
        }
        return url;
    }
}


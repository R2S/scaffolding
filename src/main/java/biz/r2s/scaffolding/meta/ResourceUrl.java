package biz.r2s.scaffolding.meta;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * Created by raphael on 15/09/15.
 */
public class ResourceUrl {
    HttpMethod httpMethod;
    URL url;
    public Map<String, Object> formatUrl(){
    	return formatUrl(true);
    }

    public Map<String, Object> formatUrl(Boolean relative){    	
        String u = relative?getUrlRelative(url.toString()):url.toString();
        return formatUrl(u, httpMethod);
    }

    static public Map<String, Object>  formatUrl(String url, HttpMethod httpMethod1){
    	return formatUrl(url, httpMethod1,  true);
    }
    
    static public Map<String, Object>  formatUrl(String url, HttpMethod httpMethod1, boolean relative){
    	Map<String, Object> map = new java.util.HashMap();
    	map.put("path", getUrlRelative(url));
    	map.put("action", httpMethod1.toString());
        return map;
    }
    static String getUrlRelative(String url){
        return url.replaceFirst(ResourceUrlScaffold.getUrlPath(), "");
    }

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
    
    
}

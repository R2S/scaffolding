package biz.r2s.scaffolding.meta.field.params;

import java.util.Map;

import biz.r2s.scaffolding.meta.ResourceUrlScaffold;
import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 24/07/15.
 */
public class Select2AjaxParamsFieldScaffold extends Select2ParamsFieldScaffold {
    ResourceUrlScaffold resourceUrl;
    String url;

    Select2AjaxParamsFieldScaffold(){
        super();
        this.setCampos("url");
    }

    @Override
    public TypeFieldScaffold getType() {
        return TypeFieldScaffold.SELECT2_AJAX;
    }

    @Override
    public Map<String, Object> format() {
    	Map<String, Object> o =  super.format();
        /*o.url = url? ResourceUrl.formatUrl(url, HttpMethod.GET):resourceUrl.resolver(TypeActionScaffold.LIST).formatUrl()
        o.url.path+="?type=compact"*/
        return o;
    }

	public ResourceUrlScaffold getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(ResourceUrlScaffold resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}

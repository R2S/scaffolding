package biz.r2s.scaffolding.meta.button;

import biz.r2s.scaffolding.meta.HttpMethod;

/**
 * Created by raphael on 29/07/15.
 */
public class ButtonAction extends Button{
    String url;
    HttpMethod httpMethod;
    Boolean confirmation;

    @Override
    ButtonType getType() {
        return ButtonType.ACTION;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Boolean getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Boolean confirmation) {
		this.confirmation = confirmation;
	}
    
    
}

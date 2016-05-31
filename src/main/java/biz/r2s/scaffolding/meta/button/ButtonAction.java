package biz.r2s.scaffolding.meta.button;

/**
 * Created by raphael on 29/07/15.
 */
public class ButtonAction extends Button{
    String url;
    String httpMethod;
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

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Boolean getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Boolean confirmation) {
		this.confirmation = confirmation;
	}
    
    
}

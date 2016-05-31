package biz.r2s.scaffolding.meta;

import biz.r2s.scaffolding.meta.icon.IconScaffold;

/**
 * Created by raphael on 25/09/15.
 */
public class MenuScaffold {
    String key;
    String root;
    TitleScaffold title;
    IconScaffold icon;
    ResourceUrlScaffold url;
    boolean enabled;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public TitleScaffold getTitle() {
		return title;
	}
	public void setTitle(TitleScaffold title) {
		this.title = title;
	}
	public IconScaffold getIcon() {
		return icon;
	}
	public void setIcon(IconScaffold icon) {
		this.icon = icon;
	}
	public ResourceUrlScaffold getUrl() {
		return url;
	}
	public void setUrl(ResourceUrlScaffold url) {
		this.url = url;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
}

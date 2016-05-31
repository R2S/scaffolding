package biz.r2s.scaffolding.meta.security;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import biz.r2s.scaffolding.format.ScaffoldFormattable;

/**
 * Created by raphael on 24/07/15.
 */
public class Permission implements ScaffoldFormattable{
    boolean acl = true;
    List<String> roles = Collections.EMPTY_LIST;

    @Override
    public Map<String, Object> format() {
    	Map<String, Object> map = Collections.EMPTY_MAP;
    	map.put("roles", roles);
    	map.put("acl", acl);    	
        return map;
    }

	public boolean isAcl() {
		return acl;
	}

	public void setAcl(boolean acl) {
		this.acl = acl;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}   
    
}

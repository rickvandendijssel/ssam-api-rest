package com.ssam.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.ssam.restapi.api.APIRestServiceImpl;
import com.ssam.restapi.authentication.GroupRestServiceImpl;
import com.ssam.restapi.authentication.PermissionRestServiceImpl;
import com.ssam.restapi.authentication.UserRestServiceImpl;
import com.ssam.restapi.security.SecurityInterceptorImpl;

public class RestServiceApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	
	public RestServiceApplication() {
		singletons.add(new SecurityInterceptorImpl());
		singletons.add(new APIRestServiceImpl());
		singletons.add(new UserRestServiceImpl());
		singletons.add(new GroupRestServiceImpl());
		singletons.add(new PermissionRestServiceImpl());
	}
 
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}

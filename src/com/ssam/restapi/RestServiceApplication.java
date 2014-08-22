package com.ssam.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.ssam.restapi.api.APIRestServiceImpl;
import com.ssam.restapi.security.SecurityInterceptorImpl;

public class RestServiceApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	
	public RestServiceApplication() {
		singletons.add(new SecurityInterceptorImpl());
		singletons.add(new APIRestServiceImpl());
	}
 
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}

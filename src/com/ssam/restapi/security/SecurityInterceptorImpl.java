package com.ssam.restapi.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import com.ssam.core.authentication.Group;
import com.ssam.core.authentication.Permission;
import com.ssam.core.authentication.PermissionType;
import com.ssam.core.authentication.Session;
import com.ssam.core.authentication.SessionManager;
import com.ssam.core.authentication.User;
import com.ssam.core.authentication.datafilter.SessionDataFilter;

@Provider
public class SecurityInterceptorImpl implements ContainerRequestFilter {
	
	 private static final ServerResponse ALL_ACCESS_DENIED = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());
	 private static final ServerResponse ACCESS_DENIED = new ServerResponse("You're not allowed to use this resource", 403, new Headers<Object>());

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	    ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
	    Method method = methodInvoker.getMethod();
	    
	    if(method.isAnnotationPresent(PermitAll.class)){
	    	return;
	    }
	    
	    if(method.isAnnotationPresent(DenyAll.class)){
	    	requestContext.abortWith(ALL_ACCESS_DENIED);
	    	return;
	    }
	    
	    String sessionToken = requestContext.getHeaderString("Session-Token");
	    Session session = getUserSession(sessionToken);
	    if(session != null){
	    	List<PermissionType> permissionTypeList = getUserPermission(session.getUser());
	    	if(permissionTypeList.contains(method.getAnnotation(MethodPermission.class).value())){
	    		return;
	    	}else{
	    		requestContext.abortWith(ACCESS_DENIED);
	    	}

	    }else{
	    	requestContext.abortWith(ACCESS_DENIED);
	    	return;
	    }
	    requestContext.abortWith(ALL_ACCESS_DENIED);
		return;
	}
	
	private Session getUserSession(String sessionToken){
	    SessionManager sessionManager = new SessionManager();
	    SessionDataFilter filter = new SessionDataFilter();
	    filter.setToken(sessionToken);
	    return sessionManager.getSession(filter);
	}
	
	private List<PermissionType> getUserPermission(User user){
		List<PermissionType> permissionList = new ArrayList<PermissionType>();
		for(Group group : user.getGroupList()){
			for(Permission permission : group.getPermissionList()){
				if(!permissionList.contains(permission.getPermissionType())){
					permissionList.add(permission.getPermissionType());
				}
			}
		}
		return permissionList;
	}

}

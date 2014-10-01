package com.ssam.restapi.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
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
import com.ssam.core.main.CoreFactory;

@Provider
public class SecurityInterceptorImpl implements ContainerRequestFilter {
	
	 private static final ServerResponse ALL_ACCESS_DENIED = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());
	 private static final ServerResponse ACCESS_DENIED = new ServerResponse("You're not allowed to use this resource", 403, new Headers<Object>());

	 private Logger logger = Logger.getLogger(this.getClass());
	
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
	    CoreFactory.getCoreFactory().openConnection();
	    String sessionToken = requestContext.getHeaderString("Session-Token");
	    Session session = getUserSession(sessionToken);
	    if(session != null){
	    	List<PermissionType> permissionTypeList = getUserPermission(session.getUser());
	    	if(permissionTypeList.contains(method.getAnnotation(MethodPermission.class).value())){
	    		CoreFactory.getCoreFactory().closeConnection();
	    		return;
	    	}else{
	    		CoreFactory.getCoreFactory().closeConnection();
	    		requestContext.abortWith(ACCESS_DENIED);
	    		return;
	    	}
	    }else{
	    	CoreFactory.getCoreFactory().closeConnection();
	    	requestContext.abortWith(ACCESS_DENIED);
	    	return;
	    }
	}
	
	private Session getUserSession(String sessionToken){
	    SessionManager sessionManager = new SessionManager();
	    sessionManager.deleteExpiredSession();
	    SessionDataFilter filter = new SessionDataFilter();
	    filter.setToken(sessionToken);
	    Session session = sessionManager.getSession(filter);
	    if(session !=null){
		    session.setLastActivity(new Date());
		    sessionManager.updateSession(session);
	    }
	    return session;
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

package com.ssam.restapi.api;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.ssam.core.authentication.Session;
import com.ssam.core.authentication.SessionManager;
import com.ssam.core.authentication.User;
import com.ssam.core.authentication.UserManager;
import com.ssam.core.authentication.datafilter.SessionDataFilter;
import com.ssam.core.authentication.datafilter.UserDataFilter;
import com.ssam.core.config.Language;
import com.ssam.restapi.api.APIRestService.OpenSessionInput;

public class APIRestServiceImpl implements APIRestService{

	@Override
	public String getVersion() {
		return "0.0.1";
	}
	

	@Override
	public OpenSessionOutput openSession(OpenSessionInput openSessionInput, @Context HttpServletRequest request) {
		UserManager userManager = new UserManager();
		UserDataFilter filter = new UserDataFilter();
		filter.setUserID(openSessionInput.getUserID());
		User user = userManager.getUser(filter);
		if(user != null && user.getSecureToken().contains(openSessionInput.getSecureToken())){
			SecureRandom random = new SecureRandom();
			Session session = new Session();
			session.setUser(user);
			session.setLastActivity(new Date());
			session.setLanguage(openSessionInput.getLanguage());
			session.setToken(new BigInteger(130, random).toString(32));
			session.setIp(request.getRemoteAddr());
			SessionManager sessionManager = new SessionManager();
			sessionManager.addSession(session);
			
			OpenSessionOutput sessionTemp = new OpenSessionOutput();
			sessionTemp.setToken(session.getToken());
			sessionTemp.setLastActivity(session.getLastActivity());
			return sessionTemp;
		}
		return null;
	}


	@Override
	public Boolean closeSession(CloseSessionInput closeSessionInput) {
		SessionManager sessionManager = new SessionManager();
		SessionDataFilter filter = new SessionDataFilter();
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(this.getClass());
		logger.info("Token:" + closeSessionInput.getToken());
		filter.setToken(closeSessionInput.getToken());
		Session session = sessionManager.getSession(filter);
		if(session != null){
			sessionManager.deleteSession(session);
			return true;
		}
		return false;
	}
	


}

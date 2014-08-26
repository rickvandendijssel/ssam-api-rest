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
import com.ssam.core.main.CoreFactory;

public class APIRestServiceImpl implements APIRestService{

	@Override
	public String getVersion() {
		return "0.0.1";
	}
	

	@Override
	public OpenSessionOutput openSession(OpenSessionInput openSessionInput, @Context HttpServletRequest request) {
		CoreFactory.getCoreFactory().openConnection();
		UserManager userManager = new UserManager();
		UserDataFilter filter = new UserDataFilter();
		filter.setUserID(openSessionInput.getUserID());
		User user = userManager.getUser(filter);
		
		if(user != null && user.getSecureTokenList().contains(openSessionInput.getSecureToken())){
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
		
		CoreFactory.getCoreFactory().closeConnection();
		return null;
	}


	@Override
	public Boolean closeSession(CloseSessionInput closeSessionInput) {
		CoreFactory.getCoreFactory().openConnection();
		SessionManager sessionManager = new SessionManager();
		SessionDataFilter filter = new SessionDataFilter();
		filter.setToken(closeSessionInput.getToken());
		Session session = sessionManager.getSession(filter);
		if(session != null){
			sessionManager.deleteSession(session);
			CoreFactory.getCoreFactory().closeConnection();
			return true;
		}
		CoreFactory.getCoreFactory().closeConnection();
		return false;
	}
	


}

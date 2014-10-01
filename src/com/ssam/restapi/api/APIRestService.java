package com.ssam.restapi.api;

import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.ssam.core.config.Language;
import com.ssam.restapi.main.AbstractOutput;


@Path("/")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public interface APIRestService {
	
	@GET
	@Path("/get_version")
	@PermitAll
	public String getVersion();
	
	@POST
	@Path("/open_session")
	@PermitAll
	public OpenSessionOutput openSession(OpenSessionInput openSessionInput, @Context HttpServletRequest req);
	
	@POST
	@Path("/close_session")
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@PermitAll
	public Boolean closeSession(CloseSessionInput closeSessionInput);
	
	public class OpenSessionInput{
		private String secureToken;
		private Long userID;
		private Language language;
		public String getSecureToken() {
			return secureToken;
		}
		public void setSecureToken(String secureToken) {
			this.secureToken = secureToken;
		}
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
		public Language getLanguage() {
			return language;
		}
		public void setLanguage(Language language) {
			this.language = language;
		}
	}
	
	public class OpenSessionOutput extends AbstractOutput{
		private String token;
		private Date lastActivity;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token= token;
		}
		public Date getLastActivity() {
			return lastActivity;
		}
		public void setLastActivity(Date lastActivity) {
			this.lastActivity=lastActivity;
		}
	}
	
	public class CloseSessionInput{
		private String token;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
	}

}

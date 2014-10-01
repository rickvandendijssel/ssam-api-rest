package com.ssam.restapi.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ssam.core.authentication.PermissionType;
import com.ssam.restapi.main.AbstractOutput;
import com.ssam.restapi.security.MethodPermission;

@Path("/auth")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public interface PermissionRestService {
	
	@GET
	@Path("/get_permission_by_user")
	@MethodPermission(PermissionType.GETPERMISSIONBYUSER)
	public GetPermissionByUserOutput getPermissionByUser(@QueryParam("userID") Long userID);
	
	public class GetPermissionByUserOutput extends AbstractOutput{
		Long userID;
		List<GetPermissionOutput> permissionList = new ArrayList<GetPermissionOutput>();
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
		public List<GetPermissionOutput> getPermissionList() {
			return permissionList;
		}
		public void setPermissionList(List<GetPermissionOutput> permissionList) {
			this.permissionList = permissionList;
		}
	}
	
	public class GetPermissionOutput{
		private Long permissionID;
		private PermissionType type;
		public Long getPermissionID() {
			return permissionID;
		}
		public void setPermissionID(Long permissionID) {
			this.permissionID = permissionID;
		}
		public PermissionType getType() {
			return type;
		}
		public void setType(PermissionType type) {
			this.type = type;
		}
	}

}

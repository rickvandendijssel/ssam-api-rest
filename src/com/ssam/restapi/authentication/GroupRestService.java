package com.ssam.restapi.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ssam.core.authentication.PermissionType;
import com.ssam.restapi.main.AbstractOutput;
import com.ssam.restapi.security.MethodPermission;

@Path("/auth")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public interface GroupRestService {
	
	@GET
	@Path("/get_group")
	@MethodPermission(PermissionType.GETGROUP)
	public GetGroupOutput getGroup(@QueryParam("groupID") Long groupID);
	
	@GET
	@Path("/get_group_list")
	@MethodPermission(PermissionType.GETGROUPLIST)
	public GetGroupListOutput getGroupList(@QueryParam("name") String name);
	
	@POST
	@Path("/create_group")
	@MethodPermission(PermissionType.CREATEGROUP)
	public CreateGroupOutput createGroup(CreateGroupInput input);
	
	@DELETE
	@Path("/delete_group")
	@MethodPermission(PermissionType.DELETEGROUP)
	public DeleteGroupOutput deleteGroup(@QueryParam("groupID") Long groupID);
	
	@PUT
	@Path("/update_group")
	@MethodPermission(PermissionType.UPDATEGROUP)
	public UpdateGroupOutput updateGroup(UpdateGroupInput input);
	
	
	
	public class CreateGroupInput{
		private String name;
		private List<Long> permissionIDList;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Long> getPermissionIDList() {
			return permissionIDList;
		}
		public void setPermissionIDList(List<Long> permissionIDList) {
			this.permissionIDList = permissionIDList;
		}
	}
	
	public class UpdateGroupInput{
		private Long groupID;
		private String name;
		private List<Long> permissionIDList;
		public Long getGroupID() {
			return groupID;
		}
		public void setGroupID(Long groupID) {
			this.groupID = groupID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Long> getPermissionIDList() {
			return permissionIDList;
		}
		public void setPermissionIDList(List<Long> permissionIDList) {
			this.permissionIDList = permissionIDList;
		}
	}
	
	public class GetGroupOutput extends AbstractOutput{
		private Long groupID;
		private String name;
		private List<GetGroupPermissionOutput> permissionList = new ArrayList<GetGroupPermissionOutput>();
		public Long getGroupID() {
			return groupID;
		}
		public void setGroupID(Long groupID) {
			this.groupID = groupID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<GetGroupPermissionOutput> getPermissionList() {
			return permissionList;
		}
		public void setPermissionList(List<GetGroupPermissionOutput> permissionList) {
			this.permissionList = permissionList;
		}
	}
	
	public class GetGroupListOutput extends AbstractOutput{
		List<GetGroupOutput> groupList = new ArrayList<GetGroupOutput>();

		public List<GetGroupOutput> getGroupList() {
			return groupList;
		}
		public void setGroupList(List<GetGroupOutput> groupList) {
			this.groupList = groupList;
		}
	}
	public class CreateGroupOutput extends AbstractOutput{
		private Long groupID;
		private String name;
		
		public Long getGroupID() {
			return groupID;
		}
		public void setGroupID(Long groupID) {
			this.groupID = groupID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	public class DeleteGroupOutput extends AbstractOutput{
		private Boolean deleted = false;

		public Boolean getDeleted() {
			return deleted;
		}
		public void setDeleted(Boolean deleted) {
			this.deleted = deleted;
		}
	}

	public class GetGroupPermissionOutput{
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

	public class UpdateGroupOutput extends AbstractOutput{
		private Long groupID;
		private String name;
		private List<GetGroupPermissionOutput> permissionList = new ArrayList<GetGroupPermissionOutput>();
		public Long getGroupID() {
			return groupID;
		}
		public void setGroupID(Long groupID) {
			this.groupID = groupID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<GetGroupPermissionOutput> getPermissionList() {
			return permissionList;
		}
		public void setPermissionList(List<GetGroupPermissionOutput> permissionList) {
			this.permissionList = permissionList;
		}
	}
}

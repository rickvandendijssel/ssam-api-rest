package com.ssam.restapi.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import com.ssam.core.authentication.Group;
import com.ssam.core.authentication.GroupManager;
import com.ssam.core.authentication.Permission;
import com.ssam.core.authentication.datafilter.GroupDataFilter;
import com.ssam.core.authentication.datafilter.GroupListDataFilter;
import com.ssam.core.main.CoreFactory;
import com.ssam.restapi.main.ErrorType;

public class GroupRestServiceImpl implements GroupRestService {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public CreateGroupOutput createGroup(CreateGroupInput input) {
		CreateGroupOutput output = new CreateGroupOutput();
		if(input.getName() == null){
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage("No name given for Group to be created");
			return output;
		}
		CoreFactory.getCoreFactory().openConnection();
		GroupManager groupManager = new GroupManager();
		Group group = new Group();
		group.setName(input.getName());
		Long groupID = groupManager.addGroup(group,input.getPermissionIDList()==null?new ArrayList<Long>():input.getPermissionIDList());
		if(groupID != null){
			output.setGroupID(groupID);
			output.setName(input.getName());
		}else{
			output.setErrorType(ErrorType.CREATEERROR);
			output.setErrorMessage("Group with name:"+ input.getName() + "can't be created");
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	} 

	@Override
	public DeleteGroupOutput deleteGroup(@QueryParam("groupID") Long groupID) {
		DeleteGroupOutput output = new DeleteGroupOutput();
		if(groupID == null){
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage("Need a groupID to delete a group");
			return output;
		}
		CoreFactory.getCoreFactory().openConnection();
		GroupManager groupManager = new GroupManager();
		if(groupManager.deleteGroup(groupID)){
			output.setDeleted(true);
		}else{
			output.setErrorType(ErrorType.DELETEERROR);
			output.setErrorMessage(groupManager.getError());
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

	@Override
	public GetGroupListOutput getGroupList(@QueryParam("name") String name) {
		GetGroupListOutput output = new GetGroupListOutput();
		CoreFactory.getCoreFactory().openConnection();
		GroupManager groupManager = new GroupManager();
		GroupListDataFilter filter = new GroupListDataFilter();
		if(name != null){
			filter.setLikeName(name);
		}
		List<Group> groupList = groupManager.getGroupList(filter);
		
		if(groupList != null){
			for(Group group : groupList){
				GetGroupOutput groupOutput = new GetGroupOutput();
				groupOutput.setGroupID(group.getGroupID());
				groupOutput.setName(group.getName());
				for(Permission permission: group.getPermissionList()){
					GetGroupPermissionOutput permissionOutput = new GetGroupPermissionOutput();
					permissionOutput.setPermissionID(permission.getPermissionID());
					permissionOutput.setType(permission.getPermissionType());
					groupOutput.getPermissionList().add(permissionOutput);
				}
				output.getGroupList().add(groupOutput);
			}
		}else{
			output.setErrorType(ErrorType.NODATAFOUND);
			output.setErrorMessage("There are no Groups found with the given specification");
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

	@Override
	public GetGroupOutput getGroup(@QueryParam("groupID") Long groupID) {
		GetGroupOutput output = new GetGroupOutput();
		if(groupID == null){
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage("Need a groupID to get a group");
			return output;
		}
		CoreFactory.getCoreFactory().openConnection();
		GroupManager groupManager = new GroupManager();
		GroupDataFilter filter = new GroupDataFilter();
		filter.setGroupID(groupID);
		Group group = groupManager.getGroup(filter);
		if(group != null){
			output.setName(group.getName());
			for(Permission permission : group.getPermissionList()){
				GetGroupPermissionOutput permissionOutput = new GetGroupPermissionOutput();
				permissionOutput.setPermissionID(permission.getPermissionID());
				permissionOutput.setType(permission.getPermissionType());
				output.getPermissionList().add(permissionOutput);
			}
		}else{
			output.setErrorType(ErrorType.NODATAFOUND);
			output.setErrorMessage("No Group found with given groupID: " + groupID);
		}
		
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

	@Override
	public UpdateGroupOutput updateGroup(UpdateGroupInput input) {
		UpdateGroupOutput output = new UpdateGroupOutput();
		if(input.getGroupID() == null){
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage("Need a groupID for updating a group");
			return output;
		}
		CoreFactory.getCoreFactory().openConnection();
		GroupManager groupManager = new GroupManager();
		GroupDataFilter filter = new GroupDataFilter();
		filter.setGroupID(input.getGroupID());
		Group group = groupManager.getGroup(filter);
		if(input.getName() != null){
			group.setName(input.getName());
		}
		groupManager.updateGroup(group, input.getPermissionIDList()!= null?input.getPermissionIDList():null);
		output.setName(group.getName());
		for(Permission permission : group.getPermissionList()){
			GetGroupPermissionOutput permissionOutput = new GetGroupPermissionOutput();
			permissionOutput.setPermissionID(permission.getPermissionID());
			permissionOutput.setType(permission.getPermissionType());
			output.getPermissionList().add(permissionOutput);
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

}

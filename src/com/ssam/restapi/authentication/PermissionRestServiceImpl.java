package com.ssam.restapi.authentication;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.ssam.core.authentication.Permission;
import com.ssam.core.authentication.PermissionManager;
import com.ssam.core.authentication.datafilter.PermissionListDataFilter;
import com.ssam.core.main.CoreFactory;
import com.ssam.restapi.main.ErrorType;

public class PermissionRestServiceImpl implements PermissionRestService {

	@Override
	public GetPermissionByUserOutput getPermissionByUser(@QueryParam("userID") Long userID) {
		GetPermissionByUserOutput output = new GetPermissionByUserOutput();
		if(userID == null){
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage("need userID to find permissions");
			return output;
		}
		CoreFactory.getCoreFactory().openConnection();
		PermissionManager permissionManager = new PermissionManager();
		PermissionListDataFilter filter = new PermissionListDataFilter();
		filter.setUserID(userID);
		List<Permission> permissionList = permissionManager.getPermissionList(filter);
		if(permissionList != null){
			for(Permission permission : permissionList){
				GetPermissionOutput permissionOutput = new GetPermissionOutput();
				permissionOutput.setPermissionID(permission.getPermissionID());
				permissionOutput.setType(permission.getPermissionType());
				output.getPermissionList().add(permissionOutput);
			}
			output.setUserID(userID);
		}else{
			output.setErrorType(ErrorType.NODATAFOUND);
			output.setErrorMessage("No permissions found for userID: " + userID);
		}
		
		return output;
	}

}

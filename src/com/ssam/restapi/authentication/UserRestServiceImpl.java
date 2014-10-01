package com.ssam.restapi.authentication;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import com.ssam.core.authentication.User;
import com.ssam.core.authentication.UserManager;
import com.ssam.core.authentication.datafilter.UserDataFilter;
import com.ssam.core.authentication.datafilter.UserListDataFilter;
import com.ssam.core.config.Country;
import com.ssam.core.main.CoreFactory;
import com.ssam.restapi.main.ErrorType;

public class UserRestServiceImpl implements UserRestService {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public CreateUserOuput createUser(CreateUserInput input) {
		User user = new User();
		user.setActive(true);
		user.setAddress(input.getAddress());
		user.setCountry(input.getCountry());
		user.setEmail(input.getEmail());
		user.setFirstName(input.getFirstName());
		user.setHouseNumber(input.getHouseNumber());
		user.setLanguage(input.getLanguage());
		user.setLastName(input.getLastName());
		user.setMiddleName(input.getMiddleName());
		user.setPassword(input.getPassword());
		user.setPhoneNumber(input.getPhoneNumber());
		user.setResidence(input.getResidence());
		user.setType(input.getType());
		user.setZipcode(input.getZipcode());
		
		CoreFactory.getCoreFactory().openConnection();
		
		UserManager userManager = new UserManager();
		Long userID = userManager.addUser(user);
		CreateUserOuput output = new CreateUserOuput();
		if(userID != null){
			output.setActive(user.getActive());
			output.setAddress(user.getAddress());
			output.setCountry(user.getCountry());
			output.setEmail(user.getEmail());
			output.setFirstName(user.getFirstName());
			output.setHouseNumber(user.getHouseNumber());
			output.setLanguage(user.getLanguage());
			output.setLastName(user.getLastName());
			output.setMiddleName(user.getMiddleName());
			output.setPhoneNumber(user.getPhoneNumber());
			output.setResidence(user.getResidence());
			output.setType(user.getType());
			output.setUserID(userID);
			output.setZipcode(user.getZipcode());
		}else{
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage(userManager.getError());
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

	@Override
	public GetUserOutput getUser(@QueryParam("userID") Long userID, @QueryParam("email") String email) {
		if(userID == null && email == null){
			return null;
		}
		try{
		CoreFactory.getCoreFactory().openConnection();
		UserManager userManager = new UserManager();
		UserDataFilter filter = new UserDataFilter();
		if(userID != null){
			filter.setUserID(userID);
		}
		if(email != null){
			filter.setEmail(email);
		}
		
		User user = userManager.getUser(filter);
		if(user != null){
			GetUserOutput output = new GetUserOutput();
			output.setUserID(user.getUserID());
			output.setActive(user.getActive());
			output.setAddress(user.getAddress());
			output.setCountry(user.getCountry());
			output.setEmail(user.getEmail());
			output.setFirstName(user.getFirstName());
			output.setHouseNumber(user.getHouseNumber());
			output.setLanguage(user.getLanguage());
			output.setLastName(user.getLastName());
			output.setMiddleName(user.getMiddleName());
			output.setPhoneNumber(user.getPhoneNumber());
			output.setResidence(user.getResidence());
			output.setType(user.getType());
			output.setZipcode(user.getZipcode());
			CoreFactory.getCoreFactory().closeConnection();
			return output;
		}
		CoreFactory.getCoreFactory().closeConnection();
		}catch (Exception e){
			logger.info(e.getMessage());
			CoreFactory.getCoreFactory().closeConnection();
			return null;
		}
		return null;
	}
	
	@Override
	public GetUserListOutput getUserList(@QueryParam("firstname") String firstName,  @QueryParam("middlename") String middleName,
			@QueryParam("lastname") String lastName, @QueryParam("email") String email, @QueryParam("zipcode") String zipcode, @QueryParam("address") String address,
			@QueryParam("phonenumber") String phoneNumber, @QueryParam("housenumber") String houseNumber, @QueryParam("residence") String residence,
			@QueryParam("country") Country country) {
		GetUserListOutput output = new GetUserListOutput();
		CoreFactory.getCoreFactory().openConnection();
		UserManager userManager = new UserManager();
		UserListDataFilter filter = new UserListDataFilter();		
		if(firstName != null){
			filter.setLikeFirstName(firstName);
		}
		if(middleName != null){
			filter.setLikeMiddleName(middleName);
		}
		if(lastName != null){
			filter.setLikeLastName(lastName);
		}
		if(email != null){
			filter.setLikeEmail(email);
		}
		if(zipcode != null){
			filter.setLikeZipcode(zipcode);
		}
		if(address != null){
			filter.setLikeAddress(address);
		}
		if(houseNumber != null){
			filter.setLikeHouseNumber(houseNumber);
		}
		if(phoneNumber != null){
			filter.setLikePhoneNumber(phoneNumber);
		}
		if(residence != null){
			filter.setLikeResidence(residence);
		}
		if(country != null){
			filter.setCountry(country);
		}
		List<User> userList = userManager.getUserList(filter);
		if(userList != null){
			for(User user : userList){
				GetUserOutput userOutput = new GetUserOutput();
				userOutput.setUserID(user.getUserID());
				userOutput.setActive(user.getActive());
				userOutput.setAddress(user.getAddress());
				userOutput.setCountry(user.getCountry());
				userOutput.setEmail(user.getEmail());
				userOutput.setFirstName(user.getFirstName());
				userOutput.setHouseNumber(user.getHouseNumber());
				userOutput.setLanguage(user.getLanguage());
				userOutput.setLastName(user.getLastName());
				userOutput.setMiddleName(user.getMiddleName());
				userOutput.setPhoneNumber(user.getPhoneNumber());
				userOutput.setResidence(user.getResidence());
				userOutput.setType(user.getType());
				userOutput.setZipcode(user.getZipcode());
				output.getUserList().add(userOutput);
				}
		}else{
			output.setErrorType(ErrorType.NODATAFOUND);
			output.setErrorMessage("No users are found with these params");
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

	@Override
	public DeleteUserOutput deleteUser(@QueryParam("userID") Long userID, @QueryParam("email") String email) {
		DeleteUserOutput output = new DeleteUserOutput();
		if(userID == null && email == null){
			output.setErrorType(ErrorType.INVALIDDATA);
			output.setErrorMessage("Need email or UserID for deleting a user");
			return output;
		}
		CoreFactory.getCoreFactory().openConnection();
		UserManager userManager = new UserManager();
		UserDataFilter filter = new UserDataFilter();
		if(userID != null){
			filter.setUserID(userID);
		}
		if(email != null){
			filter.setEmail(email);
		}
		User user = userManager.getUser(filter);
		if(user != null){
			user.setActive(false);
			userManager.updateUser(user, false);
			output.setDeleted(true);
		}else{
			output.setErrorType(ErrorType.NODATAFOUND);
			output.setErrorMessage("can't find a user with given email or userID");
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}

	@Override
	public UpdateUserOutput updateUser(UpdateUserInput input) {
		UpdateUserOutput output = new UpdateUserOutput();
		
		if(input.getUserID() == null){
			output.setErrorType(ErrorType.NODATAFOUND);
			output.setErrorMessage("Need userID to update the user");
		}else{
			CoreFactory.getCoreFactory().openConnection();
			UserManager userManager = new UserManager();
			UserDataFilter filter = new UserDataFilter();
			filter.setUserID(input.getUserID());
			User user = userManager.getUser(filter);
			if(user != null){
				user.setAddress(input.getAddress());
				user.setCountry(input.getCountry());
				user.setEmail(input.getEmail());
				user.setFirstName(input.getFirstName());
				user.setHouseNumber(input.getHouseNumber());
				user.setLanguage(input.getLanguage());
				user.setLastName(input.getLastName());
				user.setMiddleName(input.getMiddleName());
				user.setPassword(input.getPassword());
				user.setPhoneNumber(input.getPhoneNumber());
				user.setResidence(input.getResidence());
				user.setType(input.getType());
				user.setZipcode(input.getZipcode());
				
				if(userManager.updateUser(user, true)){
					output.setUserID(user.getUserID());
					output.setActive(user.getActive());
					output.setAddress(user.getAddress());
					output.setCountry(user.getCountry());
					output.setEmail(user.getEmail());
					output.setFirstName(user.getFirstName());
					output.setHouseNumber(user.getHouseNumber());
					output.setLanguage(user.getLanguage());
					output.setLastName(user.getLastName());
					output.setMiddleName(user.getMiddleName());
					output.setPhoneNumber(user.getPhoneNumber());
					output.setResidence(user.getResidence());
					output.setType(user.getType());
					output.setZipcode(user.getZipcode());
				}else{
					output.setErrorType(ErrorType.INVALIDDATA);
					output.setErrorMessage(userManager.getError());
				}
			}else{
				output.setErrorType(ErrorType.NODATAFOUND);
				output.setErrorMessage("No user found with given userID");
			}
		}
		CoreFactory.getCoreFactory().closeConnection();
		return output;
	}
}

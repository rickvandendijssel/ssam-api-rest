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
import com.ssam.core.authentication.UserType;
import com.ssam.core.config.Country;
import com.ssam.core.config.Language;
import com.ssam.restapi.main.AbstractOutput;
import com.ssam.restapi.security.MethodPermission;

@Path("/auth")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public interface UserRestService {
	
	@POST
	@Path("/create_user")
	@MethodPermission(PermissionType.CREATEUSER)
	public CreateUserOuput createUser(CreateUserInput input);
	
	@GET
	@Path("/get_user")
	@MethodPermission(PermissionType.GETUSER)
	public GetUserOutput getUser(@QueryParam("userID") Long userID, @QueryParam("email") String email);
	
	@GET
	@Path("/get_user_list")
	@MethodPermission(PermissionType.GETUSERLIST)
	public GetUserListOutput getUserList(@QueryParam("firstname") String firstName, 
			@QueryParam("middlename") String middleName,
			@QueryParam("lastname") String lastName,
			@QueryParam("email") String email,
			@QueryParam("zipcode") String zipcode,
			@QueryParam("address") String address,
			@QueryParam("phonenumber") String phoneNumber,
			@QueryParam("housenumber") String houseNumber,
			@QueryParam("residence") String residence,
			@QueryParam("country") Country country);
	
	@DELETE
	@Path("/delete_user")
	@MethodPermission(PermissionType.DELETEUSER)
	public DeleteUserOutput deleteUser(@QueryParam("userID") Long userID, @QueryParam("email") String email);
	
	@PUT
	@Path("/update_user")
	@MethodPermission(PermissionType.UPDATEUSER)
	public UpdateUserOutput updateUser(UpdateUserInput input);
	
	public class CreateUserInput{
		private UserType type;
		private String email;
		private String password;
		private String firstName;
		private String middleName;
		private String lastName;
		private String zipcode;
		private String address;
		private String houseNumber;
		private String phoneNumber;
		private String residence;
		private Country country;
		private Language language;
		public UserType getType() {
			return type;
		}
		public void setType(UserType type) {
			this.type = type;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getResidence() {
			return residence;
		}
		public void setResidence(String residence) {
			this.residence = residence;
		}
		public Country getCountry() {
			return country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}
		public Language getLanguage() {
			return language;
		}
		public void setLanguage(Language language) {
			this.language = language;
		}
	}
	
	public class UpdateUserInput{
		private Long userID;
		private UserType type;
		private String email;
		private String password;
		private String firstName;
		private String middleName;
		private String lastName;
		private String zipcode;
		private String address;
		private String houseNumber;
		private String phoneNumber;
		private String residence;
		private Country country;
		private Language language;
		public UserType getType() {
			return type;
		}
		public void setType(UserType type) {
			this.type = type;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getResidence() {
			return residence;
		}
		public void setResidence(String residence) {
			this.residence = residence;
		}
		public Country getCountry() {
			return country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}
		public Language getLanguage() {
			return language;
		}
		public void setLanguage(Language language) {
			this.language = language;
		}
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
	}
	
	public class CreateUserOuput extends AbstractOutput{
		private Long userID;
		private UserType type;
		private String email;
		private String firstName;
		private String middleName;
		private String lastName;
		private String zipcode;
		private String address;
		private String houseNumber;
		private String phoneNumber;
		private String residence;
		private Country country;
		private Language language;
		private Boolean active;
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
		public UserType getType() {
			return type;
		}
		public void setType(UserType type) {
			this.type = type;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getResidence() {
			return residence;
		}
		public void setResidence(String residence) {
			this.residence = residence;
		}
		public Country getCountry() {
			return country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}
		public Language getLanguage() {
			return language;
		}
		public void setLanguage(Language language) {
			this.language = language;
		}
		public Boolean getActive() {
			return active;
		}
		public void setActive(Boolean active) {
			this.active = active;
		}
	}
	
	public class GetUserOutput extends AbstractOutput{
		private Long userID;
		private UserType type;
		private String email;
		private String firstName;
		private String middleName;
		private String lastName;
		private String zipcode;
		private String address;
		private String houseNumber;
		private String phoneNumber;
		private String residence;
		private Country country;
		private Language language;
		private Boolean active;
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
		public UserType getType() {
			return type;
		}
		public void setType(UserType type) {
			this.type = type;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getResidence() {
			return residence;
		}
		public void setResidence(String residence) {
			this.residence = residence;
		}
		public Country getCountry() {
			return country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}
		public Language getLanguage() {
			return language;
		}
		public void setLanguage(Language language) {
			this.language = language;
		}
		public Boolean getActive() {
			return active;
		}
		public void setActive(Boolean active) {
			this.active = active;
		}
	}
	
	public class DeleteUserOutput extends AbstractOutput{
		private Boolean deleted = false;
		public Boolean getDeleted() {
			return deleted;
		}
		public void setDeleted(Boolean deleted) {
			this.deleted = deleted;
		}
	}

	public class UpdateUserOutput extends AbstractOutput{
		private Long userID;
		private UserType type;
		private String email;
		private String firstName;
		private String middleName;
		private String lastName;
		private String zipcode;
		private String address;
		private String houseNumber;
		private String phoneNumber;
		private String residence;
		private Country country;
		private Language language;
		private Boolean active;
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
		public UserType getType() {
			return type;
		}
		public void setType(UserType type) {
			this.type = type;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getResidence() {
			return residence;
		}
		public void setResidence(String residence) {
			this.residence = residence;
		}
		public Country getCountry() {
			return country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}
		public Language getLanguage() {
			return language;
		}
		public void setLanguage(Language language) {
			this.language = language;
		}
		public Boolean getActive() {
			return active;
		}
		public void setActive(Boolean active) {
			this.active = active;
		}
	}
	
	public class GetUserListOutput extends AbstractOutput{
		private List<GetUserOutput> userList = new ArrayList<GetUserOutput>();

		public List<GetUserOutput> getUserList() {
			return userList;
		}

		public void setUserList(List<GetUserOutput> userList) {
			this.userList = userList;
		}
	}
}

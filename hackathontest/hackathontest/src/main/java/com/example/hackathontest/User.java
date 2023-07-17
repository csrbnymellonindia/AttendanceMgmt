package com.example.hackathontest;
import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {
	
	@Id
	@JsonProperty("USER_IDENTIFIER")
	private String USER_IDENTIFIER;
	
	@JsonProperty("USER_PROFILE_IDENTIFIER")
	private int USER_PROFILE_IDENTIFIER;
	
	@JsonProperty("USER_NAME")
	private String USER_NAME;
	
	@JsonProperty("PASSWORD")
	private String PASSWORD;
	
	public String getUSER_IDENTIFIER() {
		return USER_IDENTIFIER;
	}
	public void setUSER_IDENTIFIER(String uSER_IDENTIFIER) {
		USER_IDENTIFIER = uSER_IDENTIFIER;
	}
	public int getUSER_PROFILE_IDENTIFIER() {
		return USER_PROFILE_IDENTIFIER;
	}
	public void setUSER_PROFILE_IDENTIFIER(int uSER_PROFILE_IDENTIFIER) {
		USER_PROFILE_IDENTIFIER = uSER_PROFILE_IDENTIFIER;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public User(String uSER_IDENTIFIER, int uSER_PROFILE_IDENTIFIER, String uSER_NAME, String pASSWORD) {
		super();
		USER_IDENTIFIER = uSER_IDENTIFIER;
		USER_PROFILE_IDENTIFIER = uSER_PROFILE_IDENTIFIER;
		USER_NAME = uSER_NAME;
		PASSWORD = pASSWORD;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "user [USER_IDENTIFIER=" + USER_IDENTIFIER + ", USER_PROFILE_IDENTIFIER=" + USER_PROFILE_IDENTIFIER
				+ ", USER_NAME=" + USER_NAME + ", PASSWORD=" + PASSWORD + "]";
	}
	
	
}

package com.olp.user_mgmt.to;

public class AuthResponse {

	private String username;
	private String accessToken;
	private String lastLoginTime;

	public AuthResponse() {
	}

	public AuthResponse(String username, String accessToken) {
		this.username = username;
		this.accessToken = accessToken;
	}

	public AuthResponse(String username, String accessToken, String lastLoginTime) {
		super();
		this.username = username;
		this.accessToken = accessToken;
		this.lastLoginTime = lastLoginTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}

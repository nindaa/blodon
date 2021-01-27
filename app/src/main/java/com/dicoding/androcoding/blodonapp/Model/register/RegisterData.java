package com.dicoding.androcoding.blodonapp.Model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("blood_group")
	private String bloodGroup;

	@SerializedName("username")
	private String username;

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setBloodGroup(String bloodGroup){
		this.bloodGroup = bloodGroup;
	}

	public String getBloodGroup(){
		return bloodGroup;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}
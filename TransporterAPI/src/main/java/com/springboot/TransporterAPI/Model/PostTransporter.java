package com.springboot.TransporterAPI.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class PostTransporter {

	@NotBlank(message = "Phone no. cannot be blank!")
	@Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number") 
	private String phoneNo;
	private String transporterLocation;
	private String transporterName;
	private String companyName;
	private String kyc;

	private String token;

}

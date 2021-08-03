package com.springboot.TransporterAPI.Response;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransporterCreateResponse {
	private String status;
	private String message;

	private String transporterId;
	private String phoneNo;
	private String transporterName;
	private String companyName;
	private String transporterLocation;
	private String kyc;
	private Boolean companyApproved;
	private Boolean transporterApproved;
	private Boolean accountVerificationInProgress;
	private String token;
	public Timestamp timestamp;
}

package com.springboot.TransporterAPI.Model;

import lombok.Data;

@Data
public class UpdateTransporter {
	private Long phoneNo;
	private String transporterLocation;
	private String transporterName;
	private String companyName;
	private String kyc;
	private Boolean companyApproved;
	private Boolean transporterApproved;
	private Boolean accountVerificationInProgress;
}

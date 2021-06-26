package com.springboot.TransporterAPI.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "Transporter")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transporter {
	@Id
	private String transporterId;
	private Long phoneNo;
	private String transporterName;
	private String companyName;
	private String transporterLocation;
	private String kyc;
	private boolean companyApproved;
	private boolean transporterApproved;
	
	private boolean accountVerificationInProgress;
}

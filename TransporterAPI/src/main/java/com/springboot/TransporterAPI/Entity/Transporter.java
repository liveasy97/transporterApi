package com.springboot.TransporterAPI.Entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Transporter")
@Entity
public class Transporter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID transporter_id;
	
	private String name;
	private long phone_no;
	private String kyc;
	private boolean approved;
}

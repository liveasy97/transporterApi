package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.model.ResponseTransporter;
import com.springboot.TransporterAPI.model.ResponseUpdate;

public interface TransporterService {

	ResponseTransporter addTransporter(Transporter transporter);
	
	List<Transporter> getApproved(boolean approved);

	ResponseUpdate updateTransporter(UUID transporter_id);

	ResponseEntity<String> deleteTransporter(UUID transporter_id);

}

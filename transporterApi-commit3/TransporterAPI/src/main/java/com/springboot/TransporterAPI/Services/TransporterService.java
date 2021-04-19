package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.model.TransporterResponse;

public interface TransporterService {

	TransporterResponse addTransporter(Transporter transporter);
	
	List<Transporter> getApproved(boolean approved);

	TransporterResponse updateTransporter(UUID id);

	TransporterResponse deleteTransporter(UUID id);

}

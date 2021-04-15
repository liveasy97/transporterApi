package com.springboot.TransporterAPIs.Service;

import java.util.List;
import java.util.UUID;

import com.springboot.TransporterAPIs.Entity.Transporter;

public interface Service {
	public List<Transporter> getTransporters(String approval);
	
	public List<Transporter> getList();
	
	public String addTransporter(Transporter transporter);
	
	public String deleteTransporter(UUID id);

	public String updateTransporter(UUID id);

	public Transporter getone(UUID id);

}

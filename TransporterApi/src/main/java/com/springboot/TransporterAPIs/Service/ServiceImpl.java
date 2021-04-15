package com.springboot.TransporterAPIs.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.TransporterAPIs.Dao.TransporterDao;
import com.springboot.TransporterAPIs.Entity.Transporter;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

	@Autowired
	TransporterDao transporterdao;
	
	@Override
	public List<Transporter> getList() {
		return transporterdao.findAll(); 
	}

	@Override
	public String addTransporter(Transporter transporter) {
		// TODO Auto-generated method stub
		if(transporter.getName().isEmpty()) {
			return "Enter name of company";
		}
		
		transporter.setApproval(false);
		transporter.setKyc(false);
		transporterdao.save(transporter);
		return "Pending...Please wait for liveasy will approve your request";
	}
	
	@Override
	public String deleteTransporter(UUID id) {
		// TODO Auto-generated method stub
		if(!transporterdao.existsById(id)) {
			return "Account does not exist";
		}
		Transporter transporter = transporterdao.getOne(id);
		transporterdao.delete(transporter);
		return "Account deleted succesfully";
	}

	@Override
	public List<Transporter> getTransporters(String approval) {
		// TODO Auto-generated method stub
		List<Transporter> list = transporterdao.findAll(); 
		List<Transporter> setlist = new ArrayList<Transporter>();
		
		if(approval.equalsIgnoreCase("false")) {
			for(Transporter t: list) {
				if((t.isApproval()==false) || (t.isKyc()==false)) {
					setlist.add(t);
				}
			}
		}
		
		else if(approval.equalsIgnoreCase("true")) {
			for(Transporter t: list) {
				if((t.isApproval()==true) && (t.isKyc()==true)) {
					setlist.add(t);
				}
			}
		}
		
		return setlist;
	}

	@Override
	public String updateTransporter(UUID id) {
		if(!transporterdao.existsById(id)) {
			return "Account does not exist";
		}
		Transporter transporter = transporterdao.getOne(id);
		transporter.setApproval(true);
		transporter.setKyc(true);
		transporterdao.save(transporter);
		return "Account updated sucessfully";
	}

	@Override
	public Transporter getone(UUID id) {
		// TODO Auto-generated method stub
		return transporterdao.findById(id).get();
	}

}

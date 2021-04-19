package com.springboot.TransporterAPI.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.model.TransporterResponse;

@Service
public class TransporterServiceImpl implements TransporterService {

	@Autowired
	private TransporterDao transporterdao;
	
	@Override
	public TransporterResponse addTransporter(Transporter transporter) {
		// TODO Auto-generated method stub
		TransporterResponse transporterResponse = new TransporterResponse();
		if(transporter.getName()==null) {
			transporterResponse.setStatus("Error");
			transporterResponse.setMessage("Enter name");
			return transporterResponse;
		}
		
		if(transporter.getPhoneNo()==0) {
			transporterResponse.setStatus("Error");
			transporterResponse.setMessage("Enter contact number");
			return transporterResponse;
		}
		
		transporter.setApproved(false);
		transporter.setKyc("s3 link");
		transporterdao.save(transporter);
		transporterResponse.setStatus("Pending");
		transporterResponse.setMessage("Please wait for liveasy will approved your request");
		return transporterResponse;
	}

	@Override
	public List<Transporter> getApproved(boolean approved) {
		// TODO Auto-generated method stub
		List<Transporter> setList = new ArrayList<Transporter>();
		List<Transporter> allTransporter = transporterdao.findAll();
		if(approved) {
			for(Transporter t: allTransporter) {
				if (t.isApproved()){
					setList.add(t);
				}
			}
		}
		
		else if(!approved) {
			for(Transporter t: allTransporter) {
				if (!t.isApproved()){
					setList.add(t);
				}
			}
		}
		
		return setList;
	}

	@Override
	public TransporterResponse updateTransporter(UUID id) {
		// TODO Auto-generated method stub
		TransporterResponse transporterResponse = new TransporterResponse();
		Transporter transporter = new Transporter();
		try {
			transporter=transporterdao.findById(id).get();
		}
		catch(NoSuchElementException e) {
			
		}
		
		System.out.print("Exception working fine");
		if(transporter.getId()==null) {
			transporterResponse.setStatus("Not Found");
			transporterResponse.setMessage("Account does not exist");
			return transporterResponse;
		}
		
		transporter.setApproved(true);
		transporterdao.save(transporter);
		transporterResponse.setStatus("Success");
		transporterResponse.setMessage("Account updated successfully");
		return transporterResponse;
	}

	@Override
	public TransporterResponse deleteTransporter(UUID id) {
		// TODO Auto-generated method stub
		TransporterResponse transporterResponse = new TransporterResponse();
		Transporter transporter = new Transporter();
		
		try {
			transporter=transporterdao.findById(id).get();
		}
		catch(NoSuchElementException e) {
			
		}
		
		if(transporter.getId()==null) {
			transporterResponse.setStatus("Not Found");
			transporterResponse.setMessage("Account does not exist");
			return transporterResponse;
		}
		
		transporterdao.delete(transporter);
		transporterResponse.setStatus("Success");
		transporterResponse.setMessage("Account deleted successfully");
		return transporterResponse;
	}
	
}

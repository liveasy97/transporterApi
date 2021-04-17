package com.springboot.TransporterAPI.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.model.ResponseTransporter;
import com.springboot.TransporterAPI.model.ResponseUpdate;

@Service
public class TransporterServiceImpl implements TransporterService {

	@Autowired
	private TransporterDao transporterdao;
	
	@Override
	public ResponseTransporter addTransporter(Transporter transporter) {
		// TODO Auto-generated method stub
		ResponseTransporter responseTransporter = new ResponseTransporter();
		transporter.setApproved(false);
		transporter.setKyc("s3 link");
		transporterdao.save(transporter);
		responseTransporter.setStatus("Pending");
		responseTransporter.setMessage("Please wait for liveasy will approved your request");
		return responseTransporter;
	}

	@Override
	public List<Transporter> getApproved(boolean approved) {
		// TODO Auto-generated method stub
		List<Transporter> setList = new ArrayList<Transporter>();
		if(approved==true) {
			for(Transporter t: transporterdao.findAll()) {
				if (t.isApproved()){
					setList.add(t);
				}
			}
		}
		
		else if(approved==false) {
			for(Transporter t: transporterdao.findAll()) {
				if (!t.isApproved()){
					setList.add(t);
				}
			}
		}
		
		return setList;
	}

	@Override
	public ResponseUpdate updateTransporter(UUID transporter_id) {
		// TODO Auto-generated method stub
		ResponseUpdate responseUpdate = new ResponseUpdate();
		Transporter transporter = transporterdao.findById(transporter_id).get();
		if(transporter==null) {
			responseUpdate.setResponse("Account does not exist");
			return responseUpdate;
		}
		
		transporter.setApproved(true);
		transporterdao.save(transporter);
		responseUpdate.setResponse("Updated successfully");
		return responseUpdate;
	}

	@Override
	public ResponseEntity<String> deleteTransporter(UUID transporter_id) {
		// TODO Auto-generated method stub
		Transporter transporter = transporterdao.findById(transporter_id).get();
		if(transporter==null) {
			return ResponseEntity.status(HttpStatus.OK).body("Account does not exist");
		}
		
		transporterdao.delete(transporter);
		return ResponseEntity.status(HttpStatus.OK).body("Account succesfully deleted");
	}
	
}

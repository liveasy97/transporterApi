package com.springboot.TransporterAPI.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;
import com.springboot.TransporterAPI.Services.TransporterService;

@RestController
public class TransporterController {
	
	@Autowired
	private TransporterService service;
	
	@GetMapping("/home")
	public String home() {
		return "Welcome to transporterApi git action check 2...!!!";
	}
	
	@PostMapping("/transporter")
	public TransporterCreateResponse addTransporter(@RequestBody PostTransporter transporter) {
		return service.addTransporter(transporter);
	}
	
	
	@GetMapping("/transporter")
	public List<Transporter> getTransporters(@RequestParam(required = false) Boolean transporterApproved,
			@RequestParam(required = false) Boolean companyApproved,
			@RequestParam(required = false) Integer pageNo){
		return service.getTransporters(transporterApproved, companyApproved, pageNo);
	}
	
	@GetMapping("/transporter/{transporterId}")
	private Transporter getOneTransporter(@PathVariable String transporterId) {
		return service.getOneTransporter(transporterId);
	}
	
	
	@PutMapping("/transporter/{transporterId}")
	public TransporterUpdateResponse updateTransporter(@PathVariable String transporterId, @RequestBody UpdateTransporter transporter){
		return service.updateTransporter(transporterId, transporter);
	}
	
	
	@DeleteMapping("/transporter/{transporterId}")
	public TransporterDeleteResponse deleteTransporter(@PathVariable String transporterId){
		return service.deleteTransporter(transporterId);
	}

}

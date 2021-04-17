package com.springboot.TransporterAPI.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Services.TransporterService;
import com.springboot.TransporterAPI.model.ResponseTransporter;
import com.springboot.TransporterAPI.model.ResponseUpdate;

@RestController
public class Controller extends ResponseEntityExceptionHandler {
	
	@Autowired
	private TransporterService service;
	
	@PostMapping("/transporter")
	public ResponseTransporter addTransporter(@RequestBody Transporter transporter) {
		return service.addTransporter(transporter);
	}
	
	@GetMapping("/transporter")
	public List<Transporter> getApproved(@RequestParam boolean approved){
		return service.getApproved(approved);
	}
	
	@PutMapping("/transporter/{transporter_id}")
	public ResponseUpdate updateTransporter(@PathVariable UUID transporter_id){
		return service.updateTransporter(transporter_id);
	}
	
	@DeleteMapping("/transporter/{transporter_id}")
	public ResponseEntity<String> deleteTransporter(@PathVariable UUID transporter_id){
		return service.deleteTransporter(transporter_id);
	}

}

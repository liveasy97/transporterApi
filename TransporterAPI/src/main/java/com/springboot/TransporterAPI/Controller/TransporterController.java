package com.springboot.TransporterAPI.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Services.TransporterService;
import com.springboot.TransporterAPI.Util.FirebaseUtil;
import com.springboot.TransporterAPI.Util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransporterController {

	@Autowired
	private TransporterService service;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private FirebaseUtil firebaseUtil;

	@GetMapping("/home")
	public String home() {
		return "Welcome to transporterApi git action check 2...!!!";
	}

	@PostMapping("/transporter")
	public ResponseEntity<Object> addTransporter( 
			@RequestHeader(value="Authorization", defaultValue="") String firebasetoken, 
			@RequestBody @Valid PostTransporter transporter){
		log.info("Post Controller Started");
//		System.out.println(firebasetoken);
		firebaseUtil.validateToken(firebasetoken);
		return new ResponseEntity<>(service.addTransporter(transporter),HttpStatus.CREATED);
	}


	@GetMapping("/transporter")
	public ResponseEntity<List<Transporter>> getTransporters(
			@RequestHeader(value = "Authorization", defaultValue = "") String token,
			@RequestParam(required = false) Boolean transporterApproved,
			@RequestParam(required = false) Boolean companyApproved,
			@RequestParam(required = false) Integer pageNo){
		log.info("Get with Params Controller Started");
		//		System.out.println(jwtUtil.extractId(token));
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(service.getTransporters(transporterApproved, companyApproved, pageNo,token),HttpStatus.OK);
	}

	@GetMapping("/transporter/{transporterId}")
	private ResponseEntity<Object> getOneTransporter(
			@RequestHeader(value = "Authorization", defaultValue = "") String token,
			@PathVariable String transporterId){
		log.info("Get by transporterId Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>( service.getOneTransporter(transporterId,token),HttpStatus.OK);
	}


	@PutMapping("/transporter/{transporterId}")
	public ResponseEntity<Object> updateTransporter(
			@RequestHeader(value = "Authorization", defaultValue = "") String token,
			@PathVariable String transporterId,
			@RequestBody UpdateTransporter transporter){

		log.info("Put Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(service.updateTransporter(transporterId, transporter,token),HttpStatus.OK);
	}


	@DeleteMapping("/transporter/{transporterId}")
	public ResponseEntity<Object> deleteTransporter(
			@RequestHeader(value = "Authorization", defaultValue = "") String token,
			@PathVariable String transporterId){
		log.info("Delete Controller Started");
		jwtUtil.validateToken(token);
		service.deleteTransporter(transporterId,token);
		return new ResponseEntity<>("Sucessfully deleted",HttpStatus.OK);
	}

}

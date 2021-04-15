package com.springboot.TransporterAPIs.Controller;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.TransporterAPIs.Entity.Transporter;
import com.springboot.TransporterAPIs.Service.Service;

@RestController
public class Controller {
	
	@Autowired
	private Service service;
	
	@GetMapping("/home")
	public String start() {
		return "Welcome";
	}
	
	@PostMapping("/transporter")
	public String addtransporter(@RequestBody Transporter transporter) {
		return this.service.addTransporter(transporter);
	}
	
	@GetMapping("/transporter/{approval}")
	public List<Transporter> gettransporters(@PathVariable String approval) {
		// TODO Auto-generated method stub
		return this.service.getTransporters(approval);
	}
	
	@GetMapping("/transporter")
	public List<Transporter> getList() {
		// TODO Auto-generated method stub
		return this.service.getList();
	}
	
	@GetMapping("/transporter/getone/{id}")
	public Transporter getone(@PathVariable String id) {
		return this.service.getone(UUID.fromString(id));
	}
	
	@DeleteMapping("/transporter/{id}")
	public String deletetransporter(@PathVariable String id) {
		return this.service.deleteTransporter(UUID.fromString(id));
	}
	
	@PutMapping("/transporter/{id}")
	public String updateTransporter(@PathVariable String id) {
		// TODO Auto-generated method stub
		return this.service.updateTransporter(UUID.fromString(id));
	}

}

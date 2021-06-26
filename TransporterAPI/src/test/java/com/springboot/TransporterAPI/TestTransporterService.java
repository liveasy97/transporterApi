package com.springboot.TransporterAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;
import com.springboot.TransporterAPI.Services.TransporterServiceImpl;

import org.junit.jupiter.api.Order;
@SpringBootTest
public class TestTransporterService {
	
	
	@Autowired
	private TransporterServiceImpl transporterservice;
	
	@MockBean
	private TransporterDao transporterdao;
	
	//add success
	@Test
	@Order(1)
	public void addTransportersuccess()
	{
		PostTransporter posttransporter = new PostTransporter((Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1", false, false, false);
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo(9999999991L)).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	
	// add fail phone number null
	
	@Test
	@Order(1)
	public void addTransporterfail_phoneno_null()
	{
		PostTransporter posttransporter = new PostTransporter(null,
				"transporter1", "company1", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.error, CommonConstants.phoneNoError,
				CommonConstants.idNotGenerated, null,
				null, null, null, null, null, null, null);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// invalid phone number
	
	@Test
	@Order(1)
	public void addTransporterfail_invalid_phoneno()
	{
		PostTransporter posttransporter = new PostTransporter((Long) 999999999L,
				"transporter1", "company1", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.error, CommonConstants.IncorrecPhoneNoError,
				CommonConstants.idNotGenerated, null,
				null, null, null, null, null, null, null);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// number is already present
	
	@Test
	@Order(1)
	public void addTransporterfail_phoneno_is_alreadypresent()
	{
		PostTransporter posttransporter = new PostTransporter((Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.error, CommonConstants.accountExist,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1", false, false, false);
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo(9999999991L)).thenReturn(Optional.of(transporter));
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		
		System.err.println("****************");
		System.err.println("a: " + transportercreateresponse);
		System.err.println("b: " + transportercreateresponseres);
		System.err.println("****************");
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// name company empty
	
	@Test
	@Order(1)
	public void addTransporterfail_emptycompanyname()
	{
		PostTransporter posttransporter = new PostTransporter((Long) 9999999991L,
				"transporter1", "", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.error, CommonConstants.emptyCompanyNameError,
				CommonConstants.idNotGenerated, null,
				null, null, null, null, null, null, null);
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo(9999999991L)).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		
		System.err.println("****************");
		System.err.println("a: " + transportercreateresponse);
		System.err.println("b: " + transportercreateresponseres);
		System.err.println("****************");
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// empty name
	
	@Test
	@Order(1)
	public void addTransporterfail_emptyname()
	{
		PostTransporter posttransporter = new PostTransporter((Long) 9999999991L,
				"", "company1", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.error, CommonConstants.emptyNameError,
				CommonConstants.idNotGenerated, null,
				null, null, null, null, null, null, null);
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo(9999999991L)).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		
		System.err.println("*****************");
		System.err.println("a: " + transportercreateresponse);
		System.err.println("a: " + transportercreateresponseres);
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// location empty
	
	@Test
	@Order(1)
	public void addTransporterfail_emptylocation()
	{
		PostTransporter posttransporter = new PostTransporter((Long) 9999999991L,
				"transporter1", "company1", "", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.error, CommonConstants.emptyLocationError,
				CommonConstants.idNotGenerated, null,
				null, null, null, null, null, null, null);
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo(9999999991L)).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		System.err.println("****************");
		System.err.println("a: " + transportercreateresponse);
		System.err.println("b: " + transportercreateresponseres);
		System.err.println("****************");
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	
	//get transporter by id success
	
	@Test
	@Order(1)
	public void getbyid_success()
	{
		Transporter transporter = createTransporters().get(0);

		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001")).thenReturn(Optional.of(transporter));
		
		Transporter transporterres = transporterservice.getOneTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		assertEquals(transporter, transporterres);
	}
	//get transporter by id fail
	
	@Test
	@Order(1)
	public void getbyid_fail()
	{
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001")).thenReturn(Optional.empty());
		
		Transporter transporterres = transporterservice.getOneTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		assertEquals(null, transporterres);
	}
	
	//transporterApproved, companyApproved, pageNo 
	@Test
	@Order(1)
	public void getTransporters1()
	{
		List<Transporter> transporters = createTransporters();
		Pageable page = PageRequest.of(0, (int) CommonConstants.pagesize);
		when(transporterdao.findByTransporterCompanyApproved(false, false, page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(false, false, 0);
		
		assertEquals(transporters, transportersres);
	}
	
	//transporterApproved   pageNo   companyApproved=null
	
	@Test
	@Order(1)
	public void getTransporters2()
	{
		List<Transporter> transporters = createTransporters();
		Pageable page = PageRequest.of(0, (int) CommonConstants.pagesize);
		when(transporterdao.findByTransporterApproved(false,page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(false, null, 0);
		assertEquals(transporters, transportersres);
	}
	
	
	//transporterApproved=null   pageNo   companyApproved
	@Test
	@Order(1)
	public void getTransporters3()
	{
		List<Transporter> transporters = createTransporters();
		Pageable page = PageRequest.of(0, (int) CommonConstants.pagesize);
		when(transporterdao.findByCompanyApproved(false, page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(null, false, 0);
		assertEquals(transporters, transportersres);
	}
	
	//transporterApproved=null   pageNo   companyApproved=null 
	@Test
	@Order(1)
	public void getTransporters4()
	{
		List<Transporter> transporters = createTransporters();
		Pageable page = PageRequest.of(0, (int) CommonConstants.pagesize);
		when(transporterdao.getAll(page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(null, null, 0);
		assertEquals(transporters, transportersres);
	}
	
	// update success
	@Test
	@Order(1)
	public void updateTransporter1()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"transporter11", "company11", "Nagpur", "link11", true, true, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.success,CommonConstants.updateSuccess,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter11", "company11", "Nagpur", "link11", true, true, false
				);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		when(transporterdao.save(transporters.get(0))).thenReturn(transporters.get(0));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
		
		System.err.println("****************");
		System.err.println("a: " + transporterupdateresponse);
		System.err.println("b: " + transportersres);
		System.err.println("****************");
		
		assertEquals(transporterupdateresponse, transportersres);
	}

	// update fail id not found
	
	@Test
	@Order(1)
	public void updateTransporter2()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"transporter11", "company11", "Nagpur", "link11", false, false, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.notFound,CommonConstants.accountNotExist,
				null, null,
				null, null, null, null, null, null, null
				);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000009"))
		.thenReturn(Optional.empty());
		
		when(transporterdao.save(transporters.get(0))).thenReturn(transporters.get(0));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000009", updatetransporter);
		
		System.err.println("****************");
		System.err.println("a: " + transporterupdateresponse);
		System.err.println("b: " + transportersres);
		System.err.println("****************");
		
		assertEquals(transporterupdateresponse, transportersres);
	}
	// update fail phone number not null
	
	@Test
	@Order(1)
	public void updateTransporter3()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(9999999991L,
				"transporter11", "company11", "Nagpur", "link11", false, false, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.error,CommonConstants.phoneNoUpdateError,
				null, null,
				null, null, null, null, null, null, null
				);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
		
		System.err.println("****************");
		System.err.println("a: " + transporterupdateresponse);
		System.err.println("b: " + transportersres);
		System.err.println("****************");
		
		assertEquals(transporterupdateresponse, transportersres);
	}
	// update fail empty name
	
	@Test
	@Order(1)
	public void updateTransporter4()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"", "company11", "Nagpur", "link11", false, false, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.error,CommonConstants.emptyNameError,
				null, null,
				null, null, null, null, null, null, null
				);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
		
		System.err.println("****************");
		System.err.println("a: " + transporterupdateresponse);
		System.err.println("b: " + transportersres);
		System.err.println("****************");
		
		assertEquals(transporterupdateresponse, transportersres);
	}
	// update fail empty location
	
	@Test
	@Order(1)
	public void updateTransporter5()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"transporter11", "company11", "", "link11", false, false, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.error,CommonConstants.emptyLocationError,
				null, null,
				null, null, null, null, null, null, null
				);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
		
		System.err.println("****************");
		System.err.println("a: " + transporterupdateresponse);
		System.err.println("b: " + transportersres);
		System.err.println("****************");
		
		assertEquals(transporterupdateresponse, transportersres);
	}
	// update fail empty location
	
	@Test
	@Order(1)
	public void updateTransporter6()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"transporter11", "", "Nagpur", "link11", false, false, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.error,CommonConstants.emptyCompanyNameError,
				null, null,
				null, null, null, null, null, null, null
				);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
		
		assertEquals(transporterupdateresponse, transportersres);
	}
	
	//delete success
	@Test
	@Order(1)
	public void deleteTransportersuccess()
	{
		List<Transporter> transporters = createTransporters();
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		TransporterDeleteResponse deletetransporter = new TransporterDeleteResponse(CommonConstants.success,
				CommonConstants.deleteSuccess);

		TransporterDeleteResponse deletetransporterres = transporterservice.deleteTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		assertEquals(deletetransporter, deletetransporterres);
	}
	
	//delete fail
	
	@Test
	@Order(1)
	public void deleteTransporterfail()
	{
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.empty());
		
		TransporterDeleteResponse deletetransporter = new TransporterDeleteResponse(CommonConstants.notFound,
				CommonConstants.accountNotExist);

		TransporterDeleteResponse deletetransporterres = transporterservice.deleteTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000009");
		assertEquals(deletetransporter, deletetransporterres);
	}
	
	public List<Transporter> createTransporters()
	{
		List<Transporter> transporters = Arrays.asList( 
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000002", (Long) 9999999992L,
				"transporter2", "company2", "Nagpur", "link2", false, false, true),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000003", (Long) 9999999993L,
				"transporter3", "company3", "Nagpur", "link3", false, false, true),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000004", (Long) 9999999994L,
				"transporter4", "company4", "Nagpur", "link4", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000005", (Long) 9999999995L,
				"transporter5", "company5", "Nagpur", "link5", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000006", (Long) 9999999996L,
				"transporter6", "company6", "Nagpur", "link6", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000007", (Long) 9999999996L,
				"transporter7", "company7", "Nagpur", "link6", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000008", (Long) 9999999996L,
				"transporter8", "company8", "Nagpur", "link6", false, false, false)
	    );
		
		return transporters;
	}

}


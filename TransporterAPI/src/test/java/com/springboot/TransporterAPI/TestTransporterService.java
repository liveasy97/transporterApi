package com.springboot.TransporterAPI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;
import com.springboot.TransporterAPI.Services.TransporterServiceImpl;

import org.hibernate.annotations.CreationTimestamp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
@SpringBootTest
public class TestTransporterService {
	
    private static Validator validator;
    
    @BeforeAll
    public static void setUp() {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       validator = factory.getValidator();
    }
	
	private static int pagesize=15;
	
	
	@Autowired
	private TransporterServiceImpl transporterservice;
	
	@MockBean
	private TransporterDao transporterdao;
	
	//add success
	@Test
	@Order(1)
	public void addTransportersuccess()
	{
		PostTransporter posttransporter = new PostTransporter("9999999991", "Nagpur", "transporter1", "company1",  "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134"));

		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo("9999999991")).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		transportercreateresponseres.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	
	// add fail phone number null
	@Test
	@Order(1)
	public void addTransporterfail_phoneno_null()
	{
		PostTransporter posttransporter = new PostTransporter( null, "Nagpur", "transporter1", "company1", "link1");
		
		Set<ConstraintViolation<PostTransporter>> constraintViolations = validator.validate(posttransporter);
	    
		assertEquals(1, constraintViolations.size());
		assertEquals("Phone no. cannot be blank!", constraintViolations.iterator().next().getMessage());
	}
	
	
	// invalid phone number
	@Test
	@Order(2)
	public void addTransporterfail_invalid_phoneno()
	{
		PostTransporter posttransporter = new PostTransporter("99999999","Nagpur","transporter1", "company1",  "link1");
		Set<ConstraintViolation<PostTransporter>> constraintViolations = validator.validate( posttransporter );
		assertEquals(1, constraintViolations.size());
		assertEquals("Please enter a valid mobile number", constraintViolations.iterator().next().getMessage());
	}
	
	
	// number is already present
	@Test
	@Order(3)
	public void addTransporterfail_phoneno_is_alreadypresent()
	{
		PostTransporter posttransporter = new PostTransporter("9999999991","Nagpur", "transporter1", "company1", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				null, CommonConstants.accountExist, 
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo("9999999991")).thenReturn(Optional.of(transporter));
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		transportercreateresponseres.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// name company empty
	
	@Test
	@Order(1)
	public void addTransporterfail_emptycompanyname()
	{
		PostTransporter posttransporter = new PostTransporter("9999999991","Nagpur",
				"transporter1", "",  "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", null, "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo("9999999991")).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		transportercreateresponseres.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));

		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// empty name
	
	@Test
	@Order(1)
	public void addTransporterfail_emptyname()
	{
		PostTransporter posttransporter = new PostTransporter("9999999991","Nagpur", "", "company1",  "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				null, "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo("9999999991")).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		transportercreateresponseres.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		assertEquals(transportercreateresponse, transportercreateresponseres);
	}
	// location empty
	
	@Test
	@Order(1)
	public void addTransporterfail_emptylocation()
	{
		PostTransporter posttransporter = new PostTransporter("9999999991", "", "transporter1", "company1", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", null, "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		Transporter transporter = createTransporters().get(0);
		
		when(transporterdao.findByPhoneNo("9999999991")).thenReturn(Optional.empty());
		when(transporterdao.save(transporter)).thenReturn(transporter);
		
		TransporterCreateResponse transportercreateresponseres = transporterservice.addTransporter(posttransporter);
		transportercreateresponseres.setTransporterId("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		transportercreateresponseres.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));

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
		
		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					Transporter transporterres = transporterservice.getOneTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
	            }
	    );
	    
	    assertEquals("Transporter was not found for parameters {id=transporter:0de885e0-5f43-4c68-8dde-0000000000001}", exception.getMessage());
	}
	
	//transporterApproved, companyApproved, pageNo 
	@Test
	@Order(1)
	public void getTransporters1()
	{
		List<Transporter> transporters = createTransporters();
		Pageable page = PageRequest.of(0, 15, Sort.Direction.DESC, "timestamp");
		when(transporterdao.findByTransporterCompanyApproved(false, false, page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(false, false, null, 0);
		
		assertEquals(transporters, transportersres);
	}
	
	//transporterApproved  
	
	@Test
	@Order(1)
	public void getTransporters2()
	{
		List<Transporter> transporters = createTransporters();
		Pageable page = PageRequest.of(0, 15, Sort.Direction.DESC, "timestamp");
		when(transporterdao.findByTransporterApproved(false,page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(false, null, null, 0);
		assertEquals(transporters, transportersres);
	}
	
	
	//companyApproved
	@Test
	@Order(1)
	public void getTransporters3()
	{
		List<Transporter> transporters = createTransporters();
		Collections.reverse(transporters);
		Pageable page = PageRequest.of(0, pagesize, Sort.Direction.DESC, "timestamp");
		when(transporterdao.findByCompanyApproved(false, page)).thenReturn(transporters);
		Collections.reverse(transporters);
		List<Transporter> transportersres = transporterservice.getTransporters(null, false, null, 0);
		assertEquals(transporters, transportersres);
	}
	
	//phoneno
	@Test
	@Order(1)
	public void getTransporters4()
	{
		Transporter transporters = createTransporters().get(0);
		Pageable page = PageRequest.of(0, pagesize, Sort.Direction.DESC, "timestamp");
		when(transporterdao.findByPhoneNo("9999999991")).thenReturn(Optional.of(transporters));
		List<Transporter> transportersres = transporterservice.getTransporters(null, null, "9999999991", 0);
		assertEquals(List.of(transporters), transportersres);
	}
	
	// update success
	@Test
	@Order(1)
	public void updateTransporter1()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"Nagpur","transporter11", "company11",  "link11", true, true, false);

		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.success,CommonConstants.updateSuccess,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter11", "company11", "Nagpur", "link11", true, true, false, Timestamp.valueOf("2021-07-28 23:28:50.134")
				);
		

		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		when(transporterdao.save(transporters.get(0))).thenReturn(transporters.get(0));
		
		TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
		
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

		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000009"))
		.thenReturn(Optional.empty());
		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
							"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
	            }
	    );

	    assertEquals("Transporter was not found for parameters {id=transporter:0de885e0-5f43-4c68-8dde-0000000000001}", exception.getMessage());
	}
	// update fail phone number not null
	
	@Test
	@Order(1)
	public void updateTransporter3()
	{
		List<Transporter> transporters = createTransporters();
		UpdateTransporter updatetransporter = new UpdateTransporter("9999999991",
				"transporter11", "company11", "Nagpur", "link11", false, false, false);
		
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(Optional.of(transporters.get(0)));
		
		
		Throwable exception = assertThrows(
				BusinessException.class, () -> {
					
					TransporterUpdateResponse transportersres = transporterservice.updateTransporter(
							"transporter:0de885e0-5f43-4c68-8dde-0000000000001", updatetransporter);
					}
	    );
	}
	

	//delete success
	
	@Test
	@Order(1)
	public void deleteTransportersuccess()
	{
		List<Transporter> transporters = createTransporters();
		when(transporterdao.findById("transporter:0de885e0-5f43-4c68-8dde-0000000000001")).thenReturn(Optional.of(transporters.get(0)));
		assertDoesNotThrow(()->transporterservice.deleteTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001"));
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

		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					transporterservice.deleteTransporter(
							"transporter:0de885e0-5f43-4c68-8dde-0000000000001");
	            }
	    );
	   
	    assertEquals("Transporter was not found for parameters {id=transporter:0de885e0-5f43-4c68-8dde-0000000000001}", exception.getMessage());
	}
	
	public List<Transporter> createTransporters()
	{
		List<Transporter> transporters = Arrays.asList( 
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134")),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.135")),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.136"))
	    );
		
		return transporters;
	}

}


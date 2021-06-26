package com.springboot.TransporterAPI;

import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Controller.TransporterController;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;
import com.springboot.TransporterAPI.Services.TransporterService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(value = TransporterController.class)
public class TestTransporterController {
	
   
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TransporterDao transporterdao;
	
	@MockBean
	private TransporterService transporterservice;
	
	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}
	
	@Test
	@Order(1)
	public void addTransporter() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter((Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1");
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1", false, false, false);
		
		when(transporterservice.addTransporter(posttransporter)).thenReturn(transportercreateresponse);
		
		String inputJson = mapToJson(posttransporter);
		String expectedJson = mapToJson(transportercreateresponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(CommonConstants.URI).accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();
		
		System.err.println("**********");
		System.err.println("a: " + outputInJson);
		System.err.println("b: " + expectedJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@Order(1)
	public void getTransporter() throws Exception
	{
		List<Transporter> transporters = createTransporters();
		when(transporterservice.getTransporters(false, false, 0)).thenReturn(transporters);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(CommonConstants.URI)
				.queryParam("transporterApproved", "false")
				.queryParam("companyApproved", "false")
				.queryParam("pageNo", "0")
				.accept(MediaType.APPLICATION_JSON);
		
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = mapToJson(transporters);
		String outputInJson = result.getResponse().getContentAsString();

		System.err.println("**********");
		System.err.println("a: " + outputInJson);
		System.err.println("b: " + expectedJson);
		
		assertEquals(expectedJson, outputInJson);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	@Order(1)
	public void getOneTransporter() throws Exception
	{
		when(transporterservice.getOneTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(createTransporters().get(0));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/transporter/transporter:0de885e0-5f43-4c68-8dde-0000000000001")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(createTransporters().get(0));
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	@Test
	@Order(1)
	public void updateTransporter() throws Exception
	{
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				"transporter11", "company11", "Nagpur", "link11", false, false, false);
		
		TransporterUpdateResponse transporterupdateresponse = new TransporterUpdateResponse(
				CommonConstants.success,CommonConstants.updateSuccess,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1", false, false, false
				);
		
		String inputJson = mapToJson(updatetransporter);
		when(transporterservice.updateTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001"
				, updatetransporter));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/transporter/transporter:0de885e0-5f43-4c68-8dde-0000000000001")
				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(transporterupdateresponse);
		String outputInJson = result.getResponse().getContentAsString();
		
		System.err.println("**********");
		System.err.println("a: " + outputInJson);
		System.err.println("b: " + expectedJson);

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	
	@Test
	@Order(5)
	public void deleteTransporter()  throws Exception
	{
		TransporterDeleteResponse deletetransporter = new TransporterDeleteResponse(CommonConstants.success,
				CommonConstants.deleteSuccess);
		
		when(transporterservice.deleteTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001"))
		.thenReturn(deletetransporter);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.
				delete("/transporter/transporter:0de885e0-5f43-4c68-8dde-0000000000001")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(deletetransporter);
		String outputInJson = result.getResponse().getContentAsString();
		
		System.err.println("**********");
		System.err.println("a: " + outputInJson);
		System.err.println("b: " + expectedJson);

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
		
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

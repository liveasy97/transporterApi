/*
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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
	
	private static int pagesize=15;
	
	private static String URI="/transporter";
	
	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		objectMapper.setDateFormat(df);

		return objectMapper.writeValueAsString(object);
	}
	
	@Test
	@Order(1)
	public void addTransporter() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999999991", "Nagpur", "transporter1", "company1",  "link1");
		
		
		TransporterCreateResponse transportercreateresponse = new TransporterCreateResponse(
				CommonConstants.pending, CommonConstants.approveRequest,
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter1", "company1", "Nagpur", "link1", false, false, false, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		
		
		when(transporterservice.addTransporter(posttransporter)).thenReturn(transportercreateresponse);
		
		String inputJson = mapToJson(posttransporter);
		String expectedJson = mapToJson(transportercreateresponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	@Test
	@Order(1)
	public void getTransporter() throws Exception
	{
		List<Transporter> transporters = createTransporters();
		when(transporterservice.getTransporters(false, false, null, 0)).thenReturn(transporters);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI)
				.queryParam("transporterApproved", "false")
				.queryParam("companyApproved", "false")
				.queryParam("pageNo", "0")
				.accept(MediaType.APPLICATION_JSON);
		
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = mapToJson(transporters);
		String outputInJson = result.getResponse().getContentAsString();
		
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
				"transporter:0de885e0-5f43-4c68-8dde-0000000000001", "9999999991",
				"transporter11", "company11", "Nagpur", "link11", true, true, false, Timestamp.valueOf("2021-07-28 23:28:50.134")
				);
		
		String inputJson = mapToJson(updatetransporter);
		when(transporterservice.updateTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001"
				, updatetransporter)).thenReturn(transporterupdateresponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/transporter/transporter:0de885e0-5f43-4c68-8dde-0000000000001")
				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(transporterupdateresponse);
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	
	
	@Test
	@Order(5)
	public void deleteTransporter()  throws Exception
	{
		TransporterDeleteResponse deletetransporter = new TransporterDeleteResponse(CommonConstants.success,CommonConstants.deleteSuccess);
		
		doNothing().when(transporterservice).deleteTransporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.
				delete("/transporter/transporter:0de885e0-5f43-4c68-8dde-0000000000001")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals("Sucessfully deleted", response1.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
		
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
*/

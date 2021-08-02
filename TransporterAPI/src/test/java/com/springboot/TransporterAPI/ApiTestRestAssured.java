package com.springboot.TransporterAPI;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.Column;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class ApiTestRestAssured {
	
	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}
	private static String transporterid1;
	private static String transporterid2;
	private static String transporterid3;
	
	private static long transportercount_companyapproved_transporterapproved_true = 0;
	private static long transportercount_companyapproved_true = 0;
	private static long transportercount_transporterapproved_true = 0;
	private static long transporter_all = 0;
	
	private static long transporterpagecount_companyapproved_transporterapproved_true = 0;
	private static long transporterpagecount_companyapproved_true = 0;
	private static long transporterpagecount_transporterapproved_true = 0;
	private static long transporterpage_all = 0;
	
	private static long pagesize=15;
	
	
	@BeforeAll
	public static void setup() throws Exception {
		RestAssured.baseURI = "http://localhost:9090/transporter";
		
		//get all
		Response response11;
		while (true)
		{
			response11 = RestAssured.given().param("pageNo", transporterpage_all)
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			transporter_all += response11.jsonPath().getList("$").size();
			if (response11.jsonPath().getList("$").size() != pagesize)
				break;

			transporterpage_all++;
		}
		
		//transporterapproved_true
		Response response22;
		while (true) {
			response22 = RestAssured.given().param("pageNo", transporterpagecount_transporterapproved_true)
					.param("transportApproved", true)
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			transportercount_transporterapproved_true += response22.jsonPath().getList("$").size();
			if (response22.jsonPath().getList("$").size() != pagesize)
				break;
			transporterpagecount_transporterapproved_true++;
		}
		
		//company approved true
		Response response33;
		while (true) {
			response33 = RestAssured.given()
					.param("pageNo", transporterpagecount_companyapproved_true)
					.param("companyApproved", true)
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			transportercount_companyapproved_true += response33.jsonPath().getList("$").size();
			if (response33.jsonPath().getList("$").size() != pagesize)
				break;
			transporterpagecount_companyapproved_true++;
		}
		
		// both
		
		Response response44;
		while (true) {
			response44 = RestAssured.given()
					.param("pageNo", transporterpagecount_companyapproved_transporterapproved_true)
					.param("companyApproved", true)
					.param("transportApproved", true)
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			transportercount_companyapproved_transporterapproved_true += response44.jsonPath().getList("$").size();
			if (response44.jsonPath().getList("$").size() != pagesize)
				break;
			transporterpagecount_companyapproved_transporterapproved_true++;
		}
		
		PostTransporter posttransporter1 = new PostTransporter("9999999991", "Nagpur", "transporter1", "company1",  "link1");
		String inputJson1 = mapToJson(posttransporter1);
		Response response1 = (Response) RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		transporterid1 = response1.jsonPath().getString("transporterId");
		assertEquals(HttpStatus.CREATED.value(), response1.statusCode());
        assertEquals(CommonConstants.pending, response1.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response1.jsonPath().getString("message"));
        assertEquals(posttransporter1.getTransporterName(), response1.jsonPath().getString("transporterName"));
		assertEquals(posttransporter1.getCompanyName(), response1.jsonPath().getString("companyName"));
		assertEquals(posttransporter1.getPhoneNo(), response1.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter1.getKyc(), response1.jsonPath().getString("kyc"));
		assertEquals(posttransporter1.getTransporterLocation(), response1.jsonPath().getString("transporterLocation"));
		assertEquals(false, response1.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response1.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response1.jsonPath().getBoolean("accountVerificationInProgress"));
		
		PostTransporter posttransporter2 = new PostTransporter("9999999992", "Nagpur", "transporter1", "company1",  "link1");
		String inputJson2 = mapToJson(posttransporter2);
		Response response2 = (Response) RestAssured.given().header("", "").body(inputJson2).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		transporterid2 = response2.jsonPath().getString("transporterId");
		assertEquals(HttpStatus.CREATED.value(), response2.statusCode());
        assertEquals(CommonConstants.pending, response2.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response2.jsonPath().getString("message"));
        assertEquals(posttransporter2.getTransporterName(), response2.jsonPath().getString("transporterName"));
		assertEquals(posttransporter2.getCompanyName(), response2.jsonPath().getString("companyName"));
		assertEquals(posttransporter2.getPhoneNo(), response2.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter2.getKyc(), response2.jsonPath().getString("kyc"));
		assertEquals(posttransporter2.getTransporterLocation(), response2.jsonPath().getString("transporterLocation"));
		assertEquals(false, response2.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response2.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response2.jsonPath().getBoolean("accountVerificationInProgress"));

       
		
		PostTransporter posttransporter3 = new PostTransporter("9999999993", "Nagpur", "transporter1", "company1",  "link1");
		String inputJson3 = mapToJson(posttransporter3);
		Response response3 = (Response) RestAssured.given().header("", "").body(inputJson3).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		transporterid3 = response3.jsonPath().getString("transporterId");
		assertEquals(HttpStatus.CREATED.value(), response3.statusCode());
        assertEquals(CommonConstants.pending, response3.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response3.jsonPath().getString("message"));
        assertEquals(posttransporter3.getTransporterName(), response3.jsonPath().getString("transporterName"));
		assertEquals(posttransporter3.getCompanyName(), response3.jsonPath().getString("companyName"));
		assertEquals(posttransporter3.getPhoneNo(), response3.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter3.getKyc(), response3.jsonPath().getString("kyc"));
		assertEquals(posttransporter3.getTransporterLocation(), response3.jsonPath().getString("transporterLocation"));
		assertEquals(false, response3.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response3.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response3.jsonPath().getBoolean("accountVerificationInProgress"));
	}
	
	
	//add shipper success
	@Test
	@Order(1)
	public void addtransportersuccess1() throws Exception
	{
		RestAssured.baseURI = "http://localhost:9090/transporter";
		
		PostTransporter posttransporter = new PostTransporter("9999999994", "Nagpur", "transporter1", "company1",  "link1");
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		String transporterid = response.jsonPath().getString("transporterId");
        
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response.jsonPath().getString("message"));
        assertEquals(posttransporter.getTransporterName(), response.jsonPath().getString("transporterName"));
		assertEquals(posttransporter.getCompanyName(), response.jsonPath().getString("companyName"));
		assertEquals(posttransporter.getPhoneNo(), response.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter.getKyc(), response.jsonPath().getString("kyc"));
		assertEquals(posttransporter.getTransporterLocation(), response.jsonPath().getString("transporterLocation"));
		assertEquals(false, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response.jsonPath().getBoolean("accountVerificationInProgress"));
		
		Response response2 = RestAssured.given().header("", "").delete("/" + transporterid).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response2.statusCode());
		assertEquals("Sucessfully deleted", response2.asString());
	}
	
	//few values null
	@Test
	@Order(1)
	public void addtransportersuccess2() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999999994", null, null, null, null);
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		String transporterid = response.jsonPath().getString("transporterId");
        
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response.jsonPath().getString("message"));
        assertEquals(null, response.jsonPath().getString("transporterName"));
		assertEquals(null, response.jsonPath().getString("companyName"));
		assertEquals(posttransporter.getPhoneNo(), response.jsonPath().getString("phoneNo"));
		assertEquals(null, response.jsonPath().getString("kyc"));
		assertEquals(null, response.jsonPath().getString("transporterLocation"));
		assertEquals(false, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response.jsonPath().getBoolean("accountVerificationInProgress"));
		
		Response response2 = RestAssured.given().header("", "").delete("/" + transporterid).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response2.statusCode());
		assertEquals("Sucessfully deleted", response2.asString());
	}
	
	// phone number null 
	
	@Test
	@Order(1)
	public void addtransporterfail1() throws Exception
	{
		
		PostTransporter posttransporter = new PostTransporter(null, "Nagpur", "transporter1", "company1",  "link1");
		String inputJson = mapToJson(posttransporter);
		
		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
        JsonPath jsonPathEvaluator = response.jsonPath();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals("Validation error", jsonPathEvaluator.get("transportererrorresponse.message").toString());
        assertEquals("[{field=phoneNo, message=Phone no. cannot be blank!, rejectedValue=null, object=postTransporter}]",
        		jsonPathEvaluator.get("transportererrorresponse.subErrors").toString());
	}
	
	// numebr invalid 
	
	@Test
	@Order(1)
	public void addtransporterfail2() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999", "Nagpur", "transporter1", "company1",  "link1");
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		String transporterid = response.jsonPath().getString("shipperId");
        
        JsonPath jsonPathEvaluator = response.jsonPath();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
        assertEquals("Validation error", jsonPathEvaluator.get("transportererrorresponse.message").toString());
        assertEquals("[{field=phoneNo, message=Please enter a valid mobile number, rejectedValue=9999, object=postTransporter}]",
        		jsonPathEvaluator.get("transportererrorresponse.subErrors").toString());
        
        
	}
	
	// already present
	
	@Test
	@Order(1)
	public void addtransporterfail3() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999999991", "Nagpur", "transporter1", "company1",  "link1");
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
        JsonPath jsonPathEvaluator = response.jsonPath();
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertEquals("Account already exist", jsonPathEvaluator.get("message").toString());
	}
	
	// transporter name empty
	
	@Test
	@Order(1)
	public void addtransporterfail4() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999999994", "Nagpur", "", "company1",  "link1");
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		String transporterid = response.jsonPath().getString("transporterId");
        
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response.jsonPath().getString("message"));
        assertEquals(null, response.jsonPath().getString("transporterName"));
		assertEquals(posttransporter.getCompanyName(), response.jsonPath().getString("companyName"));
		assertEquals(posttransporter.getPhoneNo(), response.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter.getKyc(), response.jsonPath().getString("kyc"));
		assertEquals(posttransporter.getTransporterLocation(), response.jsonPath().getString("transporterLocation"));
		assertEquals(false, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response.jsonPath().getBoolean("accountVerificationInProgress"));
		
		Response response2 = RestAssured.given().header("", "").delete("/" + transporterid).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response2.statusCode());
		assertEquals("Sucessfully deleted", response2.asString());
	}
	
	// company name empty
	@Test
	@Order(1)
	public void addtransporterfail5() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999999994", "Nagpur", "transporter1", "",  "link1");
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		String transporterid = response.jsonPath().getString("transporterId");
        
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response.jsonPath().getString("message"));
        assertEquals(posttransporter.getTransporterName(), response.jsonPath().getString("transporterName"));
		assertEquals(null, response.jsonPath().getString("companyName"));
		assertEquals(posttransporter.getPhoneNo(), response.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter.getKyc(), response.jsonPath().getString("kyc"));
		assertEquals(posttransporter.getTransporterLocation(), response.jsonPath().getString("transporterLocation"));
		assertEquals(false, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response.jsonPath().getBoolean("accountVerificationInProgress"));
		
		Response response2 = RestAssured.given().header("", "").delete("/" + transporterid).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response2.statusCode());
		assertEquals("Sucessfully deleted", response2.asString());
	}
	
	// location empty
	
	@Test
	@Order(1)
	public void addtransporterfail6() throws Exception
	{
		PostTransporter posttransporter = new PostTransporter("9999999994", "", "transporter1", "company1",  "link1");
		String inputJson = mapToJson(posttransporter);
		Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		String transporterid = response.jsonPath().getString("transporterId");
        
        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.approveRequest, response.jsonPath().getString("message"));
        assertEquals(posttransporter.getTransporterName(), response.jsonPath().getString("transporterName"));
		assertEquals(posttransporter.getCompanyName(), response.jsonPath().getString("companyName"));
		assertEquals(posttransporter.getPhoneNo(), response.jsonPath().getString("phoneNo"));
		assertEquals(posttransporter.getKyc(), response.jsonPath().getString("kyc"));
		assertEquals(null, response.jsonPath().getString("transporterLocation"));
		assertEquals(false, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(false, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(false, response.jsonPath().getBoolean("accountVerificationInProgress"));
		
		Response response2 = RestAssured.given().header("", "").delete("/" + transporterid).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response2.statusCode());
		assertEquals("Sucessfully deleted", response2.asString());
	}

	
	// update success
	
	@Test
	@Order(8)
	public void updatetransportersuccess1() throws Exception
	{
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				null, "transporter11", "company11",  "link11", true, true, true);
		String inputJsonupdate = mapToJson(updatetransporter);
		
		Response response = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + transporterid1).then().extract().response();
	
		assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(transporterid1, response.jsonPath().getString("transporterId"));
        assertEquals(CommonConstants.success, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.updateSuccess, response.jsonPath().getString("message"));
        assertEquals("transporter11", response.jsonPath().getString("transporterName"));
		assertEquals("company11", response.jsonPath().getString("companyName"));
		assertEquals("9999999991", response.jsonPath().getString("phoneNo"));
		assertEquals("link11", response.jsonPath().getString("kyc"));
		assertEquals("Nagpur", response.jsonPath().getString("transporterLocation"));
		assertEquals(true, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(true, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(true, response.jsonPath().getBoolean("accountVerificationInProgress"));
	}
	
	// few values null
	@Test
	@Order(8)
	public void updatetransportersuccess2() throws Exception
	{
		UpdateTransporter updatetransporter = new UpdateTransporter(null,
				null, null, null,null, null, null, null);
		String inputJsonupdate = mapToJson(updatetransporter);
		
		Response response = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + transporterid1).then().extract().response();
	
		assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(transporterid1, response.jsonPath().getString("transporterId"));
        assertEquals(CommonConstants.success, response.jsonPath().getString("status"));
        assertEquals(CommonConstants.updateSuccess, response.jsonPath().getString("message"));
        assertEquals("transporter11", response.jsonPath().getString("transporterName"));
		assertEquals("company11", response.jsonPath().getString("companyName"));
		assertEquals("9999999991", response.jsonPath().getString("phoneNo"));
		assertEquals("link11", response.jsonPath().getString("kyc"));
		assertEquals("Nagpur", response.jsonPath().getString("transporterLocation"));
		assertEquals(true, response.jsonPath().getBoolean("companyApproved"));
		assertEquals(true, response.jsonPath().getBoolean("transporterApproved"));
		assertEquals(true, response.jsonPath().getBoolean("accountVerificationInProgress"));
		
	}
	
	// phone number not null
	
	@Test
	@Order(8)
	public void updatetransporterfail1() throws Exception
	{
		UpdateTransporter updatetransporter = new UpdateTransporter("9999999991",
				"Nagpur1", "transporter11", "company11",  "link11", true, true, true);
		String inputJsonupdate = mapToJson(updatetransporter);
		
		Response response = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + transporterid1).then().extract().response();
	
		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.statusCode());
        assertEquals("Error:Phone no can't be updated", jsonPathEvaluator.get("transportererrorresponse.message").toString());
	}
	
	// getall
	@Test
	@Order(14)
	public void get_all_page() throws Exception
	{
		long lastPageCount = transporter_all % pagesize;
		long page = transporterpage_all;

		if (lastPageCount >= pagesize - 2)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= pagesize-3)
		{
			assertEquals(lastPageCount+3, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == pagesize-2)
		{
			assertEquals(1, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == pagesize-1)
		{
			assertEquals(2, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == pagesize)
		{
			assertEquals(3, response.jsonPath().getList("$").size());
		}
	}
	
	// get company page
	@Test
	@Order(15)
	public void get_company_page() throws Exception
	{
		long lastPageCount = transportercount_companyapproved_true % pagesize;
		long page = transporterpagecount_companyapproved_true;

		if (lastPageCount >= pagesize)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("companyApproved", true)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= pagesize-1)
		{
			assertEquals(lastPageCount+1, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == pagesize)
		{
			assertEquals(1, response.jsonPath().getList("$").size());
		}
	}
	// get transporter page
	
	@Test
	@Order(15)
	public void get_transporter_page() throws Exception
	{
		long lastPageCount = transporterpagecount_transporterapproved_true % pagesize;
		long page = transporterpagecount_transporterapproved_true;

		if (lastPageCount >= pagesize)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("transporterApproved", true)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= pagesize-1)
		{
			assertEquals(lastPageCount+1, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == pagesize)
		{
			assertEquals(1, response.jsonPath().getList("$").size());
		}
	}
	
	// get transporter  company page
	
	@Test
	@Order(15)
	public void get_transporter_company_page() throws Exception
	{
		long lastPageCount = transporterpagecount_companyapproved_transporterapproved_true % pagesize;
		long page = transporterpagecount_companyapproved_transporterapproved_true;

		if (lastPageCount >= pagesize)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("companyApproved", true)
				.param("transporterApproved", true)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= pagesize-1)
		{
			assertEquals(lastPageCount+1, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == pagesize)
		{
			assertEquals(1, response.jsonPath().getList("$").size());
		}
	}
	
	// get transporter  company
	@Test
	@Order(17)
	public void get_transporter_company() throws Exception
	{
		long lastPageCount = transportercount_companyapproved_transporterapproved_true % pagesize;
		
		Response response = RestAssured.given()
				.param("companyApproved", true)
				.param("companyApproved", true)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(transporterpagecount_companyapproved_transporterapproved_true>=1 || lastPageCount>=pagesize-1)
		{
			assertEquals(pagesize, response.jsonPath().getList("$").size());
		}
		else
		{
			assertEquals(lastPageCount+1, response.jsonPath().getList("$").size());
		}
	}
	// get page
	@Test
	@Order(14)
	public void get_all() throws Exception
	{
		long lastPageCount = transporter_all % pagesize;
		
		Response response = RestAssured.given()
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(transporterpage_all>=1 || lastPageCount>=pagesize-3)
		{
			assertEquals(pagesize, response.jsonPath().getList("$").size());
		}
		else
		{
			assertEquals(lastPageCount+3, response.jsonPath().getList("$").size());
		}
	}
	
	
	@AfterAll
	public static void deletedata()
	{
		Response response1 = RestAssured.given().header("", "").delete("/" + transporterid1).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response1.statusCode());
		assertEquals("Sucessfully deleted", response1.asString());
		
		Response response2 = RestAssured.given().header("", "").delete("/" + transporterid2).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response2.statusCode());
		assertEquals("Sucessfully deleted", response2.asString());
		
		Response response3 = RestAssured.given().header("", "").delete("/" + transporterid3).then().extract().response();
		assertEquals(HttpStatus.OK.value(), response3.statusCode());
		assertEquals("Sucessfully deleted", response3.asString());
		
	}

}

package com.springboot.TransporterAPI;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
class TransporterApiApplicationTests {

	/*
	 * @Autowired TransporterService service;
	 * 
	 * @MockBean TransporterDao transporterDao; Transporter transporter1 = new
	 * Transporter(); Transporter transporter2 = new Transporter(); Transporter
	 * transporter3 = new Transporter();
	 * 
	 * 
	 * //Unit Testing for saving
	 * 
	 * @Test public void saveTransporterTest() {
	 * transporter1.setId("transporter:01"); transporter1.setName("Ram");
	 * transporter1.setKyc("kyc"); transporter1.setApproved(false);
	 * transporter1.setPhoneNo(1234456789); transporter2.setId("transporter:02");
	 * transporter2.setName("Seeta"); transporter2.setKyc("kycSeeta");
	 * transporter2.setApproved(true); transporter2.setPhoneNo(1796546650);
	 * transporter3.setId("transporter:03"); transporter3.setName("Laxman");
	 * transporter3.setKyc("kycLaxman"); transporter3.setApproved(false);
	 * transporter3.setPhoneNo(1287965546); TransporterCreateResponse tcr = new
	 * TransporterCreateResponse(); tcr.setStatus("Pending");
	 * tcr.setMessage("Please wait for liveasy will approve your request");
	 * when(transporterDao.save(transporter1)).thenReturn(transporter1);
	 * //assertEquals(tcr, service.addTransporter(transporter1));
	 * when(transporterDao.save(transporter2)).thenReturn(transporter2);
	 * //assertEquals(tcr, service.addTransporter(transporter2));
	 * when(transporterDao.save(transporter3)).thenReturn(transporter3);
	 * //assertEquals(tcr, service.addTransporter(transporter3)); }
	 * 
	 * //Unit Testing for saving without name
	 * 
	 * @Test public void saveWithoutNameTest() { Transporter transporter4 = new
	 * Transporter(); transporter4.setId("transporter:01");
	 * transporter4.setKyc("kyc"); transporter4.setApproved(false);
	 * transporter4.setPhoneNo(1234456789); TransporterCreateResponse tcr = new
	 * TransporterCreateResponse(); tcr.setStatus("Error");
	 * tcr.setMessage("Enter name");
	 * when(transporterDao.save(transporter4)).thenReturn(transporter4);
	 * //assertEquals(tcr, service.addTransporter(transporter4)); }
	 * 
	 * //Unit Testing for saving without phone number
	 * 
	 * @Test public void saveWithoutPhoneNoTest() { Transporter transporter3 = new
	 * Transporter(); transporter3.setName("Seeeta");
	 * transporter3.setId("transporter:01"); transporter3.setKyc("kyc");
	 * transporter3.setApproved(false); TransporterCreateResponse tcr = new
	 * TransporterCreateResponse(); tcr.setStatus("Error");
	 * tcr.setMessage("Enter phone number");
	 * when(transporterDao.save(transporter3)).thenReturn(transporter3);
	 * //assertEquals(tcr, service.addTransporter(transporter3)); }
	 * 
	 * //Unit Testing for saving with incorrect phone number
	 * 
	 * @Test public void saveWithIncorrectPhoneNoTest() { Transporter transporter3 =
	 * new Transporter(); transporter3.setId("transporter:01");
	 * transporter3.setName("Ram"); transporter3.setKyc("kyc");
	 * transporter3.setApproved(false); transporter3.setPhoneNo(12344);
	 * TransporterCreateResponse tcr = new TransporterCreateResponse();
	 * tcr.setStatus("Error"); tcr.setMessage("Enter 10 digits phone number");
	 * when(transporterDao.save(transporter3)).thenReturn(transporter3);
	 * //assertEquals(tcr, service.addTransporter(transporter3)); }
	 * 
	 * //Unit Testing for adding existing data
	 * 
	 * @Test public void addExistingDataTest() { Transporter transporter3 = new
	 * Transporter(); transporter3.setName("Tyler");
	 * transporter3.setId("transporter:03"); transporter3.setName("Ram");
	 * transporter3.setKyc("kyc"); transporter3.setApproved(false);
	 * transporter3.setPhoneNo(1234457645);
	 * 
	 * TransporterCreateResponse tcr = new TransporterCreateResponse();
	 * tcr.setStatus("Error"); tcr.setMessage("Account already exist");
	 * when(transporterDao.findByPhoneNo(transporter3.getPhoneNo())).thenReturn(
	 * "1234567890"); //assertEquals(tcr, service.addTransporter(transporter3));
	 * 
	 * }
	 * 
	 * //Unit Testing for getting all transporters
	 * 
	 * @Test public void getAllTransportersTest() {
	 * when(transporterDao.findAll()).thenReturn(Stream.of(transporter1,
	 * transporter2, transporter3).collect(Collectors.toList())); assertEquals(3,
	 * service.allTransporter().size()); }
	 * 
	 * //Unit Testing for getting all transporters based on approved value
	 * 
	 * @Test public void getApprovedTest() {
	 * when(transporterDao.findByApproved(true)).thenReturn(Stream.of(transporter2).
	 * collect(Collectors.toList())); assertEquals(1,
	 * service.getApproved(true).size()); }
	 * 
	 * //Unit Testing for getting single transporter
	 * 
	 * @Test public void getOneTransporterTest() {
	 * //when(transporterDao.getById("transporter:01")).thenReturn(transporter1);
	 * //assertEquals(transporter1, service.getOneTransporter("transporter:01")); }
	 * 
	 * //Unit Testing for updating with correct id
	 * 
	 * @Test public void updateWithCorrectIdTest() { transporter1.setName("John");
	 * TransporterUpdateResponse tur = new TransporterUpdateResponse();
	 * tur.setStatus("Success"); tur.setMessage("Account updated successfully");
	 * 
	 * Optional<Transporter> t = Optional.of(transporter1);
	 * when(transporterDao.findById("transporter:01")).thenReturn(t);
	 * //assertEquals(tur, service.updateTransporter("transporter:01",
	 * transporter1)); }
	 * 
	 * //Unit Testing for updating with incorrect id
	 * 
	 * @Test public void updateWithIncorrectIdTest() { transporter1.setName("John");
	 * TransporterUpdateResponse tur = new TransporterUpdateResponse();
	 * tur.setStatus("Not Found"); tur.setMessage("Account does not exist");
	 * 
	 * Optional<Transporter> t = Optional.of(transporter1);
	 * when(transporterDao.findById("transporter:01")).thenReturn(t);
	 * //assertEquals(tur, service.updateTransporter("transporter:10",
	 * transporter1)); }
	 * 
	 * //Unit Testing for updating with phone number
	 * 
	 * @Test public void updateWithPhoneNoTest() { transporter1.setName("John");
	 * transporter1.setPhoneNo(1234567890); TransporterUpdateResponse tur = new
	 * TransporterUpdateResponse(); tur.setStatus("Error");
	 * tur.setMessage("Phone number cannot be changed");
	 * 
	 * Optional<Transporter> t = Optional.of(transporter1);
	 * when(transporterDao.findById("transporter:01")).thenReturn(t);
	 * //assertEquals(tur, service.updateTransporter("transporter:01",
	 * transporter1)); }
	 * 
	 * //Unit Testing for deletion with correct id
	 * 
	 * @Test public void deleteWithCorrectIdTest() { TransporterDeleteResponse tdr =
	 * new TransporterDeleteResponse(); tdr.setStatus("Success");
	 * tdr.setMessage("Account deleted successfully");
	 * 
	 * Optional<Transporter> t = Optional.of(transporter1);
	 * when(transporterDao.findById("transporter:01")).thenReturn(t);
	 * assertEquals(tdr, service.deleteTransporter("transporter:01"));
	 * verify(transporterDao,times(1)).delete(transporter1); }
	 * 
	 * ////Unit Testing for deletion with incorrect id
	 * 
	 * @Test public void deleteWithIncorrectIdTest() { TransporterDeleteResponse tdr
	 * = new TransporterDeleteResponse(); tdr.setStatus("Not Found");
	 * tdr.setMessage("Account does not exist");
	 * 
	 * Optional<Transporter> t = Optional.of(transporter1);
	 * when(transporterDao.findById("transporter:01")).thenReturn(t);
	 * assertEquals(tdr, service.deleteTransporter("transporter:15"));
	 * verify(transporterDao,times(0)).delete(transporter1); }
	 * 
	 */	 

}

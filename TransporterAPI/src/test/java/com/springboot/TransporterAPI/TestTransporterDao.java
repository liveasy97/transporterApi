package com.springboot.TransporterAPI;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Id;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;

@DataJpaTest
public class TestTransporterDao {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private TransporterDao transporterdao;
	
	@Test
	public void demo()
	{
		System.err.println("****************");
		System.err.println("working");
		System.err.println("****************");
	}
	
	@Test
	public void findByPhoneNosuccess()
	{
		System.err.println("findByPhoneNosuccess");
		List<Transporter> transporters = createTransporters();
		
		Transporter savedInDb = entityManager.persist(transporters.get(0));
		Optional<Transporter> getFromDb = transporterdao.findByPhoneNo(9999999991L);
		
		assertThat(getFromDb).isEqualTo(Optional.of(savedInDb));
	}
	
	@Test
	public void findByPhoneNofail()
	{
		System.err.println("findByPhoneNosuccess");
		List<Transporter> transporters = createTransporters();
		
		Transporter savedInDb = entityManager.persist(transporters.get(1));
		Optional<Transporter> getFromDb = transporterdao.findByPhoneNo(9999999991L);
		
		assertThat(getFromDb).isEqualTo(Optional.empty());
	}
	
	
	@Test
	public void getAll()
	{
		for(int i=1; i<=16; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-000000000000"+i, (Long) 9999999990L+i,
					"transporter1", "company1", "Nagpur", "link1", false, false, false));
		}
		
		PageRequest firstPage = PageRequest.of(0, (int) CommonConstants.pagesize),
			    secondPage = PageRequest.of(1, (int) CommonConstants.pagesize),
			    thirdPage = PageRequest.of(2, (int) CommonConstants.pagesize);
		
		List<Transporter> getfromDb1 = transporterdao.getAll(firstPage);
		assertThat(getfromDb1.size()).isEqualTo(CommonConstants.pagesize);
		
		List<Transporter> getfromDb2 = transporterdao.getAll(secondPage);
		assertThat(getfromDb2.size()).isEqualTo(1);
		
		List<Transporter> getfromDb3 = transporterdao.getAll(thirdPage);
		assertThat(getfromDb3.size()).isEqualTo(0);
	}
	
	@Test
	public void findByCompanyApproved()
	{
		for(int i=1; i<=33; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-000000000000"+i, (Long) 9999999990L+i,
					"transporter1", "company1", "Nagpur", "link1", (i%2==1), false, false));
		}
		PageRequest firstPage = PageRequest.of(0, (int) CommonConstants.pagesize),
		        secondPage = PageRequest.of(1, (int) CommonConstants.pagesize),
		        thirdPage = PageRequest.of(2, (int) CommonConstants.pagesize);

       List<Transporter> getFromDb1 = transporterdao.findByCompanyApproved(true,firstPage);
       assertThat(getFromDb1.size()).isEqualTo(CommonConstants.pagesize);
    
       List<Transporter> getFromDb2 = transporterdao.findByCompanyApproved(true,secondPage);
       assertThat(getFromDb2.size()).isEqualTo(2);

       List<Transporter> getFromDb3 = transporterdao.findByCompanyApproved(true,thirdPage);
       assertThat(getFromDb3.size()).isEqualTo(0);
    
       List<Transporter> getFromDb4 = transporterdao.findByCompanyApproved(false,firstPage);
       assertThat(getFromDb4.size()).isEqualTo(CommonConstants.pagesize);
    
       List<Transporter> getFromDb5 = transporterdao.findByCompanyApproved(false,secondPage);
       assertThat(getFromDb5.size()).isEqualTo(1);

       List<Transporter> getFromDb6 = transporterdao.findByCompanyApproved(false,thirdPage);
       assertThat(getFromDb6.size()).isEqualTo(0);
		
	}
	
	//transporterapproved
	
	@Test
	public void findByTransporterApproved()
	{
		for(int i=1; i<=33; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-000000000000"+i, (Long) 9999999990L+i,
					"transporter1", "company1", "Nagpur", "link1", false, (i%2==1), false));
		}
		PageRequest firstPage = PageRequest.of(0, (int) CommonConstants.pagesize),
		        secondPage = PageRequest.of(1, (int) CommonConstants.pagesize),
		        thirdPage = PageRequest.of(2, (int) CommonConstants.pagesize);

       List<Transporter> getFromDb1 = transporterdao.findByTransporterApproved(true,firstPage);
       assertThat(getFromDb1.size()).isEqualTo(CommonConstants.pagesize);
    
       List<Transporter> getFromDb2 = transporterdao.findByTransporterApproved(true,secondPage);
       assertThat(getFromDb2.size()).isEqualTo(2);

       List<Transporter> getFromDb3 = transporterdao.findByTransporterApproved(true,thirdPage);
       assertThat(getFromDb3.size()).isEqualTo(0);
    
       List<Transporter> getFromDb4 = transporterdao.findByTransporterApproved(false,firstPage);
       assertThat(getFromDb4.size()).isEqualTo(CommonConstants.pagesize);
    
       List<Transporter> getFromDb5 = transporterdao.findByTransporterApproved(false,secondPage);
       assertThat(getFromDb5.size()).isEqualTo(1);

       List<Transporter> getFromDb6 = transporterdao.findByTransporterApproved(false,thirdPage);
       assertThat(getFromDb6.size()).isEqualTo(0);
		
	}
	
	//findByTransporterCompanyApproved
	
	@Test
	public void findByTransporterCompanyApproved()
	{
		for(int i=1; i<=33; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-000000000000"+i, (Long) 9999999990L+i,
					"transporter1", "company1", "Nagpur", "link1", false, (i%2==1), false));
		}
		PageRequest firstPage = PageRequest.of(0, (int) CommonConstants.pagesize),
		        secondPage = PageRequest.of(1, (int) CommonConstants.pagesize),
		        thirdPage = PageRequest.of(2, (int) CommonConstants.pagesize);

       List<Transporter> getFromDb1 = transporterdao.findByTransporterCompanyApproved(true,false,firstPage);
       assertThat(getFromDb1.size()).isEqualTo(CommonConstants.pagesize);
    
       List<Transporter> getFromDb2 = transporterdao.findByTransporterCompanyApproved(true,false,secondPage);
       assertThat(getFromDb2.size()).isEqualTo(2);

       List<Transporter> getFromDb3 = transporterdao.findByTransporterCompanyApproved(true,false,thirdPage);
       assertThat(getFromDb3.size()).isEqualTo(0);
    
       List<Transporter> getFromDb4 = transporterdao.findByTransporterCompanyApproved(false,false,firstPage);
       assertThat(getFromDb4.size()).isEqualTo(CommonConstants.pagesize);
    
       List<Transporter> getFromDb5 = transporterdao.findByTransporterCompanyApproved(false,false,secondPage);
       assertThat(getFromDb5.size()).isEqualTo(1);

       List<Transporter> getFromDb6 = transporterdao.findByTransporterCompanyApproved(false,false,thirdPage);
       assertThat(getFromDb6.size()).isEqualTo(0);
		
	}
	
	public List<Transporter> createTransporters()
	{
		List<Transporter> transporters = Arrays.asList( 
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001", (Long) 9999999991L,
				"transporter1", "company1", "Nagpur", "link1", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000002", (Long) 9999999992L,
				"transporter2", "company2", "Nagpur", "link2", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000003", (Long) 9999999993L,
				"transporter3", "company3", "Nagpur", "link3", false, false, false),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000004", (Long) 9999999994L,
				"transporter4", "company4", "Nagpur", "link4", false, false, false)
	    );
		
		return transporters;
	}

}

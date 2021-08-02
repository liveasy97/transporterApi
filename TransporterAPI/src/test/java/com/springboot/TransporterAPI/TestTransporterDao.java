package com.springboot.TransporterAPI;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Id;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;

@DataJpaTest
public class TestTransporterDao {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private TransporterDao transporterdao;
	
	private static int pagesize=15;
	
	@Test
	public void findByPhoneNosuccess()
	{
		List<Transporter> transporters = createTransporters();
		
		Transporter savedInDb = entityManager.persist(transporters.get(0));
		Optional<Transporter> getFromDb = transporterdao.findByPhoneNo("9999999991");
		
		assertThat(getFromDb).isEqualTo(Optional.of(savedInDb));
	}
	
	@Test
	public void findByPhoneNofail()
	{
		List<Transporter> transporters = createTransporters();
		
		Transporter savedInDb = entityManager.persist(transporters.get(1));
		Optional<Transporter> getFromDb = transporterdao.findByPhoneNo("9999999991");
		
		assertThat(getFromDb).isEqualTo(Optional.empty());
	}
	
	
	@Test
	public void getAll()
	{
		List<Transporter> transporters = new ArrayList<Transporter>();
		for(int i=1; i<=9; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-00000000000"+i, 
					"999999999"+i,"transporter1", "company1", "Nagpur", "link1", false, false, false, 
					Timestamp.valueOf("2021-07-28 23:28:50.134")));
			transporters.add(savedInDb);
		}

        Collections.reverse(transporters);
		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
				    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
				    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		assertThat(transporters.subList(0, 5)).isEqualTo(transporterdao.getAll(firstPage));
		assertThat(transporters.subList(5, 9)).isEqualTo(transporterdao.getAll(secondPage));
		assertThat(transporters.subList(9, 9)).isEqualTo(transporterdao.getAll(thirdPage));
	}
	
	@Test
	public void findByCompanyApproved()
	{
		List<Transporter> transporterstrue = new ArrayList<Transporter>();
		List<Transporter> transportersfalse = new ArrayList<Transporter>();
		for(int i=11; i<=28; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-00000000000"+i, 
					"99999999"+i,"transporter1", "company1", "Nagpur", "link1", (i%2==1), false, false, 
					Timestamp.valueOf("2021-07-28 23:28:50.134")));
			if(i%2==1) transporterstrue.add(savedInDb);
			else  transportersfalse.add(savedInDb);
		}
		Collections.reverse(transporterstrue);
		Collections.reverse(transportersfalse);
		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
		        secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
		        thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");

		assertThat(transporterstrue.subList(0, 5)).isEqualTo(transporterdao.findByCompanyApproved(true,firstPage));
	    assertThat(transporterstrue.subList(5, 9)).isEqualTo(transporterdao.findByCompanyApproved(true,secondPage));
	    assertThat(transporterstrue.subList(9, 9)).isEqualTo(transporterdao.findByCompanyApproved(true,thirdPage));
	    
	    assertThat(transportersfalse.subList(0, 5)).isEqualTo(transporterdao.findByCompanyApproved(false,firstPage));
	    assertThat(transportersfalse.subList(5, 9)).isEqualTo(transporterdao.findByCompanyApproved(false,secondPage));
	    assertThat(transportersfalse.subList(9, 9)).isEqualTo(transporterdao.findByCompanyApproved(false,thirdPage));
		
	}
	
	//transporterapproved
	
	@Test
	public void findByTransporterApproved()
	{
		List<Transporter> transporterstrue = new ArrayList<Transporter>();
		List<Transporter> transportersfalse = new ArrayList<Transporter>();
		for(int i=11; i<=28; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-00000000000"+i, 
					"99999999"+i,"transporter1", "company1", "Nagpur", "link1", false, i%2==1, false, 
					Timestamp.valueOf("2021-07-28 23:28:50.134")));
			if(i%2==1) transporterstrue.add(savedInDb);
			else  transportersfalse.add(savedInDb);
		}
		Collections.reverse(transporterstrue);
		Collections.reverse(transportersfalse);
		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
		        secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
		        thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");

		assertThat(transporterstrue.subList(0, 5)).isEqualTo(transporterdao.findByTransporterApproved(true,firstPage));
	    assertThat(transporterstrue.subList(5, 9)).isEqualTo(transporterdao.findByTransporterApproved(true,secondPage));
	    assertThat(transporterstrue.subList(9, 9)).isEqualTo(transporterdao.findByTransporterApproved(true,thirdPage));
	    
	    assertThat(transportersfalse.subList(0, 5)).isEqualTo(transporterdao.findByTransporterApproved(false,firstPage));
	    assertThat(transportersfalse.subList(5, 9)).isEqualTo(transporterdao.findByTransporterApproved(false,secondPage));
	    assertThat(transportersfalse.subList(9, 9)).isEqualTo(transporterdao.findByTransporterApproved(false,thirdPage));
	}
	
	//findByTransporterCompanyApproved
	
	@Test
	public void findByTransporterCompanyApproved()
	{
		List<Transporter> transporterstrue = new ArrayList<Transporter>();
		List<Transporter> transportersfalse = new ArrayList<Transporter>();
		for(int i=11; i<=28; i++)
		{
			Transporter savedInDb = entityManager.persist(new Transporter("transporter:0de885e0-5f43-4c68-8dde-00000000000"+i, 
					"99999999"+i,"transporter1", "company1", "Nagpur", "link1", i%2==0, i%2==1, false,  
					Timestamp.valueOf("2021-07-28 23:28:50.134")));
			if(i%2==1) transporterstrue.add(savedInDb);
			else  transportersfalse.add(savedInDb);
		}
		Collections.reverse(transporterstrue);
		Collections.reverse(transportersfalse);
		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
		        secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
		        thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
       
       assertThat(transporterstrue.subList(0, 5)).isEqualTo(transporterdao.findByTransporterCompanyApproved(true,false,firstPage));
	   assertThat(transporterstrue.subList(5, 9)).isEqualTo(transporterdao.findByTransporterCompanyApproved(true,false,secondPage));
	   assertThat(transporterstrue.subList(9, 9)).isEqualTo(transporterdao.findByTransporterCompanyApproved(true,false,thirdPage));
	    
	   assertThat(transportersfalse.subList(0, 5)).isEqualTo(transporterdao.findByTransporterCompanyApproved(false,true,firstPage));
	   assertThat(transportersfalse.subList(5, 9)).isEqualTo(transporterdao.findByTransporterCompanyApproved(false,true,secondPage));
	   assertThat(transportersfalse.subList(9, 9)).isEqualTo(transporterdao.findByTransporterCompanyApproved(false,true,thirdPage));
		
	}
	
	public List<Transporter> createTransporters()
	{
		List<Transporter> transporters = Arrays.asList( 
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000001", 
				"9999999991","transporter1", "company1", "Nagpur", "link1", false, false, false, 
				Timestamp.valueOf("2021-07-28 23:28:50.134")),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000002", 
				"9999999992","transporter1", "company1", "Nagpur", "link1", false, false, false, 
				Timestamp.valueOf("2021-07-28 23:28:50.134")),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000003", 
				"9999999993","transporter1", "company1", "Nagpur", "link1", false, false, false, 
				Timestamp.valueOf("2021-07-28 23:28:50.134")),
		new Transporter("transporter:0de885e0-5f43-4c68-8dde-0000000000004", 
				"9999999994","transporter1", "company1", "Nagpur", "link1", false, false, false, 
				Timestamp.valueOf("2021-07-28 23:28:50.134"))
	    );
		
		return transporters;
	}

}

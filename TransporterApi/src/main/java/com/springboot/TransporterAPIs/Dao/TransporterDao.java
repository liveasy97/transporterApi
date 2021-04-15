package com.springboot.TransporterAPIs.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.TransporterAPIs.Entity.Transporter;


public interface TransporterDao extends JpaRepository<Transporter, UUID> {

}

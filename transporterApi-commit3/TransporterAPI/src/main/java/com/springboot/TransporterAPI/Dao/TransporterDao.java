package com.springboot.TransporterAPI.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.TransporterAPI.Entity.Transporter;

public interface TransporterDao extends JpaRepository<Transporter, UUID>  {

}

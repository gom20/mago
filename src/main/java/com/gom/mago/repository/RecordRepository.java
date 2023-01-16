package com.gom.mago.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gom.mago.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long>{
	
	 Page<Record> findByEmail(String email, Pageable pageable);
	 
	 void deleteByEmail(String email);
	 
}

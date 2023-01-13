package com.gom.mago.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gom.mago.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long>{
	
	 Optional<Record> findByUid(String id);
	 
	 List<Record> findByEmail(String email);
	 
	 Page<Record> findByEmail(String email, Pageable pageable);
	 
	 void deleteByEmail(String email);
	 
//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Query("delete from Record r where r.emailId = :emailId")
//    void deleteAllByEmail(String emailId);
    
}

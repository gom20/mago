package com.gom.mago.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gom.mago.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long>{
	 Optional<Record> findByUid(String id);
	 List<Record> findByEmail(String email);
}

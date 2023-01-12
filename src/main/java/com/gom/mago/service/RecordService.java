package com.gom.mago.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.record.CreateRecordDTO;
import com.gom.mago.dto.record.RecordDTO;
import com.gom.mago.entity.Record;
import com.gom.mago.repository.RecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordRepository recordRepository;
	private final ModelMapper modelMapper;	

	@Transactional
	public CreateRecordDTO.Response createRecord(CreateRecordDTO.Request request) {
    	Record record = request.toEntity();
        return modelMapper.map(recordRepository.save(record), CreateRecordDTO.Response.class);
	}
	
	@Transactional
	public List<RecordDTO> getAllRecords(String email) {
		List<Record> records = recordRepository.findByEmail(email);
		List<RecordDTO> collect = records.stream().map(record -> RecordDTO.fromEntity(record)).collect(Collectors.toList());
		return collect;
	}
	
	@Transactional
	public Page<RecordDTO> getRecords(Pageable pageable, String email) {
		Page<Record> pages = recordRepository.findByEmail(email, pageable);
		return pages.map(record -> RecordDTO.fromEntity(record));
	}
	
}
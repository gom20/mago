package com.gom.mago.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.post.CreateRecordDTO;
import com.gom.mago.dto.post.RecordDTO;
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
	public List<RecordDTO> getPosts(String email) {
		List<Record> records = recordRepository.findByEmail(email);
		List<RecordDTO> collect = records.stream().map(p -> RecordDTO.fromEntity(p)).collect(Collectors.toList());
		return collect;
	}
	
}
package com.gom.mago.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.record.CreateRecordDTO;
import com.gom.mago.dto.record.DeleteRecordDTO;
import com.gom.mago.dto.record.RecordDTO;
import com.gom.mago.entity.Record;
import com.gom.mago.repository.RecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordRepository recordRepository;
	private final ModelMapper modelMapper;	

	/**
	 * 레코드 등록 서비스
	 * @param request 레코드 정보
	 * @return 생성된 레코드 정보
	 */
	@Transactional
	public CreateRecordDTO.Response createRecord(CreateRecordDTO.Request request) {
		// 하이킹 시간
		int totalTime = (int) ChronoUnit.SECONDS.between(request.getStartDatetime(), request.getEndDatetime());
		BigDecimal hikingTime = new BigDecimal(totalTime).subtract(new BigDecimal(String.valueOf(request.getBreakTime())));
		
		// 평균 속도
		BigDecimal hikingHours = hikingTime.divide(new BigDecimal("3600"), 5, RoundingMode.CEILING);
		BigDecimal distance = new BigDecimal(String.valueOf(request.getDistance()));
		Float avgSpeed = distance.divide(hikingHours, 5, RoundingMode.CEILING).floatValue();
		
		Record record = request.toEntity();
		record.setAvgSpeed(avgSpeed);
		record.setTotalTime(totalTime);
		record.setHikingTime(hikingTime.intValue());
		
        return modelMapper.map(recordRepository.save(record), CreateRecordDTO.Response.class);
	}

	/**
	 * 레코드 조회 서비스
	 * @param pageable 페이지 요청 정보
	 * @param email 로그인 이메일
	 * @return 레코드 리스트
	 */
	@Transactional
	public Page<RecordDTO> getRecords(Pageable pageable, String email) {
		Page<Record> pages = recordRepository.findByEmail(email, pageable);
		return pages.map(record -> RecordDTO.fromEntity(record));
	}
	
	/**
	 * 레코드 삭제 서비스 by 레코드ID
	 * @param request 레코드 ID 리스트
	 * @param email 로그인 이메일
	 * @return
	 */
	@Transactional
	public DeleteRecordDTO deleteByIds(DeleteRecordDTO request, String email) {
		List<Record> recordsToDelete = recordRepository.findAllById(request.getIds()).stream()
				.filter(record -> email.equals(record.getEmail())).collect(Collectors.toList());
		recordRepository.deleteAll(recordsToDelete);	
		return DeleteRecordDTO.builder().ids(recordsToDelete.stream().map(record -> record.getUid()).toList()).build();
	}
	
	/**
	 * 레코드 삭제 서비스 by 이메일
	 * @param email 로그인 이메일
	 */
	@Transactional
	public void deleteByEmail(String email) {
		recordRepository.deleteByEmail(email);
	}
}
package com.gom.mago.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.stamp.StampDTO;
import com.gom.mago.dto.stamp.UpdateStampDTO;
import com.gom.mago.entity.MountainStamp;
import com.gom.mago.entity.Stamp;
import com.gom.mago.repository.StampRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StampService {

	private final StampRepository stampRepository;
	private final ModelMapper modelMapper;		
	
	@Transactional
	public List<StampDTO> getMountainStamps(String email) {
		List<MountainStamp> mountainStamps = stampRepository.findMountainStampsByEmail(email);
		return mountainStamps.stream().map(m->StampDTO.fromEntity(m)).collect(Collectors.toList());
	}


	@Transactional
	public UpdateStampDTO.Response updateMountainStamp(UpdateStampDTO.Request request) {
		Stamp stamp = modelMapper.map(request, Stamp.class);
		Optional<Stamp> selectedStamp = stampRepository.findByEmailAndMountainId(stamp.getEmail(), stamp.getMountainId());
		if(selectedStamp.isPresent()) {
			stamp.setUid(selectedStamp.get().getUid());
		}
        return modelMapper.map(stampRepository.save(stamp), UpdateStampDTO.Response.class);
	}
	
}
package com.gom.mago.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gom.mago.entity.MountainStamp;
import com.gom.mago.entity.Stamp;

public interface StampRepository extends JpaRepository<Stamp, Long> {
	Optional<Stamp> findByEmailAndMountainId(String email, Long mountainId);

	@Query(nativeQuery = true, value = 
					"SELECT M.UID AS mountainId, M.MOUNTAIN_NAME AS mountainName, " + 
						"M.REGION_TYPE AS regionType, M.REGION_NAME AS regionName, " + 
						"M.POSITION_X AS positionX, M.POSITION_Y AS positionY, IFNULL(S.FLAG, 0) flag " + 
					"FROM MOUNTAIN M " + 
					"LEFT JOIN STAMP S " + 
					"ON M.UID = S.MOUNTAIN_ID AND S.EMAIL=:email")
	List<MountainStamp> findMountainStampsByEmail(@Param("email") String email);
	
	void deleteByEmail(String email);

}
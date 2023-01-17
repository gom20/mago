package com.gom.mago.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Record {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    
    @Column(updatable = false)
    private String email;
    
    @Column(nullable = false)
    private String mountain;
    
    @Column(nullable = false)
    private String yymmdd;
    
    @Column(nullable = false)
    private LocalDateTime startDatetime;
    
    @Column(nullable = false)
    private LocalDateTime endDatetime;
    
    @Column(nullable = false)
    private Float distance;
    
    @Column(nullable = false)
    private Float minAltitude;
    
    @Column(nullable = false)
    private Float maxAltitude;
    
    @Column(nullable = false)
    private String imgPath;
    
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    
 
}

package com.ashokit.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "AIT_STUDENT_ENQUERIES")
public class StudentEnqEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	
	private String studentName;
	
	@Column(name = "student_phno")
	private String studentPhno;
	
	private String classMode;
	
	private String courseName;
	
	@Column(name = "enq_status")
	private String enqStatus;
	
	@CreationTimestamp
	private LocalDate dateCreated;
	
	@UpdateTimestamp
	private LocalDate lastUpdated;
	
	  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	  @JoinColumn(name = "userId") 
	  private UserDtlsEntity user;
	 
	

}

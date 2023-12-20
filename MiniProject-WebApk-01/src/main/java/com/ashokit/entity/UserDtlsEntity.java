package com.ashokit.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Collate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "AIT_USER_DETAILS")
public class UserDtlsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String name;

	private String emailId;

	private String pwd;

	private String mobileNumber;

	private String accStatus;

	@OneToMany(mappedBy = "user",
			   cascade = CascadeType.ALL,
			   fetch = FetchType.EAGER)
	@Column(name = "enq_status")
	private List<StudentEnqEntity> enquiries;

}

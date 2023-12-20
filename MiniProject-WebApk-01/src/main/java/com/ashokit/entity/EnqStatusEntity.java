package com.ashokit.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "AIT_ENQUIRY_STATUS")
public class EnqStatusEntity 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer statusId;
   
   private String statusName;
}

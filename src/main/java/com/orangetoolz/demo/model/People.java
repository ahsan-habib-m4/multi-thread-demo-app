package com.orangetoolz.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
@Entity
@Table(name = "People")
public class People {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long recordId;
	
	@CsvBindByPosition(position = 1)
	private String userId;
	
	@CsvBindByPosition(position = 2)
	private String firstName;
	
	@CsvBindByPosition(position = 3)
	private String lastName;
	
	@CsvBindByPosition(position = 4)
	private String sex;
	
	@CsvBindByPosition(position = 5)
	private String email;
	
	@CsvBindByPosition(position = 6)
	private String phone;
	
	@CsvBindByPosition(position = 7)
	private String dateOfBirth;
	
	@CsvBindByPosition(position = 8)
	private String jobTitle;
}

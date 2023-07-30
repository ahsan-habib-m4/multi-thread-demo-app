package com.orangetoolz.demo.service;

import java.util.List;

import com.orangetoolz.demo.model.People;

public interface PeopleService {

	public People save(People people);
	
	public Iterable<People> getAllPeople();

	public List<People> getPeople( long limit, long offset);
	
	public Long getTotalPeople();
}

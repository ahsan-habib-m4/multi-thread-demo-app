package com.orangetoolz.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.orangetoolz.demo.model.People;

@Repository
public interface PeopleRepo extends CrudRepository<People, Long> {

}

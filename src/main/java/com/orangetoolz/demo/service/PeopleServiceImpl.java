package com.orangetoolz.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.orangetoolz.demo.model.People;
import com.orangetoolz.demo.repo.PeopleRepo;

@Service
public class PeopleServiceImpl implements PeopleService {
	
	@Autowired @Qualifier("jdbcTemplate") private JdbcTemplate jdbcTemplate;
	
	private static final String GET_PEOPLE_QUERY = "select * from people ";
	
	private static final String COUNT_PEOPLE_QUERY = "select count(*) from people ";
	
	@Autowired PeopleRepo repo;

	@Override
	public People save(People people) {
		return repo.save(people);
	}

	@Override
	public Iterable<People> getAllPeople() {
		
		
		return repo.findAll();
		
	}
	
	@Override
	public List<People> getPeople( long limit, long offset) {
		StringBuilder query = new StringBuilder();
		query.append(GET_PEOPLE_QUERY);
		
		query.append(" OFFSET ? LIMIT ? ");
		Object[] paramList = getParamListForManifest(false, offset, limit);
		List<People> peoples = jdbcTemplate.query(query.toString(), paramList,  new RowMapper<People>() {
 
			    public People mapRow(ResultSet rs, int rowNum) throws SQLException {
			    	System.out.println(rs.toString());
			    	People people = new People();
			    	people.setUserId(rs.getString(9));
			    	people.setFirstName(rs.getString(4));
			    	people.setLastName(rs.getString(6));
			    	people.setEmail(rs.getString(3));
			    	people.setPhone(rs.getString(7));
			    	people.setDateOfBirth(rs.getString(2));
			    	people.setSex(rs.getString(8));
			        return people;
			    }
			});
		
		return peoples;
	}
	
	@Override
	public Long getTotalPeople() {
		StringBuilder query = new StringBuilder();
		query.append(COUNT_PEOPLE_QUERY);
		
		return jdbcTemplate.queryForObject(query.toString(), Long.class);
	}

	private Object[] getParamListForManifest(Boolean isCount, long offset, long limit) {
		List<Object> list = new ArrayList<Object>();
		if (isCount) return list.toArray(new Object[0]);
		list.add(offset);
		list.add(limit);
		return list.toArray(new Object[0]);
	}

}

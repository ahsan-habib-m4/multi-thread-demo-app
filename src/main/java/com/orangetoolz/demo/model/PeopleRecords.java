package com.orangetoolz.demo.model;

import java.util.List;

import lombok.Data;

@Data
public class PeopleRecords {
	private Long recordsTotal;
	private Long recordsFiltered;
	private List<People> PeopleList;
}

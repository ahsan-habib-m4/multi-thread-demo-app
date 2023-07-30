package com.orangetoolz.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.orangetoolz.demo.model.People;
import com.orangetoolz.demo.model.PeopleRecords;
import com.orangetoolz.demo.service.PeopleService;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired PeopleService peopleService;
	
	@GetMapping
	public String show() {
		return "home";
	}
	
	@GetMapping("/peoples")
	@ResponseBody
	public PeopleRecords getAllPeople( HttpServletRequest req ) {
		
		Long offset = Long.parseLong(req.getParameter("offset"));
		Long limit = Long.parseLong(req.getParameter("limit"));
		
		List<People> ppls = peopleService.getPeople(limit, offset);
		long total = peopleService.getTotalPeople();
		
		PeopleRecords data = new PeopleRecords();
		data.setPeopleList(ppls);
		data.setRecordsTotal(total);
		data.setRecordsFiltered(total);
		
		return data;
	}
	
	@ResponseBody
	@PostMapping("api/upload")
	public String upload(MultipartFile file) throws IOException, CsvValidationException {
		
		final int NUM_THREADS = 10;
		
		BufferedReader reader = new BufferedReader( new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
		
		CsvToBean<People> csvToBean = new CsvToBeanBuilder<People>(reader)
				.withType(People.class)
				.withIgnoreLeadingWhiteSpace(true)
				.withSkipLines(1)
				.build();
		
		Iterator<People> csvUserIterator = csvToBean.iterator();
		
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		
		List<Future<Void>> futures = new ArrayList<>();
		
		while (csvUserIterator.hasNext()) {
			People ppl = csvUserIterator.next();
			Callable<Void> task = () -> {
				peopleService.save(ppl);
				return null;
			};
			Future<Void> future = executor.submit(task);
			futures.add(future);
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executor.shutdown();
		
		for (Future<Void> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}

}

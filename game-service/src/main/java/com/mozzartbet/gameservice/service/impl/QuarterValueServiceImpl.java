package com.mozzartbet.gameservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mozzartbet.gameservice.domain.QuarterValue;
import com.mozzartbet.gameservice.mapper.QuarterValueMapper;
import com.mozzartbet.gameservice.service.QuarterValueService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuarterValueServiceImpl implements QuarterValueService {

	final QuarterValueMapper quarterValueMapper;
	
	public QuarterValue save(QuarterValue quarterValue) {
		quarterValueMapper.save(quarterValue);
		return quarterValue;
	}
	
	public QuarterValue getById(Long id) {
		return quarterValueMapper.getById(id);
	}
	
	public List<QuarterValue> getQuarters(String quarter) {
		 List<QuarterValue> quarterValues = quarterValueMapper.getQuarters(quarter);
		 return quarterValues;
	}
	
}

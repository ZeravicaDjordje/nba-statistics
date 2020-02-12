package com.mozzartbet.gameservice.service;

import java.util.List;

import com.mozzartbet.gameservice.domain.QuarterValue;

public interface QuarterValueService {

	public QuarterValue save(QuarterValue quarterValue);
	
	public QuarterValue getById(Long id);
	
	public List<QuarterValue> getQuarters(String quarter);
	
}

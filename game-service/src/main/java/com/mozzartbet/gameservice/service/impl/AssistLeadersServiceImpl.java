package com.mozzartbet.gameservice.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.domain.AssistLeaders;
import com.mozzartbet.gameservice.mapper.AssistLeadersMapper;
import com.mozzartbet.gameservice.service.AssistLeadersService;

@Service
public class AssistLeadersServiceImpl implements AssistLeadersService {

	@Autowired
	AssistLeadersMapper assitLeadersMapper;
	
	@Override
	public AssistLeaders save(AssistLeaders assitLeaders) {
		assitLeadersMapper.save(assitLeaders);
		return assitLeaders;
	}
	
	@Override
	public List<AssistLeaders> serachByPlayerUrl(String playerUrl) {
		List<AssistLeaders> assistLeaders = assitLeadersMapper.serachByPlayerUrl(playerUrl);
		return assistLeaders;
	}
	
	@Override
	public List<AssistLeaders> searchByGameUrl(String gameUrl) {
		List<AssistLeaders> assistLeaders = assitLeadersMapper.searchByGameUrl(gameUrl);
		return assistLeaders;
	}
	
	final LoadingCache<Long, AssistLeaders> assistLeadersCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, AssistLeaders>() {
				@Override
				public AssistLeaders load(Long id) throws Exception {
					return assitLeadersMapper.getById(id);
				}
			});
	
	@Override
	@Cacheable(cacheNames="AssistLeaders")
	public AssistLeaders getAssistLeadersCached(Long assistLeadersId) {
		return assitLeadersMapper.getById(assistLeadersId);
	}
	
	@Transactional(readOnly=true)
	public AssistLeaders getAssistLeaders(Long assistLeadersId) {
		return assistLeadersCache.getUnchecked(assistLeadersId);
	}
}

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
import com.mozzartbet.gameservice.domain.ReboundLeaders;
import com.mozzartbet.gameservice.mapper.ReboundLeadersMapper;
import com.mozzartbet.gameservice.mapper.ScoreLeadersMapper;
import com.mozzartbet.gameservice.service.ReboundLeadersService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReboundLeadersServiceImpl implements ReboundLeadersService {

	
	final ReboundLeadersMapper reboundLeadersMapper;
	
	@Override
	public ReboundLeaders save(ReboundLeaders rebondLeaders) {
		reboundLeadersMapper.save(rebondLeaders);
		return rebondLeaders;
	}
	
	@Override
	public List<ReboundLeaders> serachByPlayerUrl(String playerUrl) {
		List<ReboundLeaders> reboundLeaders = reboundLeadersMapper.serachByPlayerUrl(playerUrl);
		return reboundLeaders;
	}
	
	@Override
	public List<ReboundLeaders> searchByGameUrl(String gameUrl) {
		List<ReboundLeaders> reboundLeaders = reboundLeadersMapper.searchByGameUrl(gameUrl);
		return reboundLeaders;
	}
	
	final LoadingCache<Long, ReboundLeaders> reboundLeadersCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, ReboundLeaders>() {
				@Override
				public ReboundLeaders load(Long id) throws Exception {
					return reboundLeadersMapper.getById(id);
				}
			});
	
	@Override
	@Cacheable(cacheNames="ReboundLeaders")
	public ReboundLeaders getReboundLeadersCached(Long reboundLeadersId) {
		return reboundLeadersMapper.getById(reboundLeadersId);
	}
	
	@Transactional(readOnly=true)
	public ReboundLeaders getReboundLeaders(Long reboundLeadersId) {
		return reboundLeadersCache.getUnchecked(reboundLeadersId);
	}
}

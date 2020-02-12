package com.mozzartbet.gameservice.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.domain.ScoreLeaders;
import com.mozzartbet.gameservice.mapper.ScoreLeadersMapper;
import com.mozzartbet.gameservice.service.ScoreLeadersService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScoreLeadersServiceImpl implements ScoreLeadersService {

	
	final ScoreLeadersMapper scoreLeadersMapper;
	
	@Override
	public ScoreLeaders save(ScoreLeaders scoreLeaders) {
		scoreLeadersMapper.save(scoreLeaders);
		return scoreLeaders;
	}
	
	@Override
	public List<ScoreLeaders> serachByPlayerUrl(String playerUrl) {
		List<ScoreLeaders> scoreLeaders = scoreLeadersMapper.serachByPlayerUrl(playerUrl);
		return scoreLeaders;
	}
	
	@Override
	public List<ScoreLeaders> searchByGameUrl(String gameUrl) {
		List<ScoreLeaders> scoreLeaders = scoreLeadersMapper.searchByGameUrl(gameUrl);
		return scoreLeaders;
	}
	
	final LoadingCache<Long, ScoreLeaders> scoreLeadersCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(3, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, ScoreLeaders>() {
				@Override
				public ScoreLeaders load(Long id) throws Exception {
					return scoreLeadersMapper.getById(id);
				}
			});
	
	@Override
	@Cacheable(cacheNames="ScoreLeaders")
	public ScoreLeaders getScoreLeadersCached(Long scoreLeadersId) {
		return scoreLeadersMapper.getById(scoreLeadersId);
	}
	
	@Transactional(readOnly=true)
	public ScoreLeaders getScoreLeaders(Long scoreLeadersId) {
		return scoreLeadersCache.getUnchecked(scoreLeadersId);
	}
}

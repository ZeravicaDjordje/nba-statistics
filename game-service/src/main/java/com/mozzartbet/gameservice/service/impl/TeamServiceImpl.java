package com.mozzartbet.gameservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;
import com.mozzartbet.gameservice.exception.PlayerException;
import com.mozzartbet.gameservice.exception.TeamException;
import com.mozzartbet.gameservice.mapper.PlayerMapper;
import com.mozzartbet.gameservice.mapper.TeamMapper;
import com.mozzartbet.gameservice.service.TeamService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
	
	// Get in Mappers all this methods
	// Don't forget to this on 31 7 2019 
	
	
	final TeamMapper teamMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<Team> findTeamByName(String teamName) {
		List<Team> teams = teamMapper.findTeamByName(teamName);
		return teams;
	}
	
	private void checkNameDuplicated(Team team) {
		Team existing = teamMapper.getTeamByName(team.getName());
		if(existing != null && !existing.getId().equals(team.getId())) {
			throw new TeamException(
					TeamException.TeamExceptionCode.DUPLICATED_NAME,
					"Name: %s is duplicated in id=%s", team.getName(),
					existing.getId());	
		}
	}
	
	@Override
	//@CacheEvict(cacheNames="teams", key="#teams.id")
	public Team save(Team team) {
		checkNameDuplicated(team);
		try {
			teamMapper.save(team);
			return team;
		}
		catch (DuplicateKeyException e) {
			throw new TeamException(
					 TeamException.TeamExceptionCode.DUPLICATED_NAME, 
					 "Name: %s is duplicated!", 
					 team.getName());
		}
	}
	
	final LoadingCache<Long, Team> teamCache = CacheBuilder.newBuilder().
			initialCapacity(100).
			maximumSize(1000).
			expireAfterWrite(30, TimeUnit.SECONDS).
			recordStats().
			build(new CacheLoader<Long, Team>() {
				@Override
				public Team load(Long id) throws Exception {
					return teamMapper.getById(id);
				}
			});
			
		
	@Override
	@Cacheable(cacheNames="teams")
	public Team getTeamCached(Long teamId) {
		return teamMapper.getById(teamId);
	}
	
	@Transactional(readOnly=true)
	public Team getTeam(Long teamId) {
		return teamCache.getUnchecked(teamId);
	}
	
	@VisibleForTesting
	public CacheStats teamCacheStats() {
		return teamCache.stats();
	}

	@Override
	public Team searchTeamByUrl(String teamUrl) {
		return teamMapper.searchTeamByUrl(teamUrl);
	}

}

package com.mozzartbet.gameservice.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;

import com.google.common.cache.CacheStats;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;

public interface TeamService {

	public List<Team> findTeamByName(String teamName);
	
	public Team searchTeamByUrl(String teamUrl);
	
	public Team save(Team player);
	
	public Team getTeamCached(Long teamId);
	
	public Team getTeam(Long teamId);
	
	public CacheStats teamCacheStats();
	
}

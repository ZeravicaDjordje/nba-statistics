package com.mozzartbet.gameservice.service;

import java.util.List;


import com.mozzartbet.gameservice.domain.ReboundLeaders;

public interface ReboundLeadersService {

	public List<ReboundLeaders> serachByPlayerUrl(String playerUrl);
	
	public ReboundLeaders save(ReboundLeaders reboundLeaders);
	
	public List<ReboundLeaders> searchByGameUrl(String gameUrl);
	
	public ReboundLeaders getReboundLeadersCached(Long id);
	
	public ReboundLeaders getReboundLeaders(Long id);
	
}

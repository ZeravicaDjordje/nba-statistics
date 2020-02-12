package com.mozzartbet.gameservice.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;

import com.mozzartbet.gameservice.domain.AssistLeaders;


public interface AssistLeadersService {


	public List<AssistLeaders> serachByPlayerUrl(String playerUrl);
	
	public AssistLeaders save(AssistLeaders assitLeaders);
	
	public List<AssistLeaders> searchByGameUrl(String gameUrl);
	
	public AssistLeaders getAssistLeadersCached(Long id);
	
	public AssistLeaders getAssistLeaders(Long id);

}

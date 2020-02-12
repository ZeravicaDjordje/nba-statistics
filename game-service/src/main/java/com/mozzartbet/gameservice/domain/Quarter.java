package com.mozzartbet.gameservice.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.select.Elements;

import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class Quarter {

	Map<String, Map<String, PlayerStatistics>> quarterForTeamOne = new LinkedHashMap<String, Map<String, PlayerStatistics>>();
	Map<String, Map<String, PlayerStatistics>> quarterForTeamTwo = new LinkedHashMap<String, Map<String, PlayerStatistics>>();
	
	
}

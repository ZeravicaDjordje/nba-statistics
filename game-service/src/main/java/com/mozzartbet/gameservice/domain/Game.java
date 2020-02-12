package com.mozzartbet.gameservice.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mozzartbet.gameservice.domain.stats.PlayerStatistics;
import com.mozzartbet.gameservice.domain.stats.Statistics;
import com.mozzartbet.gameservice.domain.stats.TeamStatistics;
import com.mozzartbet.gameservice.domain.stats.Statistics.StatisticsBuilder;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;
import com.mozzartbet.gameservice.util.MinutesPlayed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Game implements BaseEntity {

	private Long id;
	private String gameUrl;
	private String boxUrl;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private List<ReboundLeaders> reboundLeaders;
	private List<ScoreLeaders> scoreLeaders;
	private List<AssistLeaders> assistLeaders;
	private List<QuarterValue> quarterValue;
	private List<TeamStatistics> teamStats;
	private List<PlayerStatistics> playerStats;
	
}

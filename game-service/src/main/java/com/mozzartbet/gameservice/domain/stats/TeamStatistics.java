package com.mozzartbet.gameservice.domain.stats;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.mozzartbet.gameservice.domain.BaseEntity;
import com.mozzartbet.gameservice.parser.ActionParser;
import com.mozzartbet.gameservice.parser.QuarterParser;
import com.mozzartbet.gameservice.parser.TeamMatchParser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TeamStatistics implements BaseEntity{
	
	private Long id;
	private Long teamId;
	private Long gameId;
	private String teamUrl;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String gameUrl;
	private int totalMadeThreePoints;
	private int totalMadeTwoPoints;
	private int totalMissedTwoPoints;
	private int totalMissedThreePoints;
	private int totalDefensiveRebound;
	private int totalOffensiveRebound;
	private int totalMadeFreeThrow;
	private int totalMissedFreeThrow;
	private int totalSteal;
	private int totalAssist;
	private int totalTurnover;
	private int totalBlock;
	private int totalFoul;
	private int totalSumRebound;
	private int totalSumPoints;
	private int totalTimeInPlay;
	private float totalRealizationTwoPoints;
	private float totalRealizationThreePoints;
	private float totalRealizationFreeThrow;
	
}

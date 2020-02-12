package com.mozzartbet.gameservice.domain;

import java.sql.Timestamp;

import com.mozzartbet.gameservice.domain.Game.GameBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ScoreLeaders implements BaseEntity {
	
	private Long id;
	private Long gameId;
	private String player;
	private Long score;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String gameUrl;
}

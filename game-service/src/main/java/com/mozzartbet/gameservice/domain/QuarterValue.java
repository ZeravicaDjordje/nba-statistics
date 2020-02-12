package com.mozzartbet.gameservice.domain;

import java.sql.Timestamp;

import com.mozzartbet.gameservice.domain.Player.PlayerBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuarterValue implements BaseEntity {
	
	private Long id;
	private String quarter;
	private Long score;
	private String teamUrl;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String gameUrl;
	private Long gameId;
	
}

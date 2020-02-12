package com.mozzartbet.gameservice.domain.stats;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mozzartbet.gameservice.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class PlayerStatistics  implements Cloneable, BaseEntity {
	
	public Object clone() throws CloneNotSupportedException{  
		return super.clone();  
	}  
	
	private Map<String, List<String>> enterGame = new HashMap<>();
	private Map<String, List<String>> leaveGame = new HashMap<>();
	private Long id;
	private Long gameId;
	private String playerUrl;
	private String gameUrl;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private int makesThreePoints;
	private int makesTwoPoints;
	private int missesTwoPoints;
	private int missesThreePoints;
	private int defensiveRebound;
	private int offensiveRebound;
	private int makesFreeThrow;
	private int missesFreeThrow;
	private int steal;
	private int assist;
	private int turnover;
	private int block;
	private int foul;
	private int sumRebound;
	private int sumMadePoints;
	private double realizationTwoPoints;
	private double realizationThreePoints;
	private double realizationFreeThrow;
	private String timeInPlay;
	
}

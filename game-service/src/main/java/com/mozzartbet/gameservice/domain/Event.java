package com.mozzartbet.gameservice.domain;

import java.sql.Timestamp;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event implements BaseEntity {
	
	private Long id;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private Long gameDate;
	private String gameUrl;
	private String quarter;
	private String timeStamp;
	private String teamOneScore;
	private String teamTwoScore;
	private String teamOneAction;
	private String teamTwoAction;
	private String scoreSoFar;
	private String teamOne;
	private String teamTwo;
	private String teamOneLink;
	private String teamTwoLink;
	private String event;
	
	public Elements eventToJsoupElement() {
		return Jsoup.parse(event.toString()).getAllElements();
	}
	

}

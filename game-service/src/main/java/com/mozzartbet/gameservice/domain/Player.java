package com.mozzartbet.gameservice.domain;

import java.time.LocalDateTime;
import java.util.Date;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Player implements BaseEntity{
	
	
	private Long id;
	private Long teamId;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String playerUrl;
	private String uniformNumber;
	private String playerName;
	private String position;
	private String height;
	private String width;
	private String birthDate;
	private String yearsOfExp;
	private String college;
	private String teamName;
	private Team team;
	
}
package com.mozzartbet.gameservice.service.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.mozzartbet.gameservice.domain.Team;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerSearchRequest {
	
	private String playerUrl;
	
	private String uniformNumber;
	
	private String teamId;
	
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

package com.mozzartbet.gameservice.service.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.mozzartbet.gameservice.domain.Player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerSearchResponse {

	@NotNull
	@Valid
	List<Player> players;
	
}

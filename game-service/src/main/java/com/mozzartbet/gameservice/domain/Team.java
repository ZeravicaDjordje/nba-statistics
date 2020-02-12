package com.mozzartbet.gameservice.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mozzartbet.gameservice.domain.Player.PlayerBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Team implements BaseEntity {

	private Long id;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String name;
	private String teamUrl;
	private List<Player> players;

}
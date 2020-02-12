package com.mozzartbet.gameservice.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import com.mozzartbet.gameservice.service.*;
import static org.junit.Assert.*;
import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;
import com.mozzartbet.gameservice.exception.PlayerException;
import com.mozzartbet.gameservice.mapper.PlayerMapper;

public class PlayerServiceMockTest extends BaseServiceTest {

	@Autowired
	private PlayerService playerService;
	
	@MockBean
	private PlayerMapper playerMapper;
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testGetDuplicatedWithMock() {
		Mockito.when(playerMapper.save(Mockito.any())).thenThrow(new DuplicateKeyException("Duplicated in test"));
		// possible error - even though save would do an insert, playerMapper is not called
		// when(playerMapper.insert(Mockito.any())).thenThrow(new DuplicateKeyException("Duplicated in test"));

		thrown.expect(PlayerException.class);
		thrown.expectMessage("[DUPLICATED_NAME] Name: Nikola Jokic is duplicated!");
	
		
		Player p3 = Player.builder().team(Team.builder().id(1L).build()).playerName("Nikola Jokic").uniformNumber("4").position("PG").build();
		playerService.save(p3);
	}
}

package com.mozzartbet.gameservice.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.ibatis.javassist.bytecode.stackmap.TypeData.ClassName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.domain.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class PlayerMapperTest {
	
	@Autowired
	private PlayerMapper playerMapper;
	
	@Test
	public void testCrud() {
		
		Player player = Player.builder().
				height("3123").
				width("23").
				birthDate("a").
				college("g").
				position("va").
				uniformNumber("@3").
				yearsOfExp("123").
				playerUrl("c").
				playerName("a").
				teamName("b"). 
				build();
		assertEquals(playerMapper.insert(player), 1);
		System.out.println(playerMapper.getById(Long.valueOf(1)));
		assertTrue(playerMapper.getById(Long.valueOf(1)) instanceof Player);
		assertEquals(playerMapper.update(player), 1);
		assertEquals(playerMapper.deleteById(player.getId()), 1);
	}
}


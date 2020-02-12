package com.mozzartbet.gameservice.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.mozzartbet.gameservice.domain.Player;
import com.mozzartbet.gameservice.service.PlayerService.LockType;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("test")
public class PlayerServiceLatchLockTest {

	@Autowired
	private PlayerService playerService;
	
	@Test
	@Commit
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, statements = 
	
	{
		"delete from player"
	})
	public void testTwoThreads() throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(2);
		PlayerSaveTask1 pt1 = new PlayerSaveTask1();
		PlayerSaveTask2 pt2 = new PlayerSaveTask2();
		System.out.println(pool);
		pool.invokeAll(Arrays.asList(pt2,pt1));
		
		System.out.println("PT1 PLAYER NAME " + pt1.call());
		assertThat(pt1.p.getPlayerName(), equalTo("Nikola Jokic 1"));

		assertThat(pt1.p.getPlayerName(), equalTo("Nikola Jokic 1"));
		assertThat(pt2.caught, is(instanceOf(OptimisticLockingFailureException.class)));
		Player pp = playerService.getPlayer(pt1.p.getId());
		assertThat(pp.getPlayerName(), equalTo("Nikola Jokic 1"));
	}
	
	final CountDownLatch latch = new CountDownLatch(1);
	
	class PlayerSaveTask1 implements Callable<Player> {
		Player p;
		
		@Override
		public Player call() throws Exception {
			System.out.println("Call Method");

			p = playerService.findPlayersByName("Nikola", null).get(0);
			p.setPlayerName(p.getPlayerName() + " 1");
			p = playerService.save(p, LockType.OPTIMISTIC);
			
			latch.countDown();
			
			System.out.println("First Player Save Task 2");
			return p;
		}
	}
	
	class PlayerSaveTask2 implements Callable<Player> {
		Player p;
		Exception caught;
		
		@Override
		public Player call() throws Exception {
			p = playerService.findPlayersByName("Nikola", null).get(0);
			p.setPlayerName(p.getPlayerName() + " 2");

			try {
				latch.await();
				p = playerService.save(p, LockType.OPTIMISTIC);
			}
			
			catch(OptimisticLockingFailureException e) {
				caught = e;
			}
			System.out.println("Second Player Save Task 2");
			return p;
		}
	}
	
}

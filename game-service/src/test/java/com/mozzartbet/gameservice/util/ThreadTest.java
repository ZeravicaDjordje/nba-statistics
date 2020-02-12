package com.mozzartbet.gameservice.util;

import org.junit.Test;

public class ThreadTest {

	// Two types of Thread main and child 
	// Main is automatically created when program runs
	// Child thread is created by Main Thread
	
	public class mainchild implements Runnable {
		
		Thread t1;
		
		public mainchild(String a) {
			t1 = new Thread(this, a);
			t1.start();
		}
		
		public void run() {
			for(int x = 1; x <= 3; x++) {
				System.out.println(x + " this thread is " + Thread.currentThread().getName());
				
			}
		}
	}
	
	@Test
	public void threadTest() {
		
		mainchild m = new mainchild("I AM CHILD");
		for(int x = 1; x <= 3; x++) {
			System.out.println(x + " this thread is " + Thread.currentThread().getName());
			
		}
	}
	
	
}

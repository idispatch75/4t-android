package com.forthune.client.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TransactionTest {

	@Test
	public void testConstructor() {
		Transaction contact = new Transaction("1", 1, 1, "");
		
		TestUtils.assertConstructor(contact, new Transaction("1", 1, 1, ""), false);
	}
	
	@Test
	public void testFullConstructor() {
		Transaction contact = new Transaction("1", 1, "1", 1, 1, "");
		
		TestUtils.assertConstructor(contact, new Transaction("1", 1, "1", 1, 1, ""), true);
	}
}

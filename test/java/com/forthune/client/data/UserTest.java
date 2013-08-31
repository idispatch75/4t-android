package com.forthune.client.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserTest {
	private User user;
	
	@Before
	public void createUser() {
		this.user = new User("id", 1, "user@mail.com");
	}

	@Test
	public void testConstructor() {
		User user = new User("pseudo");
		assertConstructorData(user);
		
		TestUtils.assertConstructor(user, new User("login"), false);
	}
	
	@Test
	public void testFullConstructor() {
		User user = new User("1", 1, "login");
		assertConstructorData(user);
		
		TestUtils.assertConstructor(user, new User("1", 1, "login"), true);
	}
	
	private void assertConstructorData(User user) {
		assertThat(user.getContacts().size(), is(0));
		assertThat(user.getTransactions().size(), is(0));
	}
	
	@Test
	public void testAddRemoveContact() {
		assertThat(this.user.getContacts().size(), is(0));
		
		Contact tx1 = new Contact("1", 1, "1");
		this.user.addContact(tx1);
		assertThat(this.user.getContacts().size(), is(1));
		assertThat(this.user.getContacts().containsValue(tx1), is(true));
		this.user.addContact(tx1);
		assertThat(this.user.getContacts().size(), is(1));
		
		Contact c2 = new Contact("2", 2, "2");
		this.user.addContact(c2);
		assertThat(this.user.getContacts().size(), is(2));
		assertThat(this.user.getContacts().containsValue(tx1), is(true));
		assertThat(this.user.getContacts().containsValue(c2), is(true));
		
		this.user.removeContact(tx1);
		assertThat(this.user.getContacts().size(), is(1));
		assertThat(this.user.getContacts().containsValue(c2), is(true));
		
		this.user.removeContact(tx1);
		assertThat(this.user.getContacts().size(), is(1));
		assertThat(this.user.getContacts().containsValue(c2), is(true));
		
		this.user.removeContact(c2);
		assertThat(this.user.getContacts().size(), is(0));
		
		this.user.removeContact(c2);
		assertThat(this.user.getContacts().size(), is(0));
	}
	
	@Test
	public void testAddRemoveTransaction() {
		assertThat(this.user.getTransactions().size(), is(0));
		
		Transaction tx1 = new Transaction("1", 1, "1", 1, 1, "1");
		this.user.addTransaction(tx1);
		assertThat(this.user.getTransactions().size(), is(1));
		assertThat(this.user.getTransactions().containsValue(tx1), is(true));
		this.user.addTransaction(tx1);
		assertThat(this.user.getTransactions().size(), is(1));
		
		Transaction tx2 = new Transaction("2", 1, "1", 1, 1, "1");
		this.user.addTransaction(tx2);
		assertThat(this.user.getTransactions().size(), is(2));
		assertThat(this.user.getTransactions().containsValue(tx1), is(true));
		assertThat(this.user.getTransactions().containsValue(tx2), is(true));
		
		this.user.removeTransaction(tx1);
		assertThat(this.user.getTransactions().size(), is(1));
		assertThat(this.user.getTransactions().containsValue(tx2), is(true));
		
		this.user.removeTransaction(tx1);
		assertThat(this.user.getTransactions().size(), is(1));
		assertThat(this.user.getTransactions().containsValue(tx2), is(true));
		
		this.user.removeTransaction(tx2);
		assertThat(this.user.getTransactions().size(), is(0));
		
		this.user.removeTransaction(tx2);
		assertThat(this.user.getTransactions().size(), is(0));
	}
}

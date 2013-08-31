package com.forthune.client.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DataTest {
	private Data data;
	
	@Before
	public void createUser() {
		this.data = new Data();
	}

	@Test
	public void testConstructor() {
		assertThat(this.data.getUsers().size(), is(0));
		assertThat(this.data.isDirty(), is(false));
	}
	
	@Test
	public void testAddRemoveUser() {
		assertThat(this.data.getUsers().size(), is(0));
		
		User user1 = new User("1", 1, "1");
		this.data.addUser(user1);
		assertThat(this.data.getUsers().size(), is(1));
		assertThat(this.data.getUsers().containsValue(user1), is(true));
		this.data.addUser(user1);
		assertThat(this.data.getUsers().size(), is(1));
		
		User user2 = new User("2", 2, "2");
		this.data.addUser(user2);
		assertThat(this.data.getUsers().size(), is(2));
		assertThat(this.data.getUsers().containsValue(user1), is(true));
		assertThat(this.data.getUsers().containsValue(user2), is(true));
		
		this.data.removeUser(user1);
		assertThat(this.data.getUsers().size(), is(1));
		assertThat(this.data.getUsers().containsValue(user2), is(true));
		
		this.data.removeUser(user1);
		assertThat(this.data.getUsers().size(), is(1));
		assertThat(this.data.getUsers().containsValue(user2), is(true));
		
		this.data.removeUser(user2);
		assertThat(this.data.getUsers().size(), is(0));
		
		this.data.removeUser(user2);
		assertThat(this.data.getUsers().size(), is(0));
	}
}

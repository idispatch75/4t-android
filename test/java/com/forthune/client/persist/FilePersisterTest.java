package com.forthune.client.persist;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forthune.client.data.Contact;
import com.forthune.client.data.Data;
import com.forthune.client.data.Transaction;
import com.forthune.client.data.User;

@RunWith(JUnit4.class)
public class FilePersisterTest {
	private File file;
	private FilePersister persister;
	
	@Before
	public void createPersister() {
		this.file = new File("FilePersisterTest.tmp");
		this.persister = new FilePersister(this.file);
	}
	
	@After
	public void deleteFile() {
		if(this.file.exists()) {
			this.file.delete();
		}
	}

	@Test
	public void testSaveLoad() throws IOException {
		Data data = new Data();
		User user = new User("1");
		data.addUser(user);
		Contact contact = new Contact("1");
		user.addContact(contact);
		Transaction tx = new Transaction("1", 1, 1, "");
		user.addTransaction(tx);
		assertThat(data.isDirty(), is(true));
		assertThat(user.isDirty(), is(true));
		assertThat(contact.isDirty(), is(true));
		assertThat(tx.isDirty(), is(true));
		
		this.persister.save(data);
		
		assertThat(data.isDirty(), is(false));
		assertThat(user.isDirty(), is(false));
		assertThat(contact.isDirty(), is(false));
		assertThat(tx.isDirty(), is(false));
		
		data = this.persister.load();
		assertThat(data.isDirty(), is(false));
		user = data.getUsers().values().iterator().next();
		assertThat(user.isDirty(), is(false));
		contact = user.getContacts().values().iterator().next();
		assertThat(contact.isDirty(), is(false));
		tx = user.getTransactions().values().iterator().next();
		assertThat(tx.isDirty(), is(false));
	}
	
	@Test
	public void testLoadUnfoundFile() throws IOException {
		// an empty data should be returned
		Data data = this.persister.load();
		assertThat(data, notNullValue());
	}
	
	@Test
	public void testSaveTwice() throws IOException {
		// just ensure there is no exception
		Data data = new Data();
		User user = new User("1", 1, "1");
		data.addUser(user);
		this.persister.save(data);
		this.persister.save(data);
		data = this.persister.load();
		assertThat(data, notNullValue());
		assertThat(data.getUsers().size(), is(1));
	}
}

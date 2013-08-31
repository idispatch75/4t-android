package com.forthune.client.persist.protostuff;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.forthune.client.data.AbstractItem;
import com.forthune.client.data.Contact;
import com.forthune.client.data.Data;
import com.forthune.client.data.Transaction;
import com.forthune.client.data.User;
import com.forthune.client.persist.protostuff.ProtostuffPersister;

@RunWith(JUnit4.class)
public class ProtostuffPersisterTest {
	private ProtostuffPersister persister;
	private ByteArrayOutputStream bos;
	
	@Before
	public void createPersister() {
		this.persister = new ProtostuffPersister();
		this.bos = new ByteArrayOutputStream();
	}

	@Test
	public void testEmptyData() throws IOException {
		Data data = new Data();
		data.setDirty();
		this.persister.save(data, this.bos);
		data = this.persister.load(new ByteArrayInputStream(this.bos.toByteArray()));
		assertThat(data.getUsers().size(), is(0));
	}
	
	@Test
	public void testEmptyUser() throws IOException {
		Data data = new Data();
		data.addUser(new User("1", 1, "1"));
		this.persister.save(data, this.bos);
		data = this.persister.load(new ByteArrayInputStream(this.bos.toByteArray()));
		assertThat(data.getUsers().size(), is(1));
		User user = data.getUsers().get("1");
		assertThat(user.getContacts().size(), is(0));
		assertThat(user.getTransactions().size(), is(0));
	}
	
	@Test
	public void testFull() throws IOException {
		Data data = new Data();
		User user1 = new User("1", 1, "1");
		Contact c1 = new Contact("1", 1, "1");
		c1.setIcon(new byte[] {1, 2});
		user1.addContact(c1);
		Contact c2 = new Contact("2", 1, "1");
		user1.addContact(c2);
		Transaction tx1 = new Transaction("1", 1, "1", 1, 1, "1");
		user1.addTransaction(tx1);
		Transaction tx2 = new Transaction("2", 1, "1", 1, 1, "1");
		user1.addTransaction(tx2);
		data.addUser(user1);
		
		User user2 = new User("2", 1, "1");
		user2.addContact(c1);
		user2.addContact(c2);
		Contact c3 = new Contact("3", 1, "1");
		user2.addContact(c3);
		Transaction tx3 = new Transaction("3", 1, "1", 1, 1, "1");
		user2.addTransaction(tx3);
		Transaction tx4 = new Transaction("4", 1, "1", 1, 1, "1");
		user2.addTransaction(tx4);
		data.addUser(user2);
		
		this.persister.save(data, this.bos);
		data = this.persister.load(new ByteArrayInputStream(this.bos.toByteArray()));
		assertThat(data.getUsers().size(), is(2));
		
		User newUser1 = data.getUsers().get("1");
		assertUsers(user1, newUser1);
		Contact newC1 = newUser1.getContacts().get("1");
		assertContacts(c1, newC1);
		Contact newC2 = newUser1.getContacts().get("2");
		assertContacts(c2, newC2);
		Transaction newTx1 = newUser1.getTransactions().get("1");
		assertTransactions(tx1, newTx1);
		Transaction newTx2 = newUser1.getTransactions().get("2");
		assertTransactions(tx2, newTx2);
		
		User newUser2 = data.getUsers().get("2");
		assertUsers(user1, newUser1);
		newC1 = newUser2.getContacts().get("1");
		assertContacts(c1, newC1);
		newC2 = newUser2.getContacts().get("2");
		assertContacts(c2, newC2);
		Contact newC3 = newUser2.getContacts().get("3");
		assertContacts(c3, newC3);
		Transaction newTx3 = newUser2.getTransactions().get("3");
		assertTransactions(tx3, newTx3);
		Transaction newTx4 = newUser2.getTransactions().get("4");
		assertTransactions(tx4, newTx4);
	}
	
	private void assertUsers(User old, User neww) {
		assertItems(old, neww);
		assertThat(neww.getLogin(), is(old.getLogin()));
		assertThat(neww.getContacts().size(), is(old.getContacts().size()));
		assertThat(neww.getTransactions().size(), is(old.getTransactions().size()));
	}
	
	private void assertContacts(Contact old, Contact neww) {
		assertItems(old, neww);
		assertThat(neww.getPseudo(), is(old.getPseudo()));
		assertThat(neww.getIcon().isPresent(), is(old.getIcon().isPresent()));
		if (neww.getIcon().isPresent()) {
			assertThat(neww.getIcon().get(), is(old.getIcon().get()));
		}
	}
	
	private void assertTransactions(Transaction old, Transaction neww) {
		assertItems(old, neww);
		assertThat(neww.getContact(), is(old.getContact()));
		assertThat(neww.getAmount(), is(old.getAmount()));
		assertThat(neww.getDate(), is(old.getDate()));
		assertThat(neww.getComment(), is(old.getComment()));
	}
	
	private void assertItems(AbstractItem old, AbstractItem neww) {
		assertThat(neww.getId(), is(old.getId()));
		assertThat(neww.getRevision(), is(old.getRevision()));
	}
}

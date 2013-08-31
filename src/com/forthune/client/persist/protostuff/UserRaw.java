package com.forthune.client.persist.protostuff;

import com.dyuproject.protostuff.Tag;
import com.forthune.client.data.Contact;
import com.forthune.client.data.Transaction;
import com.forthune.client.data.User;
import com.google.common.collect.ImmutableMap;

/**
 * @author Guillaume Durand
 *
 */
final class UserRaw extends ItemRaw {
	@Tag(20)
	String login;

	@Tag(21)
	ContactRaw[] contacts;

	@Tag(22)
	TransactionRaw[] transactions;

	public UserRaw(User user) {
		super(user);
		this.login = user.getLogin();
		this.contacts = new ContactRaw[user.getContacts().size()];
		int i = 0;
		for (Contact contact : user.getContacts().values()) {
			this.contacts[i] = new ContactRaw(contact);
			i++;
		}
		
		this.transactions = new TransactionRaw[user.getTransactions().size()];
		i = 0;
		for (Transaction tx : user.getTransactions().values()) {
			this.transactions[i] = new TransactionRaw(tx);
			i++;
		}
	}
	
	public User getFinal() {
		User user = new User(this.id, this.revision, this.login);
		
		ImmutableMap.Builder<String, Contact> contactBuilder = ImmutableMap.<String, Contact>builder();
		for (int i = 0; i < this.contacts.length; i++) {
			Contact contact = this.contacts[i].getFinal();
			contactBuilder.put(contact.getId(), contact);
		}
		user.setContacts(contactBuilder.build());
		
		ImmutableMap.Builder<String, Transaction> txBuilder = ImmutableMap.<String, Transaction>builder();
		for (int i = 0; i < this.transactions.length; i++) {
			Transaction tx = this.transactions[i].getFinal();
			txBuilder.put(tx.getId(), tx);
		}
		user.setTransactions(txBuilder.build());
		
		return user;
	}
}
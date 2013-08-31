package com.forthune.client.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableMap;

public final class User extends AbstractItem {
	private String login;
	private ImmutableMap<String, Contact> contacts;
	private ImmutableMap<String, Transaction> transactions;
	
	public User(String id, long revision, String login) {
		super(id, revision);
		construct(login);
	}
	
	public User(String login) {
		super();
		construct(login);
	}
	
	public void construct(String login) {
		this.login = login;
		
		this.contacts = ImmutableMap.<String, Contact>builder().build();
		this.transactions = ImmutableMap.<String, Transaction>builder().build();
	}

	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
		setDirty();
	}
	
	public ImmutableMap<String, Contact> getContacts() {
		return this.contacts;
	}
	
	public void setContacts(ImmutableMap<String, Contact> contacts) {
		this.contacts = contacts;
		setDirty();
	}
	
	public void addContact(Contact contact) {
		this.contacts = Utils.addItem(this.contacts, contact);
		setDirty();
	}
	
	public void removeContact(Contact contact) {
		this.contacts = Utils.removeItem(this.contacts, contact);
		setDirty();
	}
	
	public ImmutableMap<String, Transaction> getTransactions() {
		return this.transactions;
	}
	
	public void setTransactions(ImmutableMap<String, Transaction> transactions) {
		this.transactions = transactions;
		setDirty();
	}
	
	public void addTransaction(Transaction tx) {
		this.transactions = Utils.addItem(this.transactions, tx);
		setDirty();
	}
	
	public void removeTransaction(Transaction tx) {
		this.transactions = Utils.removeItem(this.transactions, tx);
		setDirty();
	}
	
	public void accept(DataVisitor visitor) {
    if (visitor.visit(this)) {
	    if (visitor.withContacts()) {
	    	for (Contact contact : this.contacts.values()) {
	    		contact.accept(visitor);
				}
			}
	    
	    if (visitor.withTransactions()) {
	    	for (Transaction contact : this.transactions.values()) {
	    		contact.accept(visitor);
				}
	    }
    }
	}
	
	public Iterator<Persistable> iterator() {
		List<Iterator<? extends Iterable<Persistable>>> iters = new ArrayList<Iterator<? extends Iterable<Persistable>>>();
		iters.add(this.contacts.values().iterator());
		iters.add(this.transactions.values().iterator());
		return new CompoundIterator<Persistable, AbstractPersistable>(this, iters.iterator());
	}
}

package com.forthune.client.data;

import java.util.Iterator;

public final class Transaction extends AbstractItem {
	private String contact;
	private double amount;
	private long date;
	private String comment;
	
	public Transaction(String id, long revision, String contact, double amount, long date, String comment) {
		super(id, revision);
		construct(contact, amount, date, comment);
	}
	
	public Transaction(String contact, double amount, long date, String comment) {
		super();
		construct(contact, amount, date, comment);
	}
	
	public void construct(String contact, double amount, long date, String comment) {
		this.contact = contact;
		this.amount = amount;
		this.date = date;
		this.comment = comment;
	}

	public String getContact() {
		return this.contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
		setDirty();
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
		setDirty();
	}
	
	public long getDate() {
		return this.date;
	}
	
	public void setDate(long date) {
		this.date = date;
		setDirty();
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
		setDirty();
	}
	
	public void accept(DataVisitor visitor) {
    visitor.visit(this);
	}
	
	public Iterator<Persistable> iterator() {
		return new CompoundIterator<Persistable, AbstractPersistable>(this, null);
	}
}

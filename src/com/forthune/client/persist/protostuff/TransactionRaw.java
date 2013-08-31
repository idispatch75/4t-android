package com.forthune.client.persist.protostuff;

import com.dyuproject.protostuff.Tag;
import com.forthune.client.data.Transaction;

/**
 * @author Guillaume Durand
 *
 */
final class TransactionRaw extends ItemRaw {
	@Tag(20)
	String contact;

	@Tag(21)
	double amount;
	
	@Tag(22)
	long date;

	@Tag(23)
	String comment;

	public TransactionRaw(Transaction tx) {
		super(tx);
		this.contact = tx.getContact();
		this.amount = tx.getAmount();
		this.date = tx.getDate();
		this.comment = tx.getComment();
	}
	
	public Transaction getFinal() {
		return new Transaction(this.id, this.revision, this.contact, this.amount, this.date, this.comment);
	}
}
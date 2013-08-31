package com.forthune.client.data;

public interface DataVisitor {
	boolean visit(Data data);
	boolean visit(User user);
	void visit(Contact contact);
	void visit(Transaction tx);
	boolean withTransactions();
	boolean withContacts();
}

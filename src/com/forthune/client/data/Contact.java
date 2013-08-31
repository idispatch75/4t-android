package com.forthune.client.data;

import java.util.Iterator;

import com.google.common.base.Optional;

public final class Contact extends AbstractItem {
	private String pseudo;
	private Optional<byte[]> icon;
	
	public Contact(String id, long revision, String pseudo) {
		super(id, revision);
		construct(pseudo);
	}
	
	public Contact(String pseudo) {
		super();
		construct(pseudo);
	}
	
	private void construct(String pseudo) {
		this.pseudo = pseudo;
		this.icon = Optional.absent();
	}

	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
		setDirty();
	}
	
	public Optional<byte[]> getIcon() {
		return this.icon;
	}
	
	public void setIcon(byte[] icon) {
		this.icon = Optional.fromNullable(icon);
		setDirty();
	}
	
	public void accept(DataVisitor visitor) {
    visitor.visit(this);
	}
	
	public Iterator<Persistable> iterator() {
		return new CompoundIterator<Persistable, AbstractPersistable>(this, null);
	}
}

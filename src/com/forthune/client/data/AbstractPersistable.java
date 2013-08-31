package com.forthune.client.data;

abstract class AbstractPersistable implements Persistable, Iterable<Persistable> {
	private boolean dirty;
	
	public AbstractPersistable() {
		this.dirty = false;
	}
	
	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty() {
		this.dirty = true;
	}
	
	public void clearDirty() {
		this.dirty = false;
	}
}

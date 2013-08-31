package com.forthune.client.data;

import java.util.Date;

public abstract class AbstractItem extends AbstractPersistable implements Syncable {
	private String id;
	private long revision;
	
	public AbstractItem() {
		this.id = "new" + new Date().getTime() + Math.random();
		this.revision = 0;
		setDirty();
	}
	
	public AbstractItem(String id, long revision) {
		this.id = id;
		this.revision = revision;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
		setDirty();
	}
	
	public long getRevision() {
		return this.revision;
	}
	
	public void setRevision(long revision) {
		this.revision = revision;
		setDirty();
	}
	
	public boolean isNew() {
		return this.revision == 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (getClass().isInstance(o)) {
			return this.id.equals(((AbstractItem)o).id);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
}

package com.forthune.client.data;

public interface Syncable {
	boolean isNew();
	long getRevision();
	void setRevision(long rev);
}

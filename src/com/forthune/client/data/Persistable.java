package com.forthune.client.data;

public interface Persistable {
	boolean isDirty();
	void setDirty();
	void clearDirty();
}

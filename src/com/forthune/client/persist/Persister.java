package com.forthune.client.persist;

import java.io.IOException;

import com.forthune.client.data.Data;

public interface Persister {
	void save(Data data) throws IOException;
	
	Data load() throws IOException;
}

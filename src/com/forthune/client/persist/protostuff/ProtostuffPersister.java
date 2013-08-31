package com.forthune.client.persist.protostuff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.forthune.client.data.Data;

/**
 * Saves and loads {@link Data} in Protostuff format.
 * 
 * @author Guillaume Durand
 *
 */
public class ProtostuffPersister {
	private LinkedBuffer buffer;
	private Schema<DataRaw> schema;

	/**
	 * Initializes Protostuff.
	 */
	public ProtostuffPersister() {
		// create a buffer for serialization
		this.buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		
		// initialize serialization schema
		this.schema = RuntimeSchema.getSchema(DataRaw.class);
	}

	/**
	 * Saves the data to the specified stream.
	 * The stream is not closed at the end.
	 * 
	 * @param data
	 * @param os
	 * @throws IOException
	 */
	public void save(Data data, OutputStream os) throws IOException {
		synchronized(this.buffer) {
			try {
				// populate the raw data
				DataRaw dataRaw = new DataRaw(data);
	
				// write the raw data to the file
				ProtostuffIOUtil.writeTo(os, dataRaw, this.schema, this.buffer);
				
			} finally {
				this.buffer.clear();
			}
		}
	}

	/**
	 * Loads data from the specified stream.
	 * The stream is not closed at the end.
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public Data load(InputStream is) throws IOException {
		synchronized(this.buffer) {
			try {
				// read the raw data
				DataRaw dataRaw = new DataRaw();
				ProtostuffIOUtil.mergeFrom(is, dataRaw, this.schema, this.buffer);
				
				// populate the final Data
				return dataRaw.getFinal();
				
			} finally {
				this.buffer.clear();
			}
		}
	}
}

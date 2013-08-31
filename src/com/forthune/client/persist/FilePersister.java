package com.forthune.client.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.forthune.client.data.Data;
import com.forthune.client.data.Persistable;
import com.forthune.client.persist.protostuff.ProtostuffPersister;

public final class FilePersister implements Persister {
	private File file;
	private ProtostuffPersister proto;

	public FilePersister(File file) {
		this.file = file;
		
		this.proto = new ProtostuffPersister();
	}

	public void save(Data data) throws IOException {
		synchronized(this.file) {
			// save only if data is dirty
			boolean dirty = false;
			for (Persistable persistable : data) {
				if (persistable.isDirty()) {
					dirty = true;
					break;
				}
			}
			
			if(dirty) {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(this.file);
					this.proto.save(data, fos);
					
					// clear dirty flag
					for (Persistable persistable : data) {
						persistable.clearDirty();
					}

				} finally {
					if (fos != null) {
						fos.close();
					}
				}
			}
		}
	}

	public Data load() throws IOException {
		if (!this.file.exists()) {
			return new Data();
		}
		
		synchronized(this.file) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(this.file);
				Data data = this.proto.load(fis);
				
				// clear dirty flag
				for (Persistable persistable : data) {
					persistable.clearDirty();
				}
				return data;
				
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		}
	}
}

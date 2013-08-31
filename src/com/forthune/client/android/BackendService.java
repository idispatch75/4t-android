package com.forthune.client.android;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import objectforms.utils.elog;

import com.forthune.client.data.Contact;
import com.forthune.client.data.Data;
import com.forthune.client.data.Transaction;
import com.forthune.client.data.User;
import com.forthune.client.persist.FilePersister;
import com.google.common.base.Optional;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BackendService extends Service implements Backend {
	private Binder binder;
	private Data data;
	private FilePersister persister;

	@Override
	public void onCreate() {
		// read data from local storage
		File file = getFileStreamPath("data");
		this.persister = new FilePersister(file);
		try {
			this.data = this.persister.load();
			
			// TODO remove
			User user = new User("user");
			this.data.addUser(user);
			user.addContact(new Contact("1", 1, "Guillaume Durand"));
			user.addContact(new Contact("2", 1, "Pac"));
			user.addTransaction(new Transaction("1", 10, new Date().getTime(), "Anniversaire Guillaume Durand"));
			user.addTransaction(new Transaction("1", -12.5, new Date().getTime(), "Bateau laffrey"));
			user.addTransaction(new Transaction("2", 10, new Date().getTime(), "Anniversaire Pac"));
			
		} catch (IOException e) {
			elog.error("Failed to load data", e);
			
			// TODO what to do? Creating an empty data will overwrite real data if it exists,
			// but it may not be a problem since in most cases the data would be on the cloud.
			// Ideally we would ask the user.
			this.data = new Data();
		}
		
		// start synchronization
		// TODO

		// create binder
		this.binder = new BackendBinder();
	}

	public class BackendBinder extends Binder {
		public Backend getService() {
			return BackendService.this;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// stop synchronization
		// TODO
		
		// persist the data
		/*if (this.persister != null && this.data != null) {
			try {
				this.persister.save(this.data);
			} catch (IOException e) {
				// ignore
			}
		}*/
	}

	@Override
	public IBinder onBind(Intent intent) {
		return this.binder;
	}

	public Data getData() {
		return this.data;
	}
	
	public Optional<User> getCurrentUser() {
		// TODO support multiple users
		if (this.data.getUsers().size() > 0) {
			return Optional.of(this.data.getUsers().values().iterator().next());
		} else {
			return Optional.absent();
		}
	}
}

package com.forthune.client.android;

import objectforms.utils.elog;

public class Application extends android.app.Application {
	public Application() {
		super();
		
		elog.setPrinter(new AndroidLog());
	}
}

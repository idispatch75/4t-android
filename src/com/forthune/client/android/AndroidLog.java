package com.forthune.client.android;

import android.util.Log;
import objectforms.utils.elog;
import objectforms.utils.LogPrinter;

/**
 * Connector of platform neutral elog log tool to native Android log system
 */
public final class AndroidLog implements LogPrinter {
	@Override
	public void log(LogLevel level, String text, Throwable ex) {
		String tag = elog.methodName();
		
		switch (level) {
		case ERROR: Log.e(tag, text, ex); break;
		case WARNING: Log.w(tag, text, ex); break;
		case INFO: Log.i(tag, text, ex); break;
		case DEBUG: Log.d(tag, text, ex); break;
		case VERBOSE: Log.v(tag, text, ex); break;
		}
	}

	@Override
	public void log(LogLevel level, String text) {
		String tag = elog.methodName();
		
		switch (level) {
		case ERROR: Log.e(tag, text); break;
		case WARNING: Log.w(tag, text); break;
		case INFO: Log.i(tag, text); break;
		case DEBUG: Log.d(tag, text); break;
		case VERBOSE: Log.v(tag, text); break;
		}
	}
}
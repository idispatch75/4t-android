package objectforms.utils;

public interface LogPrinter {
	public enum LogLevel {
		ERROR,
		WARNING,
		INFO,
		DEBUG,
		VERBOSE
	}
	
	public void log(LogLevel level, String text, Throwable ex);
	
	public void log(LogLevel level, String text);
}

package com.sms.common;

public class Debug {
    private static final int DEBUG=0;
    private static final int LOG=1;
    private static final int WARNING=2;
    private static final int SUCCESS=3;
    private static final int ERROR=4;
	private static void writeLog(String raw, int verboseLevel) {
		switch(verboseLevel) {
			case DEBUG:
					raw = "[DEBUG] "+raw;
				break;
			case LOG:
					raw = "[LOG] "+raw;
				break;
			case WARNING:
					raw = "[WARNING] "+raw;
				break;
			case SUCCESS:
					raw = "[SUCCESS] "+raw;
				break;
			case ERROR:
					raw = "[ERROR] "+raw;
				break;
		}
		System.out.println(raw);
	}
	public static void log(Object obj) {
		writeLog(obj.toString(),LOG);
	}
	public static void debug(Object obj) {
		writeLog(obj.toString(),DEBUG);
	}
	public static void warning(Object obj) {
		writeLog(obj.toString(),WARNING);
	}
	public static void success(Object obj) {
		writeLog(obj.toString(),SUCCESS);
	}
	public static void error(Object obj) {
		writeLog(obj.toString(),ERROR);
	}
}

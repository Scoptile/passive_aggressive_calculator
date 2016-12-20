package com.scoptile.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Console {
	public static final int TYPE_INFO = 0;
	public static final int TYPE_WARN = 1;
	public static final int TYPE_ERROR = 2;
	public static final int TYPE_ASSET = 3;
	
	public static void print (Object s, String caller, int type) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		
		String date = df.format(d);
		String color = (type == TYPE_INFO ? ANSI.ANSI_RESET : type == TYPE_WARN ? ANSI.ANSI_YELLOW : type == TYPE_ASSET ? ANSI.ANSI_CYAN : ANSI.ANSI_RED);
		String typeName = (type == TYPE_INFO ? "INFO" : type == TYPE_WARN ? "WARN" : type == TYPE_ASSET ? "ASSET" : "ERROR");
		
		System.out.println(color + "[" + date + "] " + (caller != null ? "[" + caller + "] " : "") + "[" + typeName + "] " + s + ANSI.ANSI_RESET);
	}
	
	public static void print (Object s, int type) {
		print(s, null, type);
	}
	
	public static void print (Object s, String caller) {
		print(s, caller, TYPE_INFO);
	}
	
	public static void print (Object s) {
		print(s, null);
	}
	
	public static void error (String s, Exception exception, boolean crash) {
		StackTraceElement[] stackTrace = exception.getStackTrace();
		Random rand = new Random();
		
		String[] message = new String[] {"Oops ;-;", "Why did this happen ;-;", "RIP game ;-;", "Gone but not forgotten ;-;"};
		int messageIndex = rand.nextInt(message.length);
		
		print(s + " :: " + exception, TYPE_ERROR);
		
		System.out.print(ANSI.ANSI_RED);
		if (crash) System.out.println("\t" + message[messageIndex]);
		System.out.println();
		
		for (int i = 0; i < stackTrace.length; i ++) {
			System.out.println("\tat " + stackTrace[i]);
		}
		
		if (crash) System.exit(0);
	}
}

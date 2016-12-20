package com.scoptile.util;

public class ObjectUtils {
	public static boolean instanceOf (Object o1, Object o2) {
		@SuppressWarnings("rawtypes")
		Class c = o2.getClass();
		
		while (c != o1.getClass()) {
			c = c.getSuperclass();
			if (c == null) return false;
		}
		
		return true;
	}
}

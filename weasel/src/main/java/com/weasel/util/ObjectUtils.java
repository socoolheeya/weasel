package com.weasel.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {
	
	public static String[] toStringArray(Object src) {
		String[] dst = new String[0];
		if (src == null) return dst;
		try {
			if (src instanceof String) {
				dst = new String[1];
				dst[0] = ObjectUtils.toString(src);
			} else {
				dst = (String[])src;
			}
		} catch (NullPointerException e) {
			dst = new String[0];
			// Exception ignore
		}
		return dst;
	}
	
	public static int toInt(Object src, int def) {
		if (src == null) return def;
		return NumberUtils.toInt(src.toString(), def); 
	}
	
	public static int toInt(Object src) {
		return ObjectUtils.toInt(src, 0);
	}
	
	public static int[] toIntArray(Object src) {
		int[] dst = new int[0];
		if (src == null) return dst;
		try {
			dst = (int[])src;
		} catch (ClassCastException e) {
			dst = new int[0];
			// Exception ignore
		}
		return dst;
	}
	
	public static long toLong(Object src, int def) {
		if (src == null) return def;
		return NumberUtils.toLong(src.toString(), def); 
	}
	
	public static long toLong(Object src) {
		return ObjectUtils.toLong(src, 0);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(Object src) {
		List<T> dstList = new ArrayList<T>();
		if (src == null) return dstList;
		
		try {
			dstList = (List<T>)src;
		} catch (ClassCastException e) {
			dstList = new ArrayList<T>();
			// Exception ignore
		}
		return dstList;
	}
	
	public static boolean toBool(Object src) {
		return BooleanUtils.toBoolean(ObjectUtils.toString(src));
	}
	
	public static double toDouble(Object src, double def) {
		return NumberUtils.toDouble(ObjectUtils.toString(src), def);
	}
	
	public static double toDouble(Object src) {
		return NumberUtils.toDouble(ObjectUtils.toString(src), 0D);
	}
	
	public static double toDouble(Object src, int point) {
		String sSrc = StringUtils.EMPTY;
		String format = "%." + point + "f";
		if (src instanceof Float) {
			sSrc = String.format(format, (Float)src);
		} else if (src instanceof Double) {
			sSrc = String.format(format, (Double)src);
		} else if (src instanceof BigDecimal) {
			sSrc = String.format(format, (BigDecimal)src);
		}
		return ObjectUtils.toDouble(sSrc, 0D);
	}
	
	public static String escapeStr(Object src) {
		String str = ObjectUtils.toString(src);
		return str
			.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("\"", "&quot;")
			.replace("'", "&#39;");
	}
	
	public static String unescapeStr(Object src) {
		String str = ObjectUtils.toString(src);
		return str
		.replace("&amp;", "&")
		.replace("&lt;", "<")
		.replace("&gt;", ">")
		.replace("&quot;", "\"")
		.replace("&apos;", "'")
		.replace("&#39;", "'");
	}
	
	public static boolean isEmpty(Object src) {
		boolean flag = false;
		if(src == null || src == "") {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
}

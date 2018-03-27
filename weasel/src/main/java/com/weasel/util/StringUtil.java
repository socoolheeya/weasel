package com.weasel.util;

import java.text.DecimalFormat;

public class StringUtil {
	
	public static String getDigitNumber(int number, int digit) throws Exception {
		String sDigit = null;
		for(int i = 0; i < digit; i++) {
			sDigit += "0";
		}
		DecimalFormat df = new DecimalFormat(sDigit);
		String str = df.format(number);
		
		return str;
	}
	
	public static String replaceAll(String str, String str1, String str2) throws Exception {
		String newStr = str.replaceAll(str2, str2);
		return newStr;
	}
	
	
}

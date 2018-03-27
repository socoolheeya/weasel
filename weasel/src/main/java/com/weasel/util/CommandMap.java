package com.weasel.util;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

public class CommandMap {

	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

	private HttpServletRequest request;
	private HttpSession session;

	public CommandMap(){}
	
	public CommandMap(HttpServletRequest req) {

		this.request = req;
		this.session = req.getSession();

		Enumeration<?> enumeration = req.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String[] values = req.getParameterValues(key);

			if (key.indexOf("[]") > -1) {
				key = key.substring(0, key.length() - 2);
				this.map.put(key, values);
			} else if (values != null) {
				this.map.put(key, (values.length > 1) ? values : values[0]);
			}
		}
	}

	public Object get(String key) {
		return map.get(key) == null ? "" : map.get(key);
	}

	public void put(String key, Object value) {
		map.put(key, value == null ? "" : value);
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public void clear() {
		map.clear();
	}

	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		if (m != null) {
			map.putAll(m);
		}
	}

	public UMap getMap() {
		return new UMap(this.map);
	}

	public String getStr(String key) {
		return ObjectUtils.toString(this.map.get(key));
	}

	public String getEscapeStr(String key) {
		return ObjectUtils.escapeStr(this.map.get(key));
	}

	public String getUnescapeStr(String key) {
		return ObjectUtils.unescapeStr(this.map.get(key));
	}

	public String getStr(String key, String defaultValue) {
		return ObjectUtils.toString(this.map.get(key), defaultValue);
	}

	public String[] getStrArray(String key) {
		Object src = this.map.get(key);
		String[] dst = new String[0];

		if (src == null)
			return dst;

		try {
			if (src instanceof String) {
				dst = new String[1];
				dst[0] = ObjectUtils.toString(src);
			} else {
				dst = (String[]) src;
			}
		} catch (NullPointerException e) {
			dst = new String[0];
		}
		return dst;
	}

	public int getInt(String key, int defaultValue) {
		String sVal = this.getStr(key);
		int iVal = NumberUtils.toInt(sVal, defaultValue);

		if (StringUtils.equals(String.valueOf(iVal), sVal) || iVal == defaultValue) {
			return iVal;
		} else {
			return defaultValue;
		}
	}

	public int getInt(String key) {
		return this.getInt(key, 0);
	}

	public double getDouble(String key, double defaultValue) {
		return NumberUtils.toDouble(this.getStr(key), defaultValue);
	}

	public double getDouble(String key) {
		return this.getDouble(key, 0D);
	}

	public long getLong(String key, long defaultValue) {
		String sVal = this.getStr(key);
		long lVal = NumberUtils.toLong(this.getStr(key), defaultValue);

		if (StringUtils.equals(String.valueOf(lVal), sVal) || lVal == defaultValue) {
			return lVal;
		} else {
			return defaultValue;
		}
	}
	public long getLong(String key) {
		return this.getLong(key, 0L);
	}

	public int[] getIntArray(String key) {
		Object src = this.map.get(key);
		int[] dst = new int[0];

		if (src == null)
			return dst;

		try {
			if (src instanceof Integer) {
				dst = new int[1];
				dst[0] = (Integer) src;
			} else if (ArrayUtils.isSameType(src, new int[] {})) {
				dst = (int[]) src;
			} else if (ArrayUtils.isSameType(src, new Integer[] {})) {
				dst = ArrayUtils.toPrimitive((Integer[]) src);
			} else if (src instanceof String || src instanceof String[]) {
				String[] sSrc = this.getStrArray(key);
				dst = new int[sSrc.length];

				for (int i = 0; i < sSrc.length; i++) {
					dst[i] = NumberUtils.toInt(sSrc[i]);
				}
			}
		} catch (IllegalArgumentException e) {
			dst = new int[0];
		}
		return dst;
	}

	public boolean getBool(String key) {
		return BooleanUtils.toBoolean(this.getStr(key));
	}

	public Date getDate(String key) {
		Object src = this.map.get(key);
		if (src instanceof Date) {
			return (Date) src;
		} else {
			return null;
		}
	}

	public String getDateStr(String key, String dateFormat) {
		Object src = this.map.get(key);
		if (src instanceof Date) {
			DateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(src);
		} else {
			return StringUtils.EMPTY;
		}
	}

	public boolean isEmpty(String... keys) {
		for (String key : keys) {
			key = key.trim();

			if (this.map.get(key) == null) {
				return true;
			} else if (StringUtils.isEmpty(this.getStr(key))) {
				return true;
			}
		}

		return false;
	}

	public void ifEmptyThrowException(String... keys) {
		if (isEmpty(keys)) {
			throw new InvalidParameterException("[" + StringUtils.join(keys, ", ") + "] 占식띰옙占쏙옙叩占� 占쏙옙占실듸옙占쏙옙 占십았쏙옙占싹댐옙. " + this.toString());
		}
	}

	public boolean isAllEmpty(String... keys) {
		for (String key : keys) {
			key = key.trim();

			if (this.map.get(key) != null) {
				return false;
			} else if (this.map.get(key) instanceof Object[]) { 
				if (!ArrayUtils.isEmpty((Object[]) this.map.get(key))) {
					return false;
				}
			} else if (!StringUtils.isEmpty(this.getStr(key))) {
				return false;
			}
		}

		return true;
	}

	public void ifAllEmptyThrowException(String... keys) {
		if (isAllEmpty(keys)) {
			throw new InvalidParameterException("[" + StringUtils.join(keys, ", ") + "] 占식띰옙占쏙옙叩占� 占쏙옙占� 占쏙옙占실듸옙占쏙옙 占십았쏙옙占싹댐옙. " + this.toString());
		}
	}

	public boolean isBlank(String... keys) {
		for (String key : keys) {
			key = key.trim();

			if (this.map.get(key) == null) {
				return true;
			} else if (StringUtils.isBlank(this.getStr(key))) {
				return true;
			}
		}

		return false;
	}

	public List<UMap> getListMap(String... keys) {
		List<UMap> list = new ArrayList<UMap>();

		Map<String, String[]> values = new HashMap<String, String[]>();
		for (String key : keys) {
			values.put(key, this.getStrArray(key));
		}

		if (values.size() == 0) {
			return list;
		}

		int rowCnt = values.get(keys[0]).length;
		for (int ri = 0; ri < rowCnt; ri++) {
			UMap map = new UMap();

			for (String key : keys) {
				if (values.get(key) == null || values.get(key).length == 0) {
					continue;
				}

				map.put(key, ObjectUtils.toString(values.get(key)[ri]));
			}

			list.add(map);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> T getType(String key, T defaultVal) {
		Object val = this.map.get(key);
		if (val == null) {
			return defaultVal;
		} else {
			return (T) val;
		}
	}

	public <T> T getType(String key) {
		return this.getType(key, null);
	}

	public String format(String format) {
		Pattern p = Pattern.compile("\\$\\{[\\p{Alnum}\\.\\[\\]\\_]*\\}");
		Matcher m = p.matcher(format);
		String pttn = StringUtils.EMPTY;
		String key = StringUtils.EMPTY;

		while (m.find()) {
			pttn = m.group();
			key = pttn.substring(2, pttn.length() - 1);

			format = StringUtils.replace(format, pttn, this.getStr(key));
		}

		return format;
	}

	public boolean isAjax() {
		return StringUtils.equals(this.request.getHeader("X-Requested-With"), "XMLHttpRequest");
	}

	public String getCookie(String key, String defaultValue) {
		Cookie[] cks = this.request.getCookies();
		for (Cookie c : cks) {
			if (StringUtils.equals(c.getName(), key)) {
				return ObjectUtils.toString(c.getValue());
			}
		}

		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	public <T> T getSessionValue(String key, T defaultVal) {
		Object val = this.session.getAttribute(key);
		if (val == null) {
			return defaultVal;
		} else {
			return (T) val;
		}
	}

	public <T> T getSessionValue(String key) {
		return this.getSessionValue(key, null);
	}

	public void setSessionValue(String key, Object val) {
		this.session.setAttribute(key, val);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpSession getSession() {
		return session;
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean isPretty) {
		Set<Entry<String, Object>> entry = this.entrySet();

		ToStringStyle tss = isPretty ? ToStringStyle.MULTI_LINE_STYLE : ToStringStyle.SHORT_PREFIX_STYLE;
		ToStringBuilder tsb = new ToStringBuilder(this, tss);
		for (Entry<String, Object> e : entry) {
			tsb.append(e.getKey(), e.getValue());
			if (e.getValue() == null) {
				e.setValue("");
			}
		}

		return tsb.toString();
	}
}
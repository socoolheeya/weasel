package com.weasel.util;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

public class UMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -6507112219062391208L;

	public static final int UPPER_KEY = 1;
	public static final int LOWER_KEY = 2;
	public static final int NATIVE_KEY = 3;

	public UMap() {
		super();
	}

	public UMap(Map<String, Object> map) {
		super();
		if (map != null) {
			this.putAll(map);
		}
	}

	@SuppressWarnings("unchecked")
	public UMap(Object bean) throws Exception {
		super();
		if (bean instanceof Map) {
			this.putAll((Map<String, Object>) bean);
		} else if (bean != null) {
			this.putAll(BeanUtils.describe(bean));
		}
	}

	public String getStr(String key) {
		return ObjectUtils.toString(this.get(key));
	}

	public String getEscapeStr(String key) {
		return ObjectUtils.escapeStr(this.get(key));
	}

	public String getUnescapeStr(String key) {
		return ObjectUtils.unescapeStr(this.get(key));
	}

	public String getStr(String key, String defaultValue) {
		return ObjectUtils.toString(this.get(key), defaultValue);
	}

	public String[] getStrArray(String key) {
		Object src = this.get(key);
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
		} catch (Exception e) {
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

		// 占쎌젟占쎈땾 overflow占쎈뻻 疫꿸퀡�궚揶쏉옙 獄쏆꼹�넎
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
		Object src = this.get(key);
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
		Object src = this.get(key);
		if (src instanceof Date) {
			return (Date) src;
		} else {
			return null;
		}
	}

	public String getDateStr(String key, String dateFormat) {
		Object src = this.get(key);
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

			if (this.get(key) == null) {
				return true;
			} else if (StringUtils.isEmpty(this.getStr(key))) {
				return true;
			}
		}

		return false;
	}

	public void ifEmptyThrowException(String... keys) {
		if (isEmpty(keys)) {
			throw new InvalidParameterException("[" + StringUtils.join(keys, ", ") + "] 占쎈솁占쎌뵬沃섎챸苑ｅ첎占� 占쎌젟占쎌벥占쎈┷筌욑옙 占쎈륫占쎈릭占쎈뮸占쎈빍占쎈뼄. " + this.toString());
		}
	}

	public boolean isAllEmpty(String... keys) {
		for (String key : keys) {
			key = key.trim();

			if (this.get(key) != null) {
				return false;
			} else if (this.get(key) instanceof Object[]) {
				if (!ArrayUtils.isEmpty((Object[]) this.get(key))) {
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
			throw new InvalidParameterException("[" + StringUtils.join(keys, ", ") + "] 占쎈솁占쎌뵬沃섎챸苑ｅ첎占� 筌뤴뫀紐� 占쎌젟占쎌벥占쎈┷筌욑옙 占쎈륫占쎈릭占쎈뮸占쎈빍占쎈뼄. " + this.toString());
		}
	}

	public boolean isBlank(String... keys) {
		for (String key : keys) {
			key = key.trim();

			if (this.get(key) == null) {
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
		Object val = this.get(key);
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

	@Override
	public String toString() {
		return this.toString(false);
	}

	public UMap getPrefixMap(String prefix) {
		UMap u = new UMap();
		List<String> keyList = this.toKeyList(prefix);
		for (int i = 0; i < keyList.size(); i++) {
			String newKey = keyList.get(i);
			String oldKey = prefix.concat(newKey);
			u.put(newKey, this.getStr(oldKey));
		}
		return u;
	}

	public List<UMap> getListPrefixMap(String prefix, String... keys) {
		List<UMap> list = new ArrayList<UMap>();

		Map<String, String[]> values = new HashMap<String, String[]>();
		for (String newKey : keys) {
			String oldKey = prefix.concat(newKey);
			values.put(newKey, this.getStrArray(oldKey));
		}

		int rowCnt = values.get(keys[0]).length;
		for (int ri = 0; ri < rowCnt; ri++) {
			UMap map = new UMap();

			for (String key : keys) {
				if (values.get(key) == null || values.get(key).length <= ri) {
					continue;
				}

				map.put(key, ObjectUtils.toString(values.get(key)[ri]));
			}

			list.add(map);
		}
		return list;
	}

	public List<UMap> getListPrefixMap(String prefix, String mainKey) {
		List<String> keyList = toKeyList(prefix);
		int idx = keyList.indexOf(mainKey);
		keyList.add(0, keyList.get(idx));
		keyList.remove(idx + 1);
		String[] keys = new String[keyList.size()];
		return this.getListPrefixMap(prefix, keyList.toArray(keys));
	}

	public List<? extends Object> toValueList() {
		return new ArrayList<Object>(this.values());
	}

	public List<String> toKeyList() {
		return new ArrayList<String>(this.keySet());
	}

	public List<String> toKeyList(String prefix) {
		List<String> list = this.toKeyList();
		for (int i = list.size() - 1; i >= 0; i--) {
			String key = list.get(i);
			if (key.startsWith(prefix)) {
				list.set(i, key.substring(prefix.length()));
			} else {
				list.remove(i);
			}
		}
		return list;
	}

	public UMap toUpperKeyCase() {
		return this.toConvertKeyCase(UMap.UPPER_KEY);
	}

	public UMap toLowerKeyCase() {
		return this.toConvertKeyCase(UMap.LOWER_KEY);
	}

	public UMap toConvertKeyCase(int keyCase) {
		UMap m = new UMap();
		List<String> keyList = this.toKeyList();
		for (int i = 0; i < keyList.size(); i++) {
			String oldKey = keyList.get(i);
			String newKey = oldKey;
			if (keyCase == UPPER_KEY) {
				newKey = oldKey.toUpperCase();
			} else if (keyCase == LOWER_KEY) {
				newKey = oldKey.toLowerCase();
			}
			m.put(newKey, this.getStr(oldKey));
		}
		return m;
	}

	public boolean isEqualValue(UMap targetMap, String... excludeKeys) {
		List<String> keylist = this.toKeyList();
		keylist.removeAll(Arrays.asList(excludeKeys));

		boolean flag = true;
		for (int i = 0; i < keylist.size(); i++) {
			String key = keylist.get(i);
			if (!StringUtils.deleteWhitespace(this.getStr(key)).equals(StringUtils.deleteWhitespace(targetMap.getStr(key)))) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	public boolean isEqualValueInclude(UMap targetMap, String... includeKeys) {
		// key �뵳�딅뮞占쎈뱜占쎄문占쎄쉐占쎈릭�⑨옙 占쎌젫占쎌뇚占쎈쭍 占쎄텕占쎈뮉 �뵳�딅뮞占쎈뱜占쎈퓠占쎄퐣 占쎌젫椰꾬옙
		List<String> keylist = Arrays.asList(includeKeys);

		// key 獄쏄퀣肉댐옙肉� 占쎈퉸占쎈뼣占쎈┷占쎈뮉 揶쏅�れ뱽 �뜮袁㏉꺍
		boolean flag = true;
		for (int i = 0; i < keylist.size(); i++) {
			String key = keylist.get(i);
			// replaceAll("\r\n","\n") �빊遺쏙옙 /* 2016.06.13 */
			if (!StringUtils.deleteWhitespace(this.getStr(key)).equals(StringUtils.deleteWhitespace(targetMap.getStr(key)))) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	public static UMap makeKeyMap(List<UMap> targetList, String... includeKeys) {
		UMap resultMap = new UMap();

		for (int i = 0; i < targetList.size(); i++) {
			UMap m = targetList.get(i);

			// keyString 占쎄문占쎄쉐
			List<String> values = new ArrayList<String>();
			for (int j = 0; j < includeKeys.length; j++) {
				values.add(m.getStr(includeKeys[j]));
			}
			String keyStr = StringUtils.join(values, "#");
			resultMap.put(keyStr, i);
		}

		return resultMap;
	}

	private static String getKeyString(UMap m, String... includeKeys) {
		List<String> values = new ArrayList<String>();
		for (int j = 0; j < includeKeys.length; j++) {
			values.add(m.getStr(includeKeys[j]));
		}
		String keyStr = StringUtils.join(values, "#");
		return keyStr;
	}

	public static void excludeSameKeyValue(List<UMap> target1List, List<UMap> target2List, String... compareKeys) {
		UMap t2KeyMap = UMap.makeKeyMap(target2List, compareKeys);

		List<Integer> t2RemoveKeyList = new ArrayList<Integer>();
		for (int i = target1List.size() - 1; i >= 0; i--) {
			UMap t1 = target1List.get(i);

			String keyStr = getKeyString(t1, compareKeys);

			if (t2KeyMap.containsKey(keyStr)) {
				target1List.remove(i);

				int t2idx = t2KeyMap.getInt(keyStr);
				t2RemoveKeyList.add(t2idx);

				t2KeyMap.remove(keyStr);
			}
		}
		Collections.sort(t2RemoveKeyList);

		for (int i = t2RemoveKeyList.size() - 1; i >= 0; i--) {
			target2List.remove(t2RemoveKeyList.get(i).intValue());
		}
	}

	public static void convertUpperCaseKey(List<UMap> targetList) {
		for (int i = 0; i < targetList.size(); i++) {
			UMap t = targetList.get(i).toUpperKeyCase();
			targetList.set(i, t);
		}
	}

	public static void convertLowerCaseKey(List<UMap> targetList) {
		for (int i = 0; i < targetList.size(); i++) {
			UMap t = targetList.get(i).toLowerKeyCase();
			targetList.set(i, t);
		}
	}

	public static void sortForInt(List<UMap> targetList, final String sortKey) {
		Collections.sort(targetList, new Comparator<UMap>() {
			@Override
			public int compare(UMap o1, UMap o2) {
				return o1.getInt(sortKey) - o2.getInt(sortKey);
			}

		});
	}

	public static void sortForString(List<UMap> targetList, final String sortKey) {
		sortForString(targetList, sortKey, true);
	}

	public static void sortForString(List<UMap> targetList, final String sortKey, final boolean isAsc) {
		Collections.sort(targetList, new Comparator<UMap>() {
			@Override
			public int compare(UMap o1, UMap o2) {
				if (isAsc) {
					return o1.getStr(sortKey).compareTo(o2.getStr(sortKey));
				} else {
					return o2.getStr(sortKey).compareTo(o1.getStr(sortKey));
				}
			}
		});
	}

	public boolean isNullValue(String... excludeKeys) {
		List<String> keylist = this.toKeyList();
		keylist.removeAll(Arrays.asList(excludeKeys));

		boolean flag = true;
		if (keylist != null) {
			for (int i = 0; i < keylist.size(); i++) {
				String key = keylist.get(i);
				if (!"".equals(this.getStr(key))) {
					flag = false;
				}
			}
		}
		return flag;
	}
}

//package com.sensing.core.test;
//
///*jadclipse*/
//
//// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
////Jad home page: http://www.kpdus.com/jad.html
////Decompiler options: packimports(3) radix(10) lradix(10) 
//
//import java.io.*;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
////Referenced classes of package java.lang:
////         Object, AbstractStringBuilder, CharSequence, Character, 
////         Comparable, ConditionalSpecialCasing, Double, Float, 
////         IllegalArgumentException, IndexOutOfBoundsException, Integer, Iterable, 
////         Long, Math, NullPointerException, StringBuffer, 
////         StringBuilder, StringCoding, StringIndexOutOfBoundsException, System, 
////         Throwable
//
//public final class String implements Serializable, Comparable, CharSequence {
//	private static class CaseInsensitiveComparator implements Comparator, Serializable {
//
//		public int compare(String s, String s1) {
//			int i = s.length();
//			int j = s1.length();
//			int k = Math.min(i, j);
//			for (int l = 0; l < k; l++) {
//				char c = s.charAt(l);
//				char c1 = s1.charAt(l);
//				if (c == c1)
//					continue;
//				c = Character.toUpperCase(c);
//				c1 = Character.toUpperCase(c1);
//				if (c == c1)
//					continue;
//				c = Character.toLowerCase(c);
//				c1 = Character.toLowerCase(c1);
//				if (c != c1)
//					return c - c1;
//			}
//
//			return i - j;
//		}
//
//		private Object readResolve() {
//			return String.CASE_INSENSITIVE_ORDER;
//		}
//
//		public volatile int compare(Object obj, Object obj1) {
//			return compare((String) obj, (String) obj1);
//		}
//
//		private static final long serialVersionUID = 8575799808933029326L;
//
//		private CaseInsensitiveComparator() {
//		}
//
//	}
//
//	public String() {
//		value = "".value;
//	}
//
//	public String(String s) {
//		value = s.value;
//		hash = s.hash;
//	}
//
//	public String(char ac[]) {
//		value = Arrays.copyOf(ac, ac.length);
//	}
//
//	public String(char ac[], int i, int j) {
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		if (j <= 0) {
//			if (j < 0)
//				throw new StringIndexOutOfBoundsException(j);
//			if (i <= ac.length) {
//				value = "".value;
//				return;
//			}
//		}
//		if (i > ac.length - j) {
//			throw new StringIndexOutOfBoundsException(i + j);
//		} else {
//			value = Arrays.copyOfRange(ac, i, i + j);
//			return;
//		}
//	}
//
//	public String(int ai[], int i, int j) {
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		if (j <= 0) {
//			if (j < 0)
//				throw new StringIndexOutOfBoundsException(j);
//			if (i <= ai.length) {
//				value = "".value;
//				return;
//			}
//		}
//		if (i > ai.length - j)
//			throw new StringIndexOutOfBoundsException(i + j);
//		int k = i + j;
//		int l = j;
//		for (int i1 = i; i1 < k; i1++) {
//			int j1 = ai[i1];
//			if (Character.isBmpCodePoint(j1))
//				continue;
//			if (Character.isValidCodePoint(j1))
//				l++;
//			else
//				throw new IllegalArgumentException(Integer.toString(j1));
//		}
//
//		char ac[] = new char[l];
//		int k1 = i;
//		for (int l1 = 0; k1 < k; l1++) {
//			int i2 = ai[k1];
//			if (Character.isBmpCodePoint(i2))
//				ac[l1] = (char) i2;
//			else
//				Character.toSurrogates(i2, ac, l1++);
//			k1++;
//		}
//
//		value = ac;
//	}
//
//	/**
//	 * @deprecated Method String is deprecated
//	 */
//
//	public String(byte abyte0[], int i, int j, int k) {
//		checkBounds(abyte0, j, k);
//		char ac[] = new char[k];
//		if (i == 0) {
//			for (int l = k; l-- > 0;)
//				ac[l] = (char) (abyte0[l + j] & 255);
//
//		} else {
//			i <<= 8;
//			for (int i1 = k; i1-- > 0;)
//				ac[i1] = (char) (i | abyte0[i1 + j] & 255);
//
//		}
//		value = ac;
//	}
//
//	/**
//	 * @deprecated Method String is deprecated
//	 */
//
//	public String(byte abyte0[], int i) {
//		this(abyte0, i, 0, abyte0.length);
//	}
//
//	private static void checkBounds(byte abyte0[], int i, int j) {
//		if (j < 0)
//			throw new StringIndexOutOfBoundsException(j);
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		if (i > abyte0.length - j)
//			throw new StringIndexOutOfBoundsException(i + j);
//		else
//			return;
//	}
//
//	public String(byte abyte0[], int i, int j, String s) throws UnsupportedEncodingException {
//		if (s == null) {
//			throw new NullPointerException("charsetName");
//		} else {
//			checkBounds(abyte0, i, j);
//			value = StringCoding.decode(s, abyte0, i, j);
//			return;
//		}
//	}
//
//	public String(byte abyte0[], int i, int j, Charset charset) {
//		if (charset == null) {
//			throw new NullPointerException("charset");
//		} else {
//			checkBounds(abyte0, i, j);
//			value = StringCoding.decode(charset, abyte0, i, j);
//			return;
//		}
//	}
//
//	public String(byte abyte0[], String s) throws UnsupportedEncodingException {
//		this(abyte0, 0, abyte0.length, s);
//	}
//
//	public String(byte abyte0[], Charset charset) {
//		this(abyte0, 0, abyte0.length, charset);
//	}
//
//	public String(byte abyte0[], int i, int j) {
//		checkBounds(abyte0, i, j);
//		value = StringCoding.decode(abyte0, i, j);
//	}
//
//	public String(byte abyte0[]) {
//		this(abyte0, 0, abyte0.length);
//	}
//
//	public String(StringBuffer stringbuffer) {
//		synchronized (stringbuffer) {
//			value = Arrays.copyOf(stringbuffer.getValue(), stringbuffer.length());
//		}
//	}
//
//	public String(StringBuilder stringbuilder) {
//		value = Arrays.copyOf(stringbuilder.getValue(), stringbuilder.length());
//	}
//
//	String(char ac[], boolean flag) {
//		value = ac;
//	}
//
//	public int length() {
//		return value.length;
//	}
//
//	public boolean isEmpty() {
//		return value.length == 0;
//	}
//
//	public char charAt(int i) {
//		if (i < 0 || i >= value.length)
//			throw new StringIndexOutOfBoundsException(i);
//		else
//			return value[i];
//	}
//
//	public int codePointAt(int i) {
//		if (i < 0 || i >= value.length)
//			throw new StringIndexOutOfBoundsException(i);
//		else
//			return Character.codePointAtImpl(value, i, value.length);
//	}
//
//	public int codePointBefore(int i) {
//		int j = i - 1;
//		if (j < 0 || j >= value.length)
//			throw new StringIndexOutOfBoundsException(i);
//		else
//			return Character.codePointBeforeImpl(value, i, 0);
//	}
//
//	public int codePointCount(int i, int j) {
//		if (i < 0 || j > value.length || i > j)
//			throw new IndexOutOfBoundsException();
//		else
//			return Character.codePointCountImpl(value, i, j - i);
//	}
//
//	public int offsetByCodePoints(int i, int j) {
//		if (i < 0 || i > value.length)
//			throw new IndexOutOfBoundsException();
//		else
//			return Character.offsetByCodePointsImpl(value, 0, value.length, i, j);
//	}
//
//	void getChars(char ac[], int i) {
//		System.arraycopy(value, 0, ac, i, value.length);
//	}
//
//	public void getChars(int i, int j, char ac[], int k) {
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		if (j > value.length)
//			throw new StringIndexOutOfBoundsException(j);
//		if (i > j) {
//			throw new StringIndexOutOfBoundsException(j - i);
//		} else {
//			System.arraycopy(value, i, ac, k, j - i);
//			return;
//		}
//	}
//
//	/**
//	 * @deprecated Method getBytes is deprecated
//	 */
//
//	public void getBytes(int i, int j, byte abyte0[], int k) {
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		if (j > value.length)
//			throw new StringIndexOutOfBoundsException(j);
//		if (i > j)
//			throw new StringIndexOutOfBoundsException(j - i);
//		Objects.requireNonNull(abyte0);
//		int l = k;
//		int i1 = j;
//		int j1 = i;
//		char ac[] = value;
//		while (j1 < i1)
//			abyte0[l++] = (byte) ac[j1++];
//	}
//
//	public byte[] getBytes(String s) throws UnsupportedEncodingException {
//		if (s == null)
//			throw new NullPointerException();
//		else
//			return StringCoding.encode(s, value, 0, value.length);
//	}
//
//	public byte[] getBytes(Charset charset) {
//		if (charset == null)
//			throw new NullPointerException();
//		else
//			return StringCoding.encode(charset, value, 0, value.length);
//	}
//
//	public byte[] getBytes() {
//		return StringCoding.encode(value, 0, value.length);
//	}
//
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj instanceof String) {
//			String s = (String) obj;
//			int i = value.length;
//			if (i == s.value.length) {
//				char ac[] = value;
//				char ac1[] = s.value;
//				for (int j = 0; i-- != 0; j++)
//					if (ac[j] != ac1[j])
//						return false;
//
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean contentEquals(StringBuffer stringbuffer) {
//		return contentEquals(((CharSequence) (stringbuffer)));
//	}
//
//	private boolean nonSyncContentEquals(AbstractStringBuilder abstractstringbuilder) {
//		char ac[] = value;
//		char ac1[] = abstractstringbuilder.getValue();
//		int i = ac.length;
//		if (i != abstractstringbuilder.length())
//			return false;
//		for (int j = 0; j < i; j++)
//			if (ac[j] != ac1[j])
//				return false;
//
//		return true;
//	}
//
//	public boolean contentEquals(CharSequence charsequence)
// {
//     if(!(charsequence instanceof AbstractStringBuilder))
//         break MISSING_BLOCK_LABEL_43;
//     if(!(charsequence instanceof StringBuffer))
//         break MISSING_BLOCK_LABEL_34;
//     CharSequence charsequence1 = charsequence;
//     JVM INSTR monitorenter ;
//     return nonSyncContentEquals((AbstractStringBuilder)charsequence);
//     Exception exception;
//     exception;
//     throw exception;
//     return nonSyncContentEquals((AbstractStringBuilder)charsequence);
//     if(charsequence instanceof String)
//         return equals(charsequence);
//     char ac[] = value;
//     int i = ac.length;
//     if(i != charsequence.length())
//         return false;
//     for(int j = 0; j < i; j++)
//         if(ac[j] != charsequence.charAt(j))
//             return false;
//
//     return true;
// }
//
//	public boolean equalsIgnoreCase(String s) {
//		return this != s ? s != null && s.value.length == value.length && regionMatches(true, 0, s, 0, value.length)
//				: true;
//	}
//
//	public int compareTo(String s) {
//		int i = value.length;
//		int j = s.value.length;
//		int k = Math.min(i, j);
//		char ac[] = value;
//		char ac1[] = s.value;
//		for (int l = 0; l < k; l++) {
//			char c = ac[l];
//			char c1 = ac1[l];
//			if (c != c1)
//				return c - c1;
//		}
//
//		return i - j;
//	}
//
//	public int compareToIgnoreCase(String s) {
//		return CASE_INSENSITIVE_ORDER.compare(this, s);
//	}
//
//	public boolean regionMatches(int i, String s, int j, int k) {
//		char ac[] = value;
//		int l = i;
//		char ac1[] = s.value;
//		int i1 = j;
//		if (j < 0 || i < 0 || (long) i > (long) value.length - (long) k || (long) j > (long) s.value.length - (long) k)
//			return false;
//		while (k-- > 0)
//			if (ac[l++] != ac1[i1++])
//				return false;
//		return true;
//	}
//
//	public boolean regionMatches(boolean flag, int i, String s, int j, int k) {
//		label0: {
//			char ac[] = value;
//			int l = i;
//			char ac1[] = s.value;
//			int i1 = j;
//			if (j < 0 || i < 0 || (long) i > (long) value.length - (long) k
//					|| (long) j > (long) s.value.length - (long) k)
//				return false;
//			char c2;
//			char c3;
//			do {
//				char c;
//				char c1;
//				do {
//					if (k-- <= 0)
//						break label0;
//					c = ac[l++];
//					c1 = ac1[i1++];
//				} while (c == c1);
//				if (!flag)
//					break;
//				c2 = Character.toUpperCase(c);
//				c3 = Character.toUpperCase(c1);
//			} while (c2 == c3 || Character.toLowerCase(c2) == Character.toLowerCase(c3));
//			return false;
//		}
//		return true;
//	}
//
//	public boolean startsWith(String s, int i) {
//		char ac[] = value;
//		int j = i;
//		char ac1[] = s.value;
//		int k = 0;
//		int l = s.value.length;
//		if (i < 0 || i > value.length - l)
//			return false;
//		while (--l >= 0)
//			if (ac[j++] != ac1[k++])
//				return false;
//		return true;
//	}
//
//	public boolean startsWith(String s) {
//		return startsWith(s, 0);
//	}
//
//	public boolean endsWith(String s) {
//		return startsWith(s, value.length - s.value.length);
//	}
//
//	public int hashCode() {
//		int i = hash;
//		if (i == 0 && value.length > 0) {
//			char ac[] = value;
//			for (int j = 0; j < value.length; j++)
//				i = 31 * i + ac[j];
//
//			hash = i;
//		}
//		return i;
//	}
//
//	public int indexOf(int i) {
//		return indexOf(i, 0);
//	}
//
//	public int indexOf(int i, int j) {
//		int k = value.length;
//		if (j < 0)
//			j = 0;
//		else if (j >= k)
//			return -1;
//		if (i < 65536) {
//			char ac[] = value;
//			for (int l = j; l < k; l++)
//				if (ac[l] == i)
//					return l;
//
//			return -1;
//		} else {
//			return indexOfSupplementary(i, j);
//		}
//	}
//
//	private int indexOfSupplementary(int i, int j) {
//		if (Character.isValidCodePoint(i)) {
//			char ac[] = value;
//			char c = Character.highSurrogate(i);
//			char c1 = Character.lowSurrogate(i);
//			int k = ac.length - 1;
//			for (int l = j; l < k; l++)
//				if (ac[l] == c && ac[l + 1] == c1)
//					return l;
//
//		}
//		return -1;
//	}
//
//	public int lastIndexOf(int i) {
//		return lastIndexOf(i, value.length - 1);
//	}
//
//	public int lastIndexOf(int i, int j) {
//		if (i < 65536) {
//			char ac[] = value;
//			for (int k = Math.min(j, ac.length - 1); k >= 0; k--)
//				if (ac[k] == i)
//					return k;
//
//			return -1;
//		} else {
//			return lastIndexOfSupplementary(i, j);
//		}
//	}
//
//	private int lastIndexOfSupplementary(int i, int j) {
//		if (Character.isValidCodePoint(i)) {
//			char ac[] = value;
//			char c = Character.highSurrogate(i);
//			char c1 = Character.lowSurrogate(i);
//			for (int k = Math.min(j, ac.length - 2); k >= 0; k--)
//				if (ac[k] == c && ac[k + 1] == c1)
//					return k;
//
//		}
//		return -1;
//	}
//
//	public int indexOf(String s) {
//		return indexOf(s, 0);
//	}
//
//	public int indexOf(String s, int i) {
//		return indexOf(value, 0, value.length, s.value, 0, s.value.length, i);
//	}
//
//	static int indexOf(char ac[], int i, int j, String s, int k) {
//		return indexOf(ac, i, j, s.value, 0, s.value.length, k);
//	}
//
//	static int indexOf(char ac[], int i, int j, char ac1[], int k, int l, int i1) {
//		if (i1 >= j)
//			return l != 0 ? -1 : j;
//		if (i1 < 0)
//			i1 = 0;
//		if (l == 0)
//			return i1;
//		char c = ac1[k];
//		int j1 = i + (j - l);
//		for (int k1 = i + i1; k1 <= j1; k1++) {
//			if (ac[k1] != c)
//				while (++k1 <= j1 && ac[k1] != c)
//					;
//			if (k1 > j1)
//				continue;
//			int l1 = k1 + 1;
//			int i2 = (l1 + l) - 1;
//			for (int j2 = k + 1; l1 < i2 && ac[l1] == ac1[j2]; j2++)
//				l1++;
//
//			if (l1 == i2)
//				return k1 - i;
//		}
//
//		return -1;
//	}
//
//	public int lastIndexOf(String s) {
//		return lastIndexOf(s, value.length);
//	}
//
//	public int lastIndexOf(String s, int i) {
//		return lastIndexOf(value, 0, value.length, s.value, 0, s.value.length, i);
//	}
//
//	static int lastIndexOf(char ac[], int i, int j, String s, int k) {
//		return lastIndexOf(ac, i, j, s.value, 0, s.value.length, k);
//	}
//
//	static int lastIndexOf(char ac[], int i, int j, char ac1[], int k, int l, int i1) {
//		int j1 = j - l;
//		if (i1 < 0)
//			return -1;
//		if (i1 > j1)
//			i1 = j1;
//		if (l == 0)
//			return i1;
//		int k1 = (k + l) - 1;
//		char c = ac1[k1];
//		int l1 = (i + l) - 1;
//		int k2;
//		label0: do {
//			int i2;
//			for (i2 = l1 + i1; i2 >= l1 && ac[i2] != c; i2--)
//				;
//			if (i2 < l1)
//				return -1;
//			int j2 = i2 - 1;
//			k2 = j2 - (l - 1);
//			int l2 = k1 - 1;
//			do
//				if (j2 <= k2)
//					break label0;
//			while (ac[j2--] == ac1[l2--]);
//			i2--;
//		} while (true);
//		return (k2 - i) + 1;
//	}
//
//	public String substring(int i) {
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		int j = value.length - i;
//		if (j < 0)
//			throw new StringIndexOutOfBoundsException(j);
//		else
//			return i != 0 ? new String(value, i, j) : this;
//	}
//
//	public String substring(int i, int j) {
//		if (i < 0)
//			throw new StringIndexOutOfBoundsException(i);
//		if (j > value.length)
//			throw new StringIndexOutOfBoundsException(j);
//		int k = j - i;
//		if (k < 0)
//			throw new StringIndexOutOfBoundsException(k);
//		else
//			return i != 0 || j != value.length ? new String(value, i, k) : this;
//	}
//
//	public CharSequence subSequence(int i, int j) {
//		return substring(i, j);
//	}
//
//	public String concat(String s) {
//		int i = s.length();
//		if (i == 0) {
//			return this;
//		} else {
//			int j = value.length;
//			char ac[] = Arrays.copyOf(value, j + i);
//			s.getChars(ac, j);
//			return new String(ac, true);
//		}
//	}
//
//	public String replace(char c, char c1) {
//		if (c != c1) {
//			int i = value.length;
//			int j = -1;
//			char ac[];
//			for (ac = value; ++j < i && ac[j] != c;)
//				;
//			if (j < i) {
//				char ac1[] = new char[i];
//				for (int k = 0; k < j; k++)
//					ac1[k] = ac[k];
//
//				for (; j < i; j++) {
//					char c2 = ac[j];
//					ac1[j] = c2 != c ? c2 : c1;
//				}
//
//				return new String(ac1, true);
//			}
//		}
//		return this;
//	}
//
//	public boolean matches(String s) {
//		return Pattern.matches(s, this);
//	}
//
//	public boolean contains(CharSequence charsequence) {
//		return indexOf(charsequence.toString()) > -1;
//	}
//
//	public String replaceFirst(String s, String s1) {
//		return Pattern.compile(s).matcher(this).replaceFirst(s1);
//	}
//
//	public String replaceAll(String s, String s1) {
//		return Pattern.compile(s).matcher(this).replaceAll(s1);
//	}
//
//	public String replace(CharSequence charsequence, CharSequence charsequence1) {
//		return Pattern.compile(charsequence.toString(), 16).matcher(this)
//				.replaceAll(Matcher.quoteReplacement(charsequence1.toString()));
//	}
//
//	public String[] split(String s, int i) {
//		label0: {
//			int j;
//			boolean flag1;
//			ArrayList arraylist;
//			label1: {
//				char c = '\0';
//				if ((s.value.length != 1 || ".$|()[{^?*+\\".indexOf(c = s.charAt(0)) != -1)
//						&& (s.length() != 2 || s.charAt(0) != '\\' || ((c = s.charAt(1)) - 48 | 57 - c) >= 0
//								|| (c - 97 | 122 - c) >= 0 || (c - 65 | 90 - c) >= 0)
//						|| c >= '\uD800' && c <= '\uDFFF')
//					break label0;
//				j = 0;
//				boolean flag = false;
//				flag1 = i > 0;
//				arraylist = new ArrayList();
//				do {
//					int k;
//					if ((k = indexOf(c, j)) == -1)
//						break label1;
//					if (flag1 && arraylist.size() >= i - 1)
//						break;
//					arraylist.add(substring(j, k));
//					j = k + 1;
//				} while (true);
//				arraylist.add(substring(j, value.length));
//				j = value.length;
//			}
//			if (j == 0)
//				return (new String[] { this });
//			if (!flag1 || arraylist.size() < i)
//				arraylist.add(substring(j, value.length));
//			int l = arraylist.size();
//			if (i == 0)
//				for (; l > 0 && ((String) arraylist.get(l - 1)).length() == 0; l--)
//					;
//			String as[] = new String[l];
//			return (String[]) arraylist.subList(0, l).toArray(as);
//		}
//		return Pattern.compile(s).split(this, i);
//	}
//
//	public String[] split(String s) {
//		return split(s, 0);
//	}
//
//	public static transient String join(CharSequence charsequence, CharSequence acharsequence[]) {
//		Objects.requireNonNull(charsequence);
//		Objects.requireNonNull(acharsequence);
//		StringJoiner stringjoiner = new StringJoiner(charsequence);
//		CharSequence acharsequence1[] = acharsequence;
//		int i = acharsequence1.length;
//		for (int j = 0; j < i; j++) {
//			CharSequence charsequence1 = acharsequence1[j];
//			stringjoiner.add(charsequence1);
//		}
//
//		return stringjoiner.toString();
//	}
//
//	public static String join(CharSequence charsequence, Iterable iterable) {
//		Objects.requireNonNull(charsequence);
//		Objects.requireNonNull(iterable);
//		StringJoiner stringjoiner = new StringJoiner(charsequence);
//		CharSequence charsequence1;
//		for (Iterator iterator = iterable.iterator(); iterator.hasNext(); stringjoiner.add(charsequence1))
//			charsequence1 = (CharSequence) iterator.next();
//
//		return stringjoiner.toString();
//	}
//
//	public String toLowerCase(Locale locale) {
//		int i;
//		int j;
//		label0: {
//			if (locale == null)
//				throw new NullPointerException();
//			j = value.length;
//			for (i = 0; i < j;) {
//				char c = value[i];
//				if (c >= '\uD800' && c <= '\uDBFF') {
//					int k = codePointAt(i);
//					if (k != Character.toLowerCase(k))
//						break label0;
//					i += Character.charCount(k);
//				} else {
//					if (c != Character.toLowerCase(c))
//						break label0;
//					i++;
//				}
//			}
//
//			return this;
//		}
//		char ac[] = new char[j];
//		int l = 0;
//		System.arraycopy(value, 0, ac, 0, i);
//		String s = locale.getLanguage();
//		boolean flag = s == "tr" || s == "az" || s == "lt";
//		int k1;
//		for (int l1 = i; l1 < j; l1 += k1) {
//			int j1 = value[l1];
//			if ((char) j1 >= '\uD800' && (char) j1 <= '\uDBFF') {
//				j1 = codePointAt(l1);
//				k1 = Character.charCount(j1);
//			} else {
//				k1 = 1;
//			}
//			int i1;
//			if (flag || j1 == 931 || j1 == 304)
//				i1 = ConditionalSpecialCasing.toLowerCaseEx(this, l1, locale);
//			else
//				i1 = Character.toLowerCase(j1);
//			if (i1 == -1 || i1 >= 65536) {
//				char ac1[];
//				if (i1 == -1) {
//					ac1 = ConditionalSpecialCasing.toLowerCaseCharArray(this, l1, locale);
//				} else {
//					if (k1 == 2) {
//						l += Character.toChars(i1, ac, l1 + l) - k1;
//						continue;
//					}
//					ac1 = Character.toChars(i1);
//				}
//				int i2 = ac1.length;
//				if (i2 > k1) {
//					char ac2[] = new char[(ac.length + i2) - k1];
//					System.arraycopy(ac, 0, ac2, 0, l1 + l);
//					ac = ac2;
//				}
//				for (int j2 = 0; j2 < i2; j2++)
//					ac[l1 + l + j2] = ac1[j2];
//
//				l += i2 - k1;
//			} else {
//				ac[l1 + l] = (char) i1;
//			}
//		}
//
//		return new String(ac, 0, j + l);
//	}
//
//	public String toLowerCase() {
//		return toLowerCase(Locale.getDefault());
//	}
//
//	public String toUpperCase(Locale locale) {
//		if (locale == null)
//			throw new NullPointerException();
//		int j = value.length;
//		for (int i = 0; i < j;) {
//			int k = value[i];
//			int i1;
//			if (k >= 55296 && k <= 56319) {
//				k = codePointAt(i);
//				i1 = Character.charCount(k);
//			} else {
//				i1 = 1;
//			}
//			int j1 = Character.toUpperCaseEx(k);
//			if (j1 != -1 && k == j1) {
//				i += i1;
//			} else {
//				int l = 0;
//				char ac[] = new char[j];
//				System.arraycopy(value, 0, ac, 0, i);
//				String s = locale.getLanguage();
//				boolean flag = s == "tr" || s == "az" || s == "lt";
//				int i2;
//				for (int j2 = i; j2 < j; j2 += i2) {
//					int l1 = value[j2];
//					if ((char) l1 >= '\uD800' && (char) l1 <= '\uDBFF') {
//						l1 = codePointAt(j2);
//						i2 = Character.charCount(l1);
//					} else {
//						i2 = 1;
//					}
//					int k1;
//					if (flag)
//						k1 = ConditionalSpecialCasing.toUpperCaseEx(this, j2, locale);
//					else
//						k1 = Character.toUpperCaseEx(l1);
//					if (k1 == -1 || k1 >= 65536) {
//						char ac1[];
//						if (k1 == -1) {
//							if (flag)
//								ac1 = ConditionalSpecialCasing.toUpperCaseCharArray(this, j2, locale);
//							else
//								ac1 = Character.toUpperCaseCharArray(l1);
//						} else {
//							if (i2 == 2) {
//								l += Character.toChars(k1, ac, j2 + l) - i2;
//								continue;
//							}
//							ac1 = Character.toChars(k1);
//						}
//						int k2 = ac1.length;
//						if (k2 > i2) {
//							char ac2[] = new char[(ac.length + k2) - i2];
//							System.arraycopy(ac, 0, ac2, 0, j2 + l);
//							ac = ac2;
//						}
//						for (int l2 = 0; l2 < k2; l2++)
//							ac[j2 + l + l2] = ac1[l2];
//
//						l += k2 - i2;
//					} else {
//						ac[j2 + l] = (char) k1;
//					}
//				}
//
//				return new String(ac, 0, j + l);
//			}
//		}
//
//		return this;
//	}
//
//	public String toUpperCase() {
//		return toUpperCase(Locale.getDefault());
//	}
//
//	public String trim() {
//		int i = value.length;
//		int j = 0;
//		char ac[];
//		for (ac = value; j < i && ac[j] <= ' '; j++)
//			;
//		for (; j < i && ac[i - 1] <= ' '; i--)
//			;
//		return j <= 0 && i >= value.length ? this : substring(j, i);
//	}
//
//	public String toString() {
//		return this;
//	}
//
//	public char[] toCharArray() {
//		char ac[] = new char[value.length];
//		System.arraycopy(value, 0, ac, 0, value.length);
//		return ac;
//	}
//
//	public static transient String format(String s, Object aobj[]) {
//		return (new Formatter()).format(s, aobj).toString();
//	}
//
//	public static transient String format(Locale locale, String s, Object aobj[]) {
//		return (new Formatter(locale)).format(s, aobj).toString();
//	}
//
//	public static String valueOf(Object obj) {
//		return obj != null ? obj.toString() : "null";
//	}
//
//	public static String valueOf(char ac[]) {
//		return new String(ac);
//	}
//
//	public static String valueOf(char ac[], int i, int j) {
//		return new String(ac, i, j);
//	}
//
//	public static String copyValueOf(char ac[], int i, int j) {
//		return new String(ac, i, j);
//	}
//
//	public static String copyValueOf(char ac[]) {
//		return new String(ac);
//	}
//
//	public static String valueOf(boolean flag) {
//		return flag ? "true" : "false";
//	}
//
//	public static String valueOf(char c) {
//		char ac[] = { c };
//		return new String(ac, true);
//	}
//
//	public static String valueOf(int i) {
//		return Integer.toString(i);
//	}
//
//	public static String valueOf(long l) {
//		return Long.toString(l);
//	}
//
//	public static String valueOf(float f) {
//		return Float.toString(f);
//	}
//
//	public static String valueOf(double d) {
//		return Double.toString(d);
//	}
//
//	public native String intern();
//
//	public volatile int compareTo(Object obj) {
//		return compareTo((String) obj);
//	}
//
//	private final char value[];
//	private int hash;
//	private static final long serialVersionUID = -6849794470754667710L;
//	private static final ObjectStreamField serialPersistentFields[] = new ObjectStreamField[0];
//	public static final Comparator CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator();
//
//}
//
///*
// * DECOMPILATION REPORT
// * 
// * Decompiled from: C:\Program Files\Java\jre1.8.0_181\lib\rt.jar Total time:
// * 999 ms Jad reported messages/errors: Overlapped try statements detected. Not
// * all exception handlers will be resolved in the method <init> Overlapped try
// * statements detected. Not all exception handlers will be resolved in the
// * method contentEquals Couldn't fully decompile method contentEquals Couldn't
// * resolve all exception handlers in method contentEquals Exit status: 0 Caught
// * exceptions:
// */

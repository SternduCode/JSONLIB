package com.sterndu.json;

import java.io.*;
import java.math.*;
import java.util.Arrays;

public class JsonParser {

	private enum Type {
		WhiteSpace(' ', '	', '\n', '\r'),
		Number('-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', 'E', 'e'),
		String('"'),
		Array('[', ']'),
		Object('{', '}');

		private final char[] chars;

		Type(char... chars) {
			this.chars = chars;
			Arrays.sort(this.chars);
		}

		public boolean isPartOf(char c) {
			return Arrays.binarySearch(chars, c) >= 0;
		}

	}

	private static JsonValue parse(InputStream in, char[] d_arr) throws JsonParseException {
		try {
			Type cur = null;
			char c = d_arr[0];
			for (Type t: Type.values()) if (t.isPartOf(c)) cur = t;
			if (cur == null) switch (c) {
				case 'f':
					char[] c_arr = new char[] {'a', 'l', 's', 'e'};
					for (int i = 0; i < c_arr.length; i++) if ((char) in.read() != c_arr[i]) break;
					else if (i == c_arr.length - 1) {
						d_arr[0] = (char) in.read();
						return new BoolValue(false);
					}
					throw new JsonParseException("Not a correct Json-format");
				case 't':
					char[] c_arr1 = new char[] {'r', 'u', 'e'};
					for (int i = 0; i < c_arr1.length; i++) if ((char) in.read() != c_arr1[i]) break;
					else if (i == c_arr1.length - 1) {
						d_arr[0] = (char) in.read();
						return new BoolValue(true);
					}
					throw new JsonParseException("Not a correct Json-format");
				case 'n':
					char[] c_arr2 = new char[] {'u', 'l', 'l'};
					for (int i = 0; i < c_arr2.length; i++) if ((char) in.read() != c_arr2[i]) break;
					else if (i == c_arr2.length - 1) {
						d_arr[0] = (char) in.read();
						return new NullValue();
					}
					throw new JsonParseException("Not a correct Json-format");
				default:
					throw new JsonParseException("Not a correct Json-format");
			}
			while (in.available() > 0) switch (cur) {
				case String:
					StringBuilder sb=new StringBuilder();
					char last = 0;
					while (in.available() > 0) {
						char temp = (char) in.read();
						if (temp=='"' && last != '\\') { d_arr[0]=(char) in.read(); return new StringValue(sb.toString()); }
						else { sb.append(temp); last = temp;}
					}
					throw new JsonParseException("Not a correct Json-format: " + sb.toString());
				case Number:
					StringBuilder sb1=new StringBuilder();
					sb1.append(c);
					while (in.available() > 0) {
						char temp = (char) in.read();
						if (cur.isPartOf(temp)) sb1.append(temp);
						else { d_arr[0]=temp; break; }
					}
					try {
						BigDecimal bd=new BigDecimal(sb1.toString());
						double d=bd.doubleValue();
						if (bd.compareTo(new BigDecimal(d))==0) {
							long l=(long) d;
							if (l==d) {
								byte b=(byte) l;
								short s=(short) l;
								int i=(int) l;
								if (b==l) return new ByteValue(b);
								else if (s==l) return new ShortValue(s);
								else if (i==l) return new IntegerValue(i);
								else return new LongValue(l);
							} else {
								float f=(float) d;
								if (f==d) return new FloatValue(f);
								else return new DoubleValue(d);
							}
						} else {
							BigInteger bi=bd.toBigInteger();
							if (bd.compareTo(new BigDecimal(bi))==0) return new BigIntegerValue(bi);
							else return new BigDecimalValue(bd);
						}
					} catch (NumberFormatException e) {
						throw new JsonParseException("Not a correct Json-format: Not a Number: " + sb1.toString());
					}
				case WhiteSpace:
					c = (char) in.read();
					cur=null;
					for (Type t: Type.values()) if (t.isPartOf(c)) cur = t;
					if (cur == null) switch (c) {
						case 'f':
							char[] c_arr = new char[] {'a', 'l', 's', 'e'};
							for (int i = 0; i < c_arr.length; i++) if ((char) in.read() != c_arr[i]) break;
							else if (i == 2) return new BoolValue(false);
							throw new JsonParseException("Not a correct Json-format");
						case 't':
							char[] c_arr1 = new char[] {'r', 'u', 'e'};
							for (int i = 0; i < c_arr1.length; i++) if ((char) in.read() != c_arr1[i]) break;
							else if (i == 2) return new BoolValue(true);
							throw new JsonParseException("Not a correct Json-format");
						case 'n':
							char[] c_arr2 = new char[] {'u', 'l', 'l'};
							for (int i = 0; i < c_arr2.length; i++) if ((char) in.read() != c_arr2[i]) break;
							else if (i == 2) return new NullValue();
							throw new JsonParseException("Not a correct Json-format");
						default:
							throw new JsonParseException("Not a correct Json-format");
					}
					continue;
				case Array:
					JsonArray ja=new JsonArray();
					char[] ca_arr=new char[] {(char) in.read()};
					do {
						while (in.available() > 0) if (ca_arr[0] == ',' | Type.WhiteSpace.isPartOf(ca_arr[0])) ca_arr[0] = (char) in.read();
						else break;
						if (ca_arr[0] == ']') break;
						ja.add(parse(in, ca_arr));
					} while (ca_arr[0]==','|Type.WhiteSpace.isPartOf(ca_arr[0]));
					if (ca_arr[0] == ']') {
						d_arr[0] = (char) in.read();
						return ja;
					} else throw new JsonParseException("Not a correct Json-format");
				case Object:
					JsonObject jo = new JsonObject();
					char ba_arr[] = new char[] {(char) in.read()};
					do {
						while (in.available() > 0)
							if (ba_arr[0] == ',' | Type.WhiteSpace.isPartOf(ba_arr[0])) ba_arr[0] = (char) in.read();
							else break;
						if (ba_arr[0] == '}') break;
						String name = "";
						if (ba_arr[0] == '"') {
							StringBuilder sb2 = new StringBuilder();
							char last1 = 0;
							while (in.available() > 0) {
								char temp = (char) in.read();
								if (temp == '"' && last1 != '\\') {
									ba_arr[0] = (char) in.read();
									name = sb2.toString();
									break;
								} else {
									sb2.append(temp);
									last1 = temp;
								}
							}
						} else throw new JsonParseException("Not a correct Json-format");
						while (in.available() > 0)
							if (ba_arr[0] == ':' | Type.WhiteSpace.isPartOf(ba_arr[0])) ba_arr[0] = (char) in.read();
							else break;
						jo.put(name, parse(in, ba_arr));
					} while (ba_arr[0] == ',' | Type.WhiteSpace.isPartOf(ba_arr[0]));
					if (ba_arr[0] == '}') {
						d_arr[0] = (char) in.read();
						return jo;
					} else throw new JsonParseException("Not a correct Json-format");
			}
			throw new JsonParseException("Not a correct Json-format: " + c);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonValue parse(File f) throws JsonParseException {
		try {
			return parse(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonValue parse(InputStream in) throws JsonParseException {
		try {
			return parse(in,new char[] {(char) in.read()});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonValue parse(String str) throws JsonParseException {
		try {
			return parse(new ByteArrayInputStream(str.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}

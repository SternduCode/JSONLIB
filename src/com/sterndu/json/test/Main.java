package com.sterndu.json.test;

import java.io.*;
import java.math.*;
import com.sterndu.json.*;

public class Main {

	public static InputStream getStringStream(String str) {
		try {
			return new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		JsonObject obj = new JsonObject();
		JsonObject alp = new JsonObject();
		obj.put("alpha", alp);
		JsonArray arr = new JsonArray();
		obj.put("arr", arr);
		arr.add(1);
		arr.add(true);
		arr.add("hi");
		alp.put("d", 5);
		alp.put("c", true);
		alp.put("h", null);
		alp.put("z", "wdym");
		System.out.println(obj.toJson());
		arr.add(null);
		arr.add(false);
		arr.add(arr);
		arr.add(0.3d);
		arr.add(1.0d / 3.0d);
		try {
			System.out.println(obj.toJson());
			JsonValue jv = JsonParser.parse(getStringStream(obj.toJson()));
			System.out.println(jv.toJson());

			BigInteger bi = BigInteger.valueOf(1852805403500234500l);
			bi = bi.pow(6);
			System.out.println(String.format("%e", new BigDecimal(bi)));

		} catch (JsonParseException e) {
			e.printStackTrace();
		}
	}

}

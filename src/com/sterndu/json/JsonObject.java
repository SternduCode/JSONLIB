package com.sterndu.json;

import java.util.*;
import java.util.function.Function;

public class JsonObject extends HashMap<String,Object> implements JsonValue {

	/**
	 *
	 */
	private static final long serialVersionUID = 5544447067100285990L;

	public JsonObject() {}

	public JsonObject(Map<String, ?> m) {
		super(m);
	}

	@Override
	public String toJson() {
		return toJson(o -> "\"" + o + "\"");
	}

	@Override
	public String toJson(Function<Object, String> function) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		boolean b = true;
		for (Entry<String, Object> e: entrySet()) if (e.getValue()!=this) {
			if (b) b = false;
			else sb.append(",\n");
			sb.append("\"" + e.getKey() + "\" : ");
			sb.append(JsonUtil.serialize(e.getValue(), function));
		}
		return sb.toString().replace("\n", "\n\t") + "\n}";
	}

	@Override
	public String toString() {
		return toJson();
	}

}

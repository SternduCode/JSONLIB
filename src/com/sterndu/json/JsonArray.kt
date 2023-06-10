package com.sterndu.json;

import java.util.*;
import java.util.function.Function;

public class JsonArray extends ArrayList<Object> implements JsonValue {

	/**
	 *
	 */
	private static final long serialVersionUID = 2915477326174591243L;

	public JsonArray() {}

	public JsonArray(Collection<?> c) {
		super(c);
	}

	@Override
	public String toJson() {
		return toJson(o -> "\"" + o + "\"");
	}

	@Override
	public String toJson(Function<Object, String> function) {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		boolean b = true;
		for (Object o: this) if (o!=this) {
			if (b) b = false;
			else sb.append(",\n");
			sb.append(JsonUtil.serialize(o, function));
		}
		return sb.toString().replace("\n", "\n\t") + "\n]";
	}

	@Override
	public JsonValue toJsonValue() {
		return this;
	}

	@Override
	public JsonValue toJsonValue(Function<Object, String> function) {
		return this;
	}

	@Override
	public String toString() {
		return toJson();
	}


}

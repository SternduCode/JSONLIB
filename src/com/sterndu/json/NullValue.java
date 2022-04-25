package com.sterndu.json;

import java.util.function.Function;

public class NullValue implements JsonValue {

	public Object getValue() {
		return null;
	}

	@Override
	public String toJson() {
		return "null";
	}

	@Override
	public String toJson(Function<Object, String> function) {
		return "null";
	}

	@Override
	public String toString() {
		return toJson();
	}

}

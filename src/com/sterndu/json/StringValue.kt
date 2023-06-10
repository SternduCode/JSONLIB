package com.sterndu.json;

import java.util.function.Function;

public class StringValue implements JsonValue {

	private String value = "";

	public StringValue(String value) {
		this.value = value;
	}

	public String getValue() { return value; }

	public void setValue(String value) { this.value = value; }

	@Override
	public String toJson() {
		return "\"" + value + "\"";
	}

	@Override
	public String toJson(Function<Object, String> function) {
		return "\"" + value + "\"";
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

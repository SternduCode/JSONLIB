package com.sterndu.json;

import java.util.function.Function;

public class IntegerValue implements NumberValue {

	private int value = 0;

	public IntegerValue(int value) {
		this.value = value;
	}

	@Override
	public Number getValue() { return value; }

	public int getValueInt() { return value; }

	public void setValue(int value) { this.value = value; }

	@Override
	public String toJson() {
		return value + "";
	}

	@Override
	public String toJson(Function<Object, String> function) {
		return value + "";
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

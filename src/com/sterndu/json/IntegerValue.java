package com.sterndu.json;

import java.util.function.Function;

public class IntegerValue implements JsonValue {

	private int value = 0;

	public IntegerValue(int value) {
		this.value = value;
	}

	public int getValue() { return value; }

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
	public String toString() {
		return toJson();
	}

}

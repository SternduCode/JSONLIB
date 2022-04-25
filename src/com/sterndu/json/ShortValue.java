package com.sterndu.json;

import java.util.function.Function;

public class ShortValue implements JsonValue {

	private short value = 0;

	public ShortValue(short value) {
		this.value = value;
	}

	public short getValue() { return value; }

	public void setValue(short value) { this.value = value; }

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

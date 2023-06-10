package com.sterndu.json;

import java.util.function.Function;

public class ShortValue implements NumberValue {

	private short value = 0;

	public ShortValue(short value) {
		this.value = value;
	}

	@Override
	public Number getValue() { return value; }

	public short getValueShort() { return value; }

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

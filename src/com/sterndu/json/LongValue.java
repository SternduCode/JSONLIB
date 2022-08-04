package com.sterndu.json;

import java.util.function.Function;

public class LongValue implements NumberValue {

	private long value = 0;

	public LongValue(long value) {
		this.value = value;
	}

	@Override
	public Number getValue() { return value; }

	public long getValueLong() { return value; }

	public void setValue(long value) { this.value = value; }

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

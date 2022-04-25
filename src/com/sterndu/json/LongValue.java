package com.sterndu.json;

import java.util.function.Function;

public class LongValue implements JsonValue {

	private long value = 0;

	public LongValue(long value) {
		this.value = value;
	}

	public long getValue() { return value; }

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
	public String toString() {
		return toJson();
	}

}

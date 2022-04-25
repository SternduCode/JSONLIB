package com.sterndu.json;

import java.util.function.Function;

public class ByteValue implements JsonValue {

	private byte value = 0;

	public ByteValue(byte value) {
		this.value = value;
	}

	public byte getValue() { return value; }

	public void setValue(byte value) { this.value = value; }

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

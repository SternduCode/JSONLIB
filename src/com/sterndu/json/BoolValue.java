package com.sterndu.json;

import java.util.function.Function;

public class BoolValue implements JsonValue {

	private boolean value = false;

	public BoolValue(boolean value) {
		this.value = value;
	}

	public boolean getValue() { return value; }

	public void setValue(boolean value) { this.value = value; }

	@Override
	public String toJson() {
		return "" + value;
	}

	@Override
	public String toJson(Function<Object, String> function) {
		return "" + value;
	}

	@Override
	public String toString() {
		return toJson();
	}

}

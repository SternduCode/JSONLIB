package com.sterndu.json;

import java.util.Locale;
import java.util.function.Function;

public class FloatValue implements JsonValue {

	private float value = 0.0f;

	public FloatValue(float value) {
		this.value = value;
	}

	public float getValue() { return value; }

	public void setValue(float value) { this.value = value; }

	@Override
	public String toJson() {
		return String.format(Locale.US, "%g", value);
	}

	@Override
	public String toJson(Function<Object, String> function) {
		return String.format(Locale.US, "%g", value);
	}

	@Override
	public String toString() {
		return toJson();
	}

}

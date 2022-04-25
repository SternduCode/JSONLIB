package com.sterndu.json;

import java.util.Locale;
import java.util.function.Function;

public class DoubleValue implements JsonValue {

	private double value = 0.0d;

	public DoubleValue(double value) {
		this.value = value;
	}

	public double getValue() { return value; }

	public void setValue(double value) { this.value = value; }

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
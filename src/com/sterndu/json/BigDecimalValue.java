package com.sterndu.json;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.function.Function;

public class BigDecimalValue implements NumberValue {

	private BigDecimal value = BigDecimal.valueOf(0.0d);

	public BigDecimalValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public Number getValue() { return value; }

	public BigDecimal getValueBigDecimal() { return value; }

	public void setValue(BigDecimal value) { this.value = value; }

	@Override
	public String toJson() {
		return String.format(Locale.US, "%g", value);
	}

	@Override
	public String toJson(Function<Object, String> function) {
		return String.format(Locale.US, "%g", value);
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

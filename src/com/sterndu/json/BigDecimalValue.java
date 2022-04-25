package com.sterndu.json;

import java.math.BigDecimal;
import java.util.function.Function;

public class BigDecimalValue implements JsonValue {

	private BigDecimal value = BigDecimal.valueOf(0.0d);

	public BigDecimalValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() { return value; }

	public void setValue(BigDecimal value) { this.value = value; }

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

package com.sterndu.json;

import java.math.BigInteger;
import java.util.function.Function;

public class BigIntegerValue implements JsonValue {

	private BigInteger value = BigInteger.valueOf(0l);

	public BigIntegerValue(BigInteger value) {
		this.value = value;
	}

	public BigInteger getValue() { return value; }

	public void setValue(BigInteger value) { this.value = value; }

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

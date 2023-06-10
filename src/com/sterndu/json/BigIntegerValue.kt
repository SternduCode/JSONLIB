@file:JvmName("BigIntegerValue")
package com.sterndu.json

import java.math.BigInteger

class BigIntegerValue @JvmOverloads constructor(value: BigInteger = BigInteger.valueOf(0L)) : NumberValue {
	private var valueBigInteger = value


	fun getValueBigInteger(): BigInteger {
		return valueBigInteger
	}

	override fun getValue(): Number {
		return valueBigInteger
	}

	fun setValue(value: BigInteger) {
		valueBigInteger = value
	}

	override fun toJson(): String {
		return valueBigInteger.toString() + ""
	}

	override fun toJson(function: (Any?) -> String): String {
		return valueBigInteger.toString() + ""
	}

	override fun toJsonValue(): JsonValue {
		return this
	}

	override fun toJsonValue(function: (Any?) -> String): JsonValue {
		return this
	}

	override fun toString(): String {
		return toJson()
	}
}

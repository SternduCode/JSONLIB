@file:JvmName("BigDecimalValue")
package com.sterndu.json

import java.math.BigDecimal
import java.util.*

class BigDecimalValue @JvmOverloads constructor(value: BigDecimal = BigDecimal.valueOf(0.0)) : NumberValue {

	private var valueBigDecimal = value

	fun getValueBigDecimal(): BigDecimal {
		return valueBigDecimal
	}

	override fun getValue(): Number {
		return valueBigDecimal
	}

	fun setValue(value: BigDecimal) {
		valueBigDecimal = value
	}

	override fun toJson(): String {
		return String.format(Locale.US, "%g", valueBigDecimal)
	}

	override fun toJson(function: (Any?) -> String): String {
		return String.format(Locale.US, "%g", valueBigDecimal)
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

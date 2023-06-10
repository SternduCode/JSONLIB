@file:JvmName("DoubleValue")
package com.sterndu.json

import java.util.*

class DoubleValue @JvmOverloads constructor(value: Double = 0.0) : NumberValue {
	private var valueDouble = value

	fun getValueDouble(): Double {
		return valueDouble
	}

	override fun getValue(): Number {
		return valueDouble
	}

	fun setValue(value: Double) {
		valueDouble = value
	}

	override fun toJson(): String {
		return String.format(Locale.US, "%g", valueDouble)
	}

	override fun toJson(function: Function1<Any?, String>): String {
		return String.format(Locale.US, "%g", valueDouble)
	}

	override fun toJsonValue(): JsonValue {
		return this
	}

	override fun toJsonValue(function: Function1<Any?, String>): JsonValue {
		return this
	}

	override fun toString(): String {
		return toJson()
	}
}

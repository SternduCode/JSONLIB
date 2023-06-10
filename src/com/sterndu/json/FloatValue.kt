@file:JvmName("FloatValue")
package com.sterndu.json

import java.util.*

class FloatValue @JvmOverloads constructor(value: Float = 0.0f) : NumberValue {
	private var valueFloat = value

	fun getValueFloat(): Float {
		return valueFloat
	}

	override fun getValue(): Number {
		return valueFloat
	}

	fun setValue(value: Float) {
		valueFloat = value
	}

	override fun toJson(): String {
		return String.format(Locale.US, "%g", valueFloat)
	}

	override fun toJson(function: (Any?) -> String): String {
		return String.format(Locale.US, "%g", valueFloat)
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

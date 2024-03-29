@file:JvmName("LongValue")
package com.sterndu.json

class LongValue @JvmOverloads constructor(value: Long = 0) : NumberValue {
	var valueLong: Long = value
		private set

	override fun getValue(): Number {
		return valueLong
	}

	fun setValue(value: Long) {
		valueLong = value
	}

	override fun toJson(): String {
		return valueLong.toString() + ""
	}

	override fun toJson(function: (Any?) -> String): String {
		return valueLong.toString() + ""
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

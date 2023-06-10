@file:JvmName("ShortValue")
package com.sterndu.json

class ShortValue @JvmOverloads constructor(value: Short = 0) : NumberValue {

	private var valueShort: Short = value

	fun getValueShort(): Short {
		return valueShort
	}

	override fun getValue(): Number {
		return valueShort
	}

	fun setValue(value: Short) {
		valueShort = value
	}

	override fun toJson(): String {
		return valueShort.toString() + ""
	}

	override fun toJson(function: Function1<Any?, String>): String {
		return valueShort.toString() + ""
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

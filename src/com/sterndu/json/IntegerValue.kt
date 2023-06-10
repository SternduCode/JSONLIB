@file:JvmName("IntegerValue")
package com.sterndu.json

class IntegerValue @JvmOverloads constructor(value: Int = 0) : NumberValue {
	private var valueInt = value

	fun getValueInt(): Int {
		return valueInt
	}

	override fun getValue(): Number {
		return valueInt
	}

	fun setValue(value: Int) {
		valueInt = value
	}

	override fun toJson(): String {
		return valueInt.toString() + ""
	}

	override fun toJson(function: (Any?) -> String): String {
		return valueInt.toString() + ""
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

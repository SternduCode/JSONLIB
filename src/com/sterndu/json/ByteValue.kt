@file:JvmName("ByteValue")
package com.sterndu.json


class ByteValue @JvmOverloads constructor(value: Byte = 0) : NumberValue {

	var valueByte: Byte = value
		private set

	override fun getValue(): Number {
		return valueByte
	}

	fun setValue(value: Byte) {
		valueByte = value
	}

	override fun toJson(): String {
		return valueByte.toString()
	}

	override fun toJson(function: (Any?) -> String): String {
		return valueByte.toString()
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

@file:JvmName("BoolValue")
package com.sterndu.json

class BoolValue @JvmOverloads constructor(var value: Boolean = false) : JsonValue {

	override fun toJson(): String {
		return value.toString()
	}

	override fun toJson(function: (Any?) -> String): String {
		return value.toString()
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

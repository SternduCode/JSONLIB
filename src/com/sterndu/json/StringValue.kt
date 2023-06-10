@file:JvmName("StringValue")
package com.sterndu.json

class StringValue @JvmOverloads constructor(var value: String = "") : JsonValue {

	override fun toJson(): String {
		return "\"$value\""
	}

	override fun toJson(function: Function1<Any?, String>): String {
		return "\"$value\""
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

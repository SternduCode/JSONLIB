@file:JvmName("NullValue")
package com.sterndu.json

class NullValue : JsonValue {
	val value: Any?
		get() = null

	override fun toJson(): String {
		return "null"
	}

	override fun toJson(function: Function1<Any?, String>): String {
		return "null"
	}

	override fun toJsonValue(): JsonValue {
		return this
	}

	override fun toString(): String {
		return toJson()
	}

	override fun toJsonValue(function: Function1<Any?, String>): JsonValue {
		return this
	}
}

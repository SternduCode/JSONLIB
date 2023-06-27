@file:JvmName("JsonValue")
package com.sterndu.json

interface JsonValue {
	fun toJson(): String {
		return toJsonValue().toJson()
	}

	fun toJson(function: (Any?) -> String = { it.toString() }): String {
		return toJsonValue(function).toJson()
	}

	/**
	 *
	 * @return a JsonValue that is not this. Meant for Sub-Classes
	 */
	fun toJsonValue(): JsonValue

	/**
	 *
	 * @return a JsonValue that is not this. Meant for Sub-Classes
	 */
	fun toJsonValue(function: (Any?) -> String = { it.toString() }): JsonValue
}

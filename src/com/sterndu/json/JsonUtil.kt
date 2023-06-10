@file:JvmName("JsonUtil")
package com.sterndu.json

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

@JvmOverloads
fun serialize(obj: Any?, function: (Any?) -> String = { "$it" }): String {
	if (obj == null) return "null"
	return when (obj) {
		is JsonValue -> obj.toJson(function)
		is String -> "\"$obj\"".replace("\n", "\\n").replace("\r", "\\r")
		is BigInteger, is Byte, is Short, is Int, is Long, is Boolean -> "$obj"
		is BigDecimal, is Double, is Float -> String.format(Locale.US, "%g", obj)
		is Collection<*> -> JsonArray(obj).toJson(function)
		is Map<*, *> -> JsonObject(obj.mapKeys { it.toString() }).toJson(function)
		else -> function(obj)
	}
}
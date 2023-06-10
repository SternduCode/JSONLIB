@file:JvmName("JsonObject")
package com.sterndu.json

class JsonObject : HashMap<String?, Any?>, JsonValue {
	constructor() : super()
	constructor(m: Map<String, *>) : super(m)

	override fun toJson(): String {
		return toJson { "\"$it\"" }
	}

	override fun toJson(function: (Any?) -> String): String {
		val sb = StringBuilder()
		sb.append("{\n")
		var b = true
		for ((key, value) in entries) if (value !== this) {
			if (b) b = false else sb.append(",\n")
			sb.append("\"").append(key).append("\" : ")
			sb.append(serialize(value, function))
		}
		return sb.toString().replace("\n", "\n\t") + "\n}"
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

	companion object {
		private const val serialVersionUID = 5544447067100285990L
	}
}

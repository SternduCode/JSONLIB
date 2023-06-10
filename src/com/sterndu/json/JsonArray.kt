@file:JvmName("JsonArray")
package com.sterndu.json

class JsonArray : ArrayList<Any?>, JsonValue {
	constructor() : super()
	constructor(c: Collection<*>) : super(c)

	override fun toJson(): String {
		return toJson { "\"$it\"" }
	}

	override fun toJson(function: (Any?) -> String): String {
		val sb = StringBuilder()
		sb.append("[\n")
		var b = true
		for (o in this) if (o != this) {
			if (b) b = false else sb.append(",\n")
			sb.append(serialize(o, function))
		}
		return sb.toString().replace("\n", "\n\t") + "\n]"
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
		private const val serialVersionUID = 2915477326174591243L
	}
}

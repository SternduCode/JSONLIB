@file:JvmName("NumberValue")
package com.sterndu.json

interface NumberValue : JsonValue {
	fun getValue(): Number
}

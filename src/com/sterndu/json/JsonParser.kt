@file:JvmName("JsonParser")
package com.sterndu.json

import java.io.*
import java.math.BigDecimal
import java.util.*

private const val notACorrectJsonValue = "Not a correct Json-format"

private fun findTypeOf(char: Char): Type? {
	for (t in Type.entries) if (t.isPartOf(char)) return t
	return null
}

private fun readString(`in`: InputStream, dataArray: CharArray): StringValue {
	val sb = StringBuilder()
	var last = 0.toChar()
	while (`in`.available() > 0) {
		val temp = `in`.read().toChar()
		if (temp == '"' && last != '\\') {
			dataArray[0] = `in`.read().toChar()
			return StringValue(sb.toString())
		} else {
			sb.append(temp)
			last = temp
		}
	}
	throw JsonParseException("$notACorrectJsonValue: $sb")
}

private fun readNumber(`in`: InputStream, dataArray: CharArray): NumberValue {
	val char = dataArray[0]
	val sb = StringBuilder()
	sb.append(char)
	while (`in`.available() > 0) {
		val temp = `in`.read().toChar()
		if (Type.Number.isPartOf(temp)) sb.append(temp)
		else {
			dataArray[0] = temp
			break
		}
	}
	return try {
		val bd = BigDecimal(sb.toString())
		val d = bd.toDouble()
		if (bd.compareTo(BigDecimal(d)) == 0) {
			val l = d.toLong()
			val b = l.toByte()
			val s = l.toShort()
			val i = l.toInt()
			val f = d.toFloat()
			when (d) {
				b.toDouble() -> ByteValue(b)
				s.toDouble() -> ShortValue(s)
				i.toDouble() -> IntegerValue(i)
				l.toDouble() -> LongValue(l)
				f.toDouble() -> FloatValue(f)
				else -> DoubleValue(d)
			}
		} else {
			val bi = bd.toBigInteger()
			if (bd.compareTo(BigDecimal(bi)) == 0) BigIntegerValue(bi) else BigDecimalValue(bd)
		}
	} catch (e: NumberFormatException) {
		throw JsonParseException("$notACorrectJsonValue: Not a Number: $sb")
	}
}

private fun readNoType(`in`: InputStream, dataArray: CharArray): JsonValue {
	when (dataArray[0]) {
		'f' -> {
			val data = charArrayOf('a', 'l', 's', 'e')
			data.forEach { char -> if (`in`.read().toChar() != char) throw JsonParseException(notACorrectJsonValue) }
			dataArray[0] = `in`.read().toChar()
			return BoolValue(false)
		}

		't' -> {
			val data = charArrayOf('r', 'u', 'e')
			data.forEach { char -> if (`in`.read().toChar() != char) throw JsonParseException(notACorrectJsonValue) }
			dataArray[0] = `in`.read().toChar()
			return BoolValue(true)
		}

		'n' -> {
			val data = charArrayOf('u', 'l', 'l')
			data.forEach { char -> if (`in`.read().toChar() != char) throw JsonParseException(notACorrectJsonValue) }
			dataArray[0] = `in`.read().toChar()
			return NullValue()
		}

		else -> throw JsonParseException(notACorrectJsonValue)
	}
}

private fun readArray(`in`: InputStream, dataArray: CharArray): JsonArray {
	val ja = JsonArray()
	val charArray = charArrayOf(`in`.read().toChar())
	do {
		while (`in`.available() > 0) if ((charArray[0] == ',')
			or Type.WhiteSpace.isPartOf(charArray[0])) charArray[0] = `in`.read().toChar()
		else break
		if (charArray[0] == ']') break
		ja.add(parse(`in`, charArray))
	} while ((charArray[0] == ',') or Type.WhiteSpace.isPartOf(charArray[0]))
	return if (charArray[0] == ']') {
		dataArray[0] = `in`.read().toChar()
		ja
	} else throw JsonParseException("$notACorrectJsonValue: ${charArray[0]} is not ']' which it should be at the end of JsonArray:\n$ja")
}

private fun readObject(`in`: InputStream, dataArray: CharArray): JsonObject {
	val jo = JsonObject()
	val charArray = charArrayOf(`in`.read().toChar())
	do {
		while (`in`.available() > 0) if ((charArray[0] == ',')
			or Type.WhiteSpace.isPartOf(charArray[0])) charArray[0] = `in`.read().toChar()
		else break
		if (charArray[0] == '}') break
		val name = if (charArray[0] == '"') {
			readString(`in`, dataArray).value
		} else throw JsonParseException(notACorrectJsonValue)
		while (`in`.available() > 0) if ((charArray[0] == ':')
			or Type.WhiteSpace.isPartOf(charArray[0])) charArray[0] = `in`.read().toChar()
		else break
		jo[name] = parse(`in`, charArray)
	} while ((charArray[0] == ',') or Type.WhiteSpace.isPartOf(charArray[0]))
	return if (charArray[0] == '}') {
		dataArray[0] = `in`.read().toChar()
		jo
	} else throw JsonParseException(notACorrectJsonValue)
}

@Throws(JsonParseException::class)
private fun parse(`in`: InputStream, dataArray: CharArray): JsonValue? {
	return try {
		var char = dataArray[0]
		var cur: Type? = findTypeOf(char)

		while (`in`.available() > 0) when (cur) {
			Type.String -> return readString(`in`, dataArray)
			Type.Number -> return readNumber(`in`, dataArray)

			Type.WhiteSpace, null -> {
				if (cur == Type.WhiteSpace) {
					char = `in`.read().toChar()
					cur = findTypeOf(char)
				}

				if (cur == null) readNoType(`in`, dataArray)
				continue
			}

			Type.Array -> return readArray(`in`, dataArray)

			Type.Object -> return readObject(`in`, dataArray)
		}
		throw JsonParseException("$notACorrectJsonValue: $char")
	} catch (e: IOException) {
		e.printStackTrace()
		null
	}
}

@Throws(JsonParseException::class)
fun parse(file: File): JsonValue? {
	return try {
		parse(FileInputStream(file))
	} catch (e: FileNotFoundException) {
		e.printStackTrace()
		null
	}
}

@Throws(JsonParseException::class)
fun parse(inputStream: InputStream): JsonValue? {
	return try {
		parse(inputStream, charArrayOf(inputStream.read().toChar()))
	} catch (e: IOException) {
		e.printStackTrace()
		null
	}
}

@Throws(JsonParseException::class)
fun parse(str: String): JsonValue? {
	return try {
		parse(ByteArrayInputStream(str.toByteArray(charset("UTF-8"))))
	} catch (e: UnsupportedEncodingException) {
		e.printStackTrace()
		null
	}
}

private enum class Type(private vararg val chars: Char) {
	WhiteSpace(' ', '	', '\n', '\r'),
	Number('-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', 'E', 'e'),
	String('"'),
	Array('[', ']'),
	Object('{', '}');

	init {
		Arrays.sort(chars)
	}

	fun isPartOf(c: Char): Boolean {
		return Arrays.binarySearch(chars, c) >= 0
	}
}

@file:JvmName("JsonParser")
package com.sterndu.json

import java.io.*
import java.math.BigDecimal
import java.util.*

@Throws(JsonParseException::class)
private fun parse(`in`: InputStream, dataArray: CharArray): JsonValue? {
	return try {
		var cur: Type? = null
		var char = dataArray[0]
		for (t in Type.values()) if (t.isPartOf(char)) cur = t

		while (`in`.available() > 0) when (cur) {
			Type.String -> {
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
				throw JsonParseException("Not a correct Json-format: $sb")
			}

			Type.Number -> {
				val sb = StringBuilder()
				sb.append(char)
				while (`in`.available() > 0) {
					val temp = `in`.read().toChar()
					if (cur.isPartOf(temp)) sb.append(temp)
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
						if (l.toDouble() == d) {
							val b = l.toByte()
							val s = l.toShort()
							val i = l.toInt()
							when (l) {
								b.toLong() -> ByteValue(b)
								s.toLong() -> ShortValue(s)
								i.toLong() -> IntegerValue(i)
								else -> LongValue(l)
							}
						} else {
							val f = d.toFloat()
							if (f.toDouble() == d) FloatValue(f) else DoubleValue(d)
						}
					} else {
						val bi = bd.toBigInteger()
						if (bd.compareTo(BigDecimal(bi)) == 0) BigIntegerValue(bi) else BigDecimalValue(bd)
					}
				} catch (e: NumberFormatException) {
					throw JsonParseException("Not a correct Json-format: Not a Number: $sb")
				}
			}

			Type.WhiteSpace, null -> {
				if (cur == Type.WhiteSpace) {
					char = `in`.read().toChar()
					cur = null

					for (t in Type.values()) if (t.isPartOf(char)) cur = t
				}

				if (cur == null) when (char) {
					'f' -> {
						val data = charArrayOf('a', 'l', 's', 'e')
						for (i in data.indices) {
							if (`in`.read().toChar() != data[i]) break else if (i == data.size - 1) {
								dataArray[0] = `in`.read().toChar()
								return BoolValue(false)
							}
						}
						throw JsonParseException("Not a correct Json-format")
					}

					't' -> {
						val data = charArrayOf('r', 'u', 'e')
						for (i in data.indices) {
							if (`in`.read().toChar() != data[i]) break else if (i == data.size - 1) {
								dataArray[0] = `in`.read().toChar()
								return BoolValue(true)
							}
						}
						throw JsonParseException("Not a correct Json-format")
					}

					'n' -> {
						val data = charArrayOf('u', 'l', 'l')
						for (i in data.indices) {
							if (`in`.read().toChar() != data[i]) break else if (i == data.size - 1) {
								dataArray[0] = `in`.read().toChar()
								return NullValue()
							}
						}
						throw JsonParseException("Not a correct Json-format")
					}

					else -> throw JsonParseException("Not a correct Json-format")
				}
				continue
			}

			Type.Array -> {
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
				} else throw JsonParseException("Not a correct Json-format")
			}

			Type.Object -> {
				val jo = JsonObject()
				val charArray = charArrayOf(`in`.read().toChar())
				do {
					while (`in`.available() > 0) if ((charArray[0] == ',')
						or Type.WhiteSpace.isPartOf(charArray[0])) charArray[0] = `in`.read().toChar()
					else break
					if (charArray[0] == '}') break
					var name = ""
					if (charArray[0] == '"') {
						val sb = StringBuilder()
						var last = 0.toChar()
						while (`in`.available() > 0) {
							val temp = `in`.read().toChar()
							if (temp == '"' && last != '\\') {
								charArray[0] = `in`.read().toChar()
								name = sb.toString()
								break
							} else {
								sb.append(temp)
								last = temp
							}
						}
					} else throw JsonParseException("Not a correct Json-format")
					while (`in`.available() > 0) if ((charArray[0] == ':')
						or Type.WhiteSpace.isPartOf(charArray[0])) charArray[0] = `in`.read().toChar()
					else break
					jo[name] = parse(`in`, charArray)
				} while ((charArray[0] == ',') or Type.WhiteSpace.isPartOf(charArray[0]))
				return if (charArray[0] == '}') {
					dataArray[0] = `in`.read().toChar()
					jo
				} else throw JsonParseException("Not a correct Json-format")
			}
		}
		throw JsonParseException("Not a correct Json-format: $char")
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

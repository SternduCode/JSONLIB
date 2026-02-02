@file:JvmName("Main")
package com.sterndu.json.test

import com.sterndu.json.*
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.math.BigDecimal
import java.math.BigInteger

fun main() {
	val obj = JsonObject()
	val alp = JsonObject()
	obj["alpha"] = alp
	val arr = JsonArray()
	obj["arr"] = arr
	arr.add(1)
	arr.add(true)
	arr.add("hi")
	alp["d"] = 5
	alp["c"] = true
	alp["h"] = null
	alp["z"] = "wdm"
	println(obj.toJson())
	arr.add(null)
	arr.add(false)
	arr.add(arr)
	arr.add(0.3)
	arr.add(1.0 / 3.0)
	try {
		println(obj.toJson())
		val jv = parse(getStringStream(obj.toJson()))
		println(jv!!.toJson())
		var bi = BigInteger.valueOf(1852805403500234500L)
		bi = bi.pow(6)
		System.out.printf("%e%n", BigDecimal(bi))
	} catch (e: JsonParseException) {
		e.printStackTrace()
	}
}

fun getStringStream(str: String): InputStream = ByteArrayInputStream(str.toByteArray(Charsets.UTF_8))

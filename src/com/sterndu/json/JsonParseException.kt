@file:JvmName("JsonParseException")
package com.sterndu.json

class JsonParseException : Exception {
	constructor(): super()
	constructor(exception: String) : super(exception)

	companion object {
		/**
		 *
		 */
		private const val serialVersionUID = -3716841024531140343L
	}
}

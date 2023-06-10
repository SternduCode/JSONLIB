package com.sterndu.json;

import java.util.function.Function;

public interface JsonValue {
	default String toJson() {
		return toJsonValue().toJson();
	}

	default String toJson(Function<Object, String> function) {
		return toJsonValue(function).toJson();
	}

	/**
	 *
	 * @return a JsonValue that is not this. Meant for Sub-Classes
	 */
	JsonValue toJsonValue();

	/**
	 *
	 * @return a JsonValue that is not this. Meant for Sub-Classes
	 */
	JsonValue toJsonValue(Function<Object, String> function);
}

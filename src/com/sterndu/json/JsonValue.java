package com.sterndu.json;

import java.util.function.Function;

public interface JsonValue {
	String toJson();

	String toJson(Function<Object, String> function);
}

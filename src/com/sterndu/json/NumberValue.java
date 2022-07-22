package com.sterndu.json;

import java.util.function.Function;

public interface NumberValue extends JsonValue {

	Number getValue() ;

	@Override
	String toJson();

	@Override
	String toJson(Function<Object, String> function);

}

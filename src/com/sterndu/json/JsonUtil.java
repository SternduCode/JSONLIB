package com.sterndu.json;

import java.util.*;
import java.util.function.Function;

public class JsonUtil {

	public static String serialize(Object o) {
		return serialize(o, b -> "" + b);
	}

	public static String serialize(Object o, Function<Object, String> function) {
		if (o == null) return "null";
		if (o instanceof JsonValue jv) return jv.toJson(function);
		else if (o instanceof String) return "\"" + o + "\"".replace("\n", "\\n");
		else if (o instanceof Number || o instanceof Boolean) return "" + o;
		else if (o instanceof Collection<?> c) return new JsonArray(c).toJson();
		else if (o instanceof Map<?, ?> m) {
			Map<String, Object> mm = new HashMap<>();
			m.forEach((k, v) -> mm.put(k.toString(), v));
			return new JsonObject(mm).toJson();
		}
		return function.apply(o);
	}

}

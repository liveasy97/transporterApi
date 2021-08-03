package com.springboot.TransporterAPI.Exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

public class AccessDeniedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8003786879400862391L;

	private static <K, V> Map<K, V> toMap(
			Class<K> keyType, Class<V> valueType, String... entries) {
		if (entries.length % 2 == 1)
			throw new IllegalArgumentException("Invalid entries");
		return IntStream.range(0, entries.length / 2).map(i -> i * 2)
				.collect(HashMap::new,
						(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
						Map::putAll);
	}

	public AccessDeniedException(Class<?> clazz, String... searchParamsMap) {
		super(AccessDeniedException.generateMessage(
				clazz.getSimpleName(),
				toMap(String.class, String.class, searchParamsMap)
				));
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return StringUtils.capitalize(entity) +
				" is not accessible by parameters " +
				searchParams;
	}
}

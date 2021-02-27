package de.monx.aoc.util.common;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Directions<T, K> {
	private final Map<T, K> map = new HashMap<>();

	public Directions(Pair<T, K>... dirs) {
		for (var dir : dirs) {
			map.put(dir.first, dir.second);
		}
	}

	public K getDir(T t) {
		return map.get(t);
	}
}

package de.monx.aoc.util.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Pair<T, K> {
	public T first;
	public K second;

	public Pair<T, K> clone() {
		return new Pair<T, K>(first, second);
	}
}

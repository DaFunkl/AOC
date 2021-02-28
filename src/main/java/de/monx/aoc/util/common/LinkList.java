package de.monx.aoc.util.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkList<T> {
	public T value;
	public LinkList<T> prev;
	public LinkList<T> next;

	public LinkList(T val) {
		value = val;
	}

	public LinkList<T> remove() {
		prev.next = next;
		next.prev = prev;
		return prev;
	}
}

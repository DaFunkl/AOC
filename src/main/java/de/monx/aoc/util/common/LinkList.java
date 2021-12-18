package de.monx.aoc.util.common;

import lombok.Data;

@Data
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

	public LinkList<T> add(T e) {
		var ll = new LinkList<T>(e);
		ll.prev = this;
		this.next = ll;
		return ll;
	}

	public LinkList<T> placeBefore(T e) {
		var ll = new LinkList<T>(e);
		this.prev = ll;
		ll.next = this;
		return ll;
	}

	public void append(LinkList<T> o) {
		this.next = o;
		o.prev = this;
	}

	public LinkList<T> getHead() {
		var c = this;
		while (c.next != null) {
			c = c.next;
		}
		return c;
	}

	@Override
	public String toString() {
		return value + "->" + next;
	}
}

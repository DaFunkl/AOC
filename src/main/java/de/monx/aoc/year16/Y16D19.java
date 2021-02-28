package de.monx.aoc.year16;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.LinkList;

public class Y16D19 extends Day {
	int max = 5;// Integer.valueOf(getInputString());

	@Override
	public Object part1() {
		var data = getData(max);
		while (data.value != data.next.value) {
			data.next.remove();
			data = data.next;
		}
		return data.value;
	}

	@Override
	public Object part2() {
		return apr3(max);
	}

	int apr3(int vals) {
		var data = getData(vals);
		int amt = vals;
		var rem = data;
		for (int i = 0; i < amt / 2; i++) {
			rem = rem.next;
		}
		int remIdx = (amt / 2) - 1;
		int dIdx = 0;
		while (data.value != data.next.value) {
			int nRem = (dIdx + (amt / 2)) % amt;

			rem = rem.remove().next;
			amt--;

			data = data.next;
			dIdx = (dIdx + 1) % amt;
		}
		return data.value;
	}

	LinkList<Integer> getData(int amt) {
		LinkList<Integer> start = new LinkList<>(1);
		var current = start;
		for (int i = 2; i <= amt; i++) {
			LinkList<Integer> next = new LinkList<>(i);
			next.prev = current;
			current.next = next;
			current = next;
		}
		start.prev = current;
		current.next = start;
		return start;
	}
}

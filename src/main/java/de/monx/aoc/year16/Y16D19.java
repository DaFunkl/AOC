package de.monx.aoc.year16;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.LinkList;

public class Y16D19 extends Day {
	int max = Integer.valueOf(getInputString());

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
		int remIdx = (amt / 2);
		int dIdx = 0;
		while (data.value != data.next.value) {
			int nRem = (dIdx + (amt / 2)) % amt;
			remIdx = remIdx % amt;
			while (nRem != remIdx) {
				remIdx = (remIdx + 1) % amt;
				rem = rem.next;
			}

			rem = rem.remove().next;
			if (dIdx < remIdx) {
				dIdx++;
			}
			amt--;
			if (dIdx >= amt) {
				dIdx = 0;
			}
			data = data.next;
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

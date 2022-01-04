package de.monx.aoc.year17;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.LinkList;

public class Y17D17 extends Day {
	int stepAmt = Integer.valueOf(getInputString());

	@Override
	public Object part1() {
		int size = 1;
		LinkList<Integer> ll = new LinkList<>(0);
		ll.next = ll;
		ll.prev = ll;
		for (int i = 1; i <= 2017; i++) {
			for (int s = 0; s < stepAmt % size; s++) {
				ll = ll.next;
			}
			ll = ll.insert(i);
			size++;
		}
		return ll.next.value;
	}

	@Override
	public Object part2() {
		int size = 1;
		LinkList<Integer> ll = new LinkList<>(0);
		ll.next = ll;
		ll.prev = ll;
		for (int i = 1; i <= 50_000_000; i++) {
			for (int s = 0; s < stepAmt % size; s++) {
				ll = ll.next;
			}
			ll = ll.insert(i);
			size++;
			if (i % 1_000_000 == 0) {
				System.out.println("Step: " + i);
			}
		}
		while (ll.value != 0) {
			ll = ll.next;
		}
		return ll.next.value;
	}

}

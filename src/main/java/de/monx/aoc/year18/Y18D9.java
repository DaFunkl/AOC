package de.monx.aoc.year18;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.LinkList;

public class Y18D9 extends Day {

//	412 players; last marble is worth 71646 points
	String[] sarIn = getInputString().split(" ");
	int[] in = { Integer.valueOf(sarIn[0]), Integer.valueOf(sarIn[6]) };

	@Override
	public Object part1() {
		LinkList<Integer> marbles = new LinkList<>(0);
		marbles = marbles.add(1);
		marbles.prev.prev = marbles;
		marbles.next = marbles.prev;
		int ret = 0;
		int[] scores = new int[in[0]];
		for (int i = 2; i <= in[1]; i++) {
			if (i % 23 == 0) {
				int pidx = i % in[0];
				scores[pidx] += i;
				for (int j = 0; j < 7; j++) {
					marbles = marbles.prev;
				}
				scores[pidx] += marbles.value;
				ret = Math.max(ret, scores[pidx]);
				marbles = marbles.remove().next;
			} else {
				marbles = marbles.next;
				marbles = marbles.insert(i);
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		LinkList<Integer> marbles = new LinkList<>(0);
		marbles = marbles.add(1);
		marbles.prev.prev = marbles;
		marbles.next = marbles.prev;
		long ret = 0;
		long[] scores = new long[in[0]];
		int amt = in[1] * 100;
		for (int i = 2; i <= amt; i++) {
			if (i % 23 == 0) {
				int pidx = i % in[0];
				scores[pidx] += i;
				for (int j = 0; j < 7; j++) {
					marbles = marbles.prev;
				}
				scores[pidx] += marbles.value;
				ret = Math.max(ret, scores[pidx]);
				marbles = marbles.remove().next;
			} else {
				marbles = marbles.next;
				marbles = marbles.insert(i);
			}
		}
		return ret;
	}

}

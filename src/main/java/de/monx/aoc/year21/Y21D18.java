package de.monx.aoc.year21;

import java.util.List;
import java.util.Stack;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y21D18 extends Day {
	static final int _BO = -1;
	static final int _BC = -2;
	static final int _SE = -3;

	List<String> in = getInputList();

	@Override
	public Object part1() {
		Sll current = parseLine(in.get(0));
		for (int i = 1; i < in.size(); i++) {
			var next = parseLine(in.get(i));
			current = addition(current, next);
		}
		return calcMagnitude(current);
	}

	@Override
	public Object part2() {
		long max = 0;
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.size(); j++) {
				if (i == j) {
					continue;
				}
				var a = parseLine(in.get(i));
				var b = parseLine(in.get(j));
				max = Math.max(max, calcMagnitude(addition(a, b)));
			}
		}
		return max;
	}

	long calcMagnitude(Sll snail) {
		Stack<Pair<Long, Integer>> stack = new Stack<>();
		int lvl = 0;
		while (snail != null) {
			int cv = 0;
			switch (snail.value) { //@formatter:off
				case _BO: lvl++; snail = snail.next; continue;
				case _BC: lvl--; snail = snail.next; continue; 
				default: cv = snail.value; break;
			}//@formatter:on
			Pair<Long, Integer> p = new Pair<Long, Integer>((long) cv, lvl);
			while (!stack.isEmpty() && stack.peek().second == p.second) {
				var calc = stack.pop();
				p.second--;
				p.first = (calc.first * 3l) + (p.first * 2l);
			}
			stack.push(p);
			snail = snail.next;
		}
		return stack.pop().first;
	}

	public Sll addition(Sll a, Sll b) {
		b.getHead().add(_BC);
		a.getHead().append(b);
		a = a.placeBefore(_BO);

		boolean running = reduce(a, false);
		while (running) {
			running = reduce(a, false);
			if (!running) {
				running = reduce(a, true);
			}
		}
		return a;
	}

	boolean reduce(Sll node, boolean allowSplit) {
		int lvl = 0;
		while (node != null) {
			int cv = 0;
			switch (node.value) { //@formatter:off
				case _BO: lvl++; node = node.next; continue;
				case _BC: lvl--; node = node.next; continue; 
				default: cv = node.value; break;
			}//@formatter:on
			if (lvl == 5) { // explode
				// fetch Explode Values
				int l = node.value;
				int r = node.next.value;

				// set prev zo zero (exploding node)
				var zn = node.prev;
				zn.value = 0;

				// close explosion gap
				zn.append(node.next.next.next);

				// add explosion to left
				var ln = zn.prev;
				while (ln != null && ln.value < 0) {
					ln = ln.prev;
				}
				if (ln != null) {
					ln.value += l;
				}

				// add explosion to right
				var rn = zn.next;
				while (rn != null && rn.value < 0) {
					rn = rn.next;
				}
				if (rn != null) {
					rn.value += r;
				}

				return true;
			} else if (cv > 9 && allowSplit) { // split
				var spl = splitter(cv);
				var nn = node.next;
				node.value = _BO;
				node.add(spl[0]).add(spl[1]).add(_BC).append(nn);
				return true;
			} else {
				node = node.next;
			}
		}
		return false;
	}

	int[] splitter(int num) {
		return new int[] { //
				num / 2, //
				(num / 2) + (num % 2 == 0 ? 0 : 1) //
		};
	}

	Sll parseLine(String s) {
		Sll ret = null;
		Sll cur = null;
		for (char c : s.toCharArray()) {
			int ni = switch (c) {
			case '[' -> _BO;
			case ']' -> _BC;
			case ',' -> _SE;
			default -> Character.getNumericValue(c);
			};
			if (ni == _SE) {
				continue;
			}
			if (cur == null) {
				ret = new Sll(ni);
				cur = ret;
			} else {
				cur = cur.add(ni);
			}
		}
		return ret;
	}

	static class Sll {
		public int value;
		public Sll prev;
		public Sll next;

		public Sll(Integer val) {
			value = val;
		}

		public Sll placeBefore(int e) {
			var ll = new Sll(e);
			this.prev = ll;
			ll.next = this;
			return ll;
		}

		public void append(Sll o) {
			this.next = o;
			o.prev = this;
		}

		public Sll add(int e) {
			var ll = new Sll(e);
			ll.prev = this;
			this.next = ll;
			return ll;
		}

		public Sll getHead() {
			var c = this;
			while (c.next != null) {
				c = c.next;
			}
			return c;
		}

		@Override
		public String toString() {
			return switch (value) {
			case _BO -> "[";
			case _BC -> "]";
			default -> " " + value + " ";
			} + (next == null ? "" : next);
		}
	}

}

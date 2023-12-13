package de.monx.aoc.year23;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_23_10;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y23D10 extends Day {
	List<String> in = getInputList();
	IntPair start = null;
	IntPair loopEnd = null;
	Map<IntPair, Integer> eType = new HashMap<>();

	static final IntPair[] _DIRS = { //
			new IntPair(-1, 0), // N
			new IntPair(0, 1), // E
			new IntPair(1, 0), // S
			new IntPair(0, -1), // W
	};

	static final IntPair Z = new IntPair(0, 0);
	Map<IntPair, Integer> weights = new HashMap<>();

	static Map<Character, IntPair[]> MAP;
	static {
		Map<Character, IntPair[]> m = new HashMap<>();
		m.put('|', new IntPair[] { _DIRS[0], _DIRS[2] });
		m.put('-', new IntPair[] { _DIRS[1], _DIRS[3] });
		m.put('L', new IntPair[] { _DIRS[0], _DIRS[1] });
		m.put('J', new IntPair[] { _DIRS[0], _DIRS[3] });
		m.put('7', new IntPair[] { _DIRS[2], _DIRS[3] });
		m.put('F', new IntPair[] { _DIRS[2], _DIRS[1] });
		m.put('X', new IntPair[] { Z, Z });
		m.put('.', new IntPair[] { Z, Z });
		m.put('S', new IntPair[] { Z, Z });
		MAP = Collections.unmodifiableMap(m);
	}
	static Map<Character, IntPair[]> EO;
	static {
		Map<Character, IntPair[]> m = new HashMap<>();
		m.put('|', new IntPair[] { _DIRS[3], _DIRS[1] });
		m.put('-', new IntPair[] { _DIRS[0], _DIRS[2] });
		m.put('L', new IntPair[] { _DIRS[3], _DIRS[2] });
		m.put('J', new IntPair[] { _DIRS[1], _DIRS[2] });
		m.put('7', new IntPair[] { _DIRS[0], _DIRS[1] });
		m.put('F', new IntPair[] { _DIRS[0], _DIRS[3] });
		EO = Collections.unmodifiableMap(m);
	}

	boolean isAnim = true;
	Animation anim = null;
	long sleep = 1;

	void drawAnim(long sleep, Map<IntPair, Integer> enclosed, int eon) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1100, 1100, new DP_23_10());
			((DP_23_10) anim.pane).update(sleep, in, weights, eType, enclosed, eon);
		}
		((DP_23_10) anim.pane).update(sleep, in, weights, eType, enclosed, eon);
	}

	@Override
	public Object part1() {
		findStart();
		weights.put(start, 0);
		ArrayDeque<Pair<IntPair, int[]>> stack = new ArrayDeque<>();
		List<IntPair> sad = new ArrayList<>();
		for (var d : _DIRS) {
			for (var od : MAP.get(getChar(start.add(d)))) {
				if (start.equals(start.add(d).add(od))) {
					sad.add(d);
				}
			}
		}
		char sTile = '.';
		for (var k : MAP.keySet()) {
			var mv = MAP.get(k);
			if ((mv[0].equals(sad.get(0)) && mv[1].equals(sad.get(1)))
					|| (mv[0].equals(sad.get(1)) && mv[1].equals(sad.get(0)))) {
				sTile = k;
			}
		}
		int[] dm = detMark(sTile, getChar(start.add(sad.get(0))), 0);
		stack.push(new Pair<IntPair, int[]>(start.add(sad.get(0)), new int[] { 1, dm[0], dm[1] }));
		dm = detMark(sTile, getChar(start.add(sad.get(1))), 0);
		stack.push(new Pair<IntPair, int[]>(start.add(sad.get(1)), new int[] { 1, dm[0], dm[1] }));

		int cc = 0;
		drawAnim(sleep, null, -2);
		Util.readLine();
		while (!stack.isEmpty()) {
			if (cc++ % 10 == 0) {
				drawAnim(sleep, null, -2);
			}
			var cur = stack.pollLast();
			if (weights.containsKey(cur.first) && cur.second[0] >= weights.get(cur.first)) {
				continue;
			}
			weights.put(cur.first, cur.second[0]);
			char pc = getChar(cur.first);
			var eo = EO.get(pc);
			eType.put(cur.first.add(eo[0]), cur.second[1]);
			eType.put(cur.first.add(eo[1]), cur.second[2]);
			for (var d : MAP.get(getChar(cur.first))) {
				IntPair np = d.add(cur.first);
				char nc = getChar(np);
				if (getChar(np) == '.' || getChar(np) == 'S') {
					continue;
				}
				int[] dmt = detMark(pc, nc, cur.second[1]);
				stack.push(new Pair<IntPair, int[]>(np, new int[] { cur.second[0] + 1, dmt[0], dmt[1] }));
			}
		}
		int max = 0;
		for (var k : weights.keySet()) {
			if (weights.get(k) > max) {
				max = weights.get(k);
				loopEnd = k;
			}
		}
//		printEO();
//		System.out.println();
		drawAnim(sleep * 3, null, -2);
		return max;
	}

	@Override
	public Object part2() {
		Map<IntPair, Integer> enclosed = new HashMap<>();
		IntPair cp = new IntPair(0, 0);
		int beo = -1;
		for (cp.first = 0; cp.first < in.size(); cp.addi(1, 0)) {
//			drawAnim(sleep, enclosed, -2);
			for (cp.second = 0; cp.second < in.get(0).length(); cp.addi(0, 1)) {
				if (enclosed.containsKey(cp) || weights.containsKey(cp)) {
					continue;
				}
				boolean isEnclosed = true;
				ArrayDeque<IntPair> stack = new ArrayDeque<>();
				stack.push(cp.clone());
				Set<IntPair> set = new HashSet<>();
				int eo = -1;
				while (!stack.isEmpty()) {
					var cc = stack.pop();
					if (eType.containsKey(cc)) {
						eo = eType.get(cc);
					}
					if (set.contains(cc)) {
						continue;
					}
					set.add(cc);
					for (var d : _DIRS) {
						var np = cc.add(d);
						var cs = getChar(np);
						if (cs == 'X') {
							isEnclosed = false;
						} else if (!weights.containsKey(np)) {
							stack.add(np);
						}
					}
				}
				if (!isEnclosed) {
					if (eo != -1) {
						beo = eo;
					}
				}
				int cc = 0;
				for (var s : set) {
					enclosed.put(s, eo);
					if (cc++ % 10 == 0) {
						drawAnim(10, enclosed, -2);
					}
				}
				drawAnim(sleep, enclosed, beo);
			}
		}
		drawAnim(sleep, enclosed, beo);

//		print(enclosed);
		final int neo = beo;
//		System.out.println("-1 -> " + enclosed.values().stream().filter(x -> x != 0 && x != 1).count());
//		System.out.println(" 0 -> " + enclosed.values().stream().filter(x -> x != -1 && x != 1).count());
//		System.out.println(" 1 -> " + enclosed.values().stream().filter(x -> x != -1 && x != 0).count());
		return enclosed.values().stream().filter(x -> x != -1 && x != neo).count();
	}

	int[] detMark(char p, char n, int t1) {
		int t2 = (t1 + 1) % 2;
		return switch (p) {
		case '|' -> switch (n) {
		case 'L' -> new int[] { t1, t1 }; //
		case 'J' -> new int[] { t2, t2 }; // IntPair(t1, t1);
		case '7' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case 'F' -> new int[] { t1, t1 }; // IntPair(t2, t2);
		case '|' -> new int[] { t1, t2 }; // IntPair(t1, t2);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};

		case '-' -> switch (n) {
		case 'L' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case 'J' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case '7' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case 'F' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case '-' -> new int[] { t1, t2 }; // IntPair(t2, t1);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};

		case 'L' -> switch (n) {
		case 'J' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case '7' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case 'F' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case '-' -> new int[] { t2, t1 }; // IntPair(t2, t1);
		case '|' -> new int[] { t1, t2 }; // IntPair(t1, t2);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};

		case 'J' -> switch (n) {
		case 'L' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case '7' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case 'F' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case '-' -> new int[] { t2, t1 }; // IntPair(t2, t1);
		case '|' -> new int[] { t2, t1 }; // IntPair(t2, t1);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};

		case '7' -> switch (n) {
		case 'L' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case 'J' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case 'F' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case '-' -> new int[] { t1, t2 }; // IntPair(t1, t2);
		case '|' -> new int[] { t2, t1 }; // IntPair(t2, t1);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};

		case 'F' -> switch (n) {
		case 'L' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case 'J' -> new int[] { t2, t2 }; // IntPair(t2, t2);
		case '7' -> new int[] { t1, t1 }; // IntPair(t1, t1);
		case '-' -> new int[] { t1, t2 }; // IntPair(t1, t2);
		case '|' -> new int[] { t1, t2 }; // IntPair(t1, t2);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};

		default -> throw new IllegalArgumentException("Unexpected value: " + p);
		};
	}

	void printEO() {
		char[] signs = { 'O', 'X', 'Y' };
		IntPair cp = new IntPair(0, 0);
		for (cp.first = 0; cp.first < in.size(); cp.addi(1, 0)) {
			StringBuilder sb = new StringBuilder();
			for (cp.second = 0; cp.second < in.get(0).length(); cp.addi(0, 1)) {
				if (weights.containsKey(cp)) {
					sb.append(in.get(cp.first).charAt(cp.second));
				} else if (eType.containsKey(cp)) {
					sb.append(signs[1 + eType.get(cp)]);
				} else {
					sb.append(getChar(cp));
				}
			}
			System.out.println(sb.toString());
		}
	}

	void print(Map<IntPair, Integer> enc) {
		char[] signs = { 'O', 'X', 'Y' };
		IntPair cp = new IntPair(0, 0);
		for (cp.first = 0; cp.first < in.size(); cp.addi(1, 0)) {
			StringBuilder sb = new StringBuilder();
			for (cp.second = 0; cp.second < in.get(0).length(); cp.addi(0, 1)) {
				if (weights.containsKey(cp)) {
					sb.append(in.get(cp.first).charAt(cp.second));
				} else if (enc != null && enc.containsKey(cp)) {
					sb.append(signs[1 + enc.get(cp)]);
				} else {
					sb.append(getChar(cp));
				}
			}
			System.out.println(sb.toString());
		}
	}

	char getChar(IntPair ip) {
		if (ip.first < 0 || ip.first >= in.size() || ip.second < 0 || ip.second >= in.get(0).length()) {
			return 'X';
		}
		return in.get(ip.first).charAt(ip.second);
	}

	void findStart() {
		for (int i = 0; i < in.size(); i++) {
			var l = in.get(i);
			for (int j = 0; j < l.length(); j++) {
				if (l.charAt(j) == 'S') {
					start = new IntPair(i, j);
					return;
				}
			}
		}

	}
}

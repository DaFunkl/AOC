package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D21 extends Day {

	List<String> in = getInputList();
	static Map<Character, IntPair> pad1 = new HashMap<>();
	static Map<Character, IntPair> pad2 = new HashMap<>();
	static {
		Map<Character, IntPair> aMap = new HashMap<>();
		aMap.put('7', new IntPair(0, 0));
		aMap.put('8', new IntPair(0, 1));
		aMap.put('9', new IntPair(0, 2));
		aMap.put('4', new IntPair(1, 0));
		aMap.put('5', new IntPair(1, 1));
		aMap.put('6', new IntPair(1, 2));
		aMap.put('1', new IntPair(2, 0));
		aMap.put('2', new IntPair(2, 1));
		aMap.put('3', new IntPair(2, 2));
		aMap.put('0', new IntPair(3, 1));
		aMap.put('A', new IntPair(3, 2));
		pad1 = aMap;

		Map<Character, IntPair> bMap = new HashMap<>();
		bMap.put('^', new IntPair(0, 1));
		bMap.put('A', new IntPair(0, 2));
		bMap.put('<', new IntPair(1, 0));
		bMap.put('v', new IntPair(1, 1));
		bMap.put('>', new IntPair(1, 2));
		pad2 = bMap;
	}

	static final IntPair[] ForbiddenCell = { new IntPair(3, 0), new IntPair(0, 0) };

	@Override
	public Object part1() {
		int ret = 0;
		for (var str : in) {
			String[] best = null;
			ArrayDeque<Pair<String[][], char[]>> stack = new ArrayDeque<>();
			stack.push(new Pair<>(new String[][] { { "", "", "" }, { str, "", "" } }, new char[] { 'A', 'A', 'A' }));
			while (!stack.isEmpty()) {
				var cur = stack.pollLast();
				var sarr = cur.first[0];
				var tarr = cur.first[1];
				var carr = cur.second;
				System.out.println(Arrays.toString(sarr));
				System.out.println(Arrays.toString(tarr));
				System.out.println(Arrays.toString(carr));
				System.out.println();

				var tidx = tarr.length - 1;
				while (tidx >= 0 && tarr[tidx].isBlank()) {
					tidx--;
				}
				if (tidx < 0) {
					if (best == null || sarr[2].length() < best[2].length()) {
						best = sarr;
						System.out.println("new Best: " + Arrays.toString(sarr));
					}
					continue;
				}

				var nxc = tarr[tidx].charAt(0);
				var rst = tarr[tidx].substring(1);
				var mvs = toMoves(carr[tidx], nxc, tidx > 0 ? 1 : 0);

				for (var mov : mvs) {
					if (mov == null) {
						continue;
					}
					String[][] sar = { Arrays.copyOf(sarr, sarr.length), Arrays.copyOf(tarr, tarr.length) };
					sar[0][tidx] += mov;
					sar[1][tidx] = rst;
					if ((tidx + 1) < tarr.length) {
						sar[1][tidx + 1] = mov;
					}
					char[] car = Arrays.copyOf(carr, carr.length);
					car[tidx] = nxc;
					stack.push(new Pair<>(sar, car));
				}
			}
			int codeVal = Integer.valueOf(str.substring(0, str.length() - 1));
			System.out.println(str + " | " + codeVal + " * " + best[2].length() + " = " + (codeVal * best[2].length())
					+ Arrays.toString(best));
			ret += codeVal * best[2].length();
			break;
		}
		return ret;
	}

	@Override
	public Object part2() {
		return null;
	}

	Map<Integer, String[]> moveMap = new HashMap<>();

	String[] toMoves(char fromChar, char toChar, int fixd) {

		int reqKey = (((fromChar << 4) + toChar) << 2) + fixd;
//		String reqKey = "" + fromChar + toChar + fixd;
		if (moveMap.containsKey(reqKey)) {
			return moveMap.get(reqKey);
		}

		IntPair from1 = fixd == 0 ? pad1.get(fromChar) : pad2.get(fromChar);
		IntPair from2 = from1.clone();
		IntPair to = fixd == 0 ? pad1.get(toChar) : pad2.get(toChar);
		String r1 = "";
		String r2 = "";
		IntPair adders = new IntPair(from1.first < to.first ? 1 : -1, from1.second < to.second ? 1 : -1);

		// from 1 starts moving on y axis first
		while (from1.first != to.first) {
			r1 += adders.first < 0 ? '^' : 'v';
			from1.first += adders.first;
			if (from1.equals(ForbiddenCell[fixd])) {
				r1 = null;
				break;
			}
		}
		while (r1 != null && from1.second != to.second) {
			r1 += adders.second < 0 ? '<' : '>';
			from1.second += adders.second;
			if (from1.equals(ForbiddenCell[fixd])) {
				r1 = null;
				break;
			}
		}

		// from 2 starts moving on x axis first
		while (from2.second != to.second) {
			r2 += adders.second < 0 ? '<' : '>';
			from2.second += adders.second;
			if (from2.equals(ForbiddenCell[fixd])) {
				r2 = null;
				break;
			}
		}
		while (r2 != null && from2.first != to.first) {
			r2 += adders.first < 0 ? '^' : 'v';
			from2.first += adders.first;
			if (from2.equals(ForbiddenCell[fixd])) {
				r2 = null;
				break;
			}
		}
		if (r1 != null && r2 != null && r1.equals(r2)) {
			r2 = null;
		}

		moveMap.put(reqKey, new String[] { r1 == null ? r1 : r1 + "A", r2 == null ? r2 : r2 + "A" });
		return moveMap.get(reqKey);
	}
}

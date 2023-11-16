package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y18D13 extends Day {

	static final boolean _PRINT = false;

	static final int _U = 0; // ^
	static final int _R = 1; // >
	static final int _D = 2; // v
	static final int _L = 3; // <

	static final int[][] _DIR = { //
			{ -1, 00 }, // Up
			{ 00, +1 }, // Right
			{ +1, 00 }, // Down
			{ 00, -1 }, // Left
	};

	static final char[] _DC = { '^', '>', 'v', '<' };

	List<String> in = getInputList();
	Map<String, int[]> carts = init();
	String p1 = null;
	String p2 = null;

	@Override
	public Object part1() {
		solve();
		return p1;
	}

	@Override
	public Object part2() {
		return p2;
	}

	void solve() {
		print();
		while (carts.size() > 1) {
			for (var k : order()) {
				var ca = carts.get(k);
				if (ca == null) {
					continue;
				}
				carts.remove(k);
				ca[0] += _DIR[ca[2]][0];
				ca[1] += _DIR[ca[2]][1];
				var c = in.get(ca[0]).charAt(ca[1]);
				var nk = ca[0] + "," + ca[1];
				if (carts.containsKey(nk)) {
					if (p1 == null) {
						p1 = ca[1] + "," + ca[0];
					}
					carts.remove(nk);
					continue;
				}
				if (c == '\\') {
					ca[2] = switch (ca[2]) {
					case _U -> _L;
					case _R -> _D;
					case _D -> _R;
					case _L -> _U;
					default -> -1;
					};
				}
				if (c == '/') {
					ca[2] = switch (ca[2]) {
					case _U -> _R;
					case _R -> _U;
					case _D -> _L;
					case _L -> _D;
					default -> -1;
					};
				}
				if (c == '+') {
					ca[2] = (ca[2] + ca[3] + 3) % 4;
					ca[3] = (ca[3] + 1) % 3;
				}
				carts.put(nk, ca);
			}
			print();
		}

		for (var v : carts.values()) {
			p2 = v[1] + "," + v[0];
			break;
		}
	}

	List<String> order() {
		List<String> ret = new ArrayList<>();
		for (var k : carts.keySet()) {
			ret.add(k);
		}
		for (int i = 0; i < ret.size(); i++) {
			for (int j = i + 1; j < ret.size(); j++) {
				var ci = carts.get(ret.get(i));
				var cj = carts.get(ret.get(j));
				if (cj[0] < ci[0] || (cj[0] == ci[0] && cj[1] < ci[1])) {
					var t = ret.get(i);
					ret.set(i, ret.get(j));
					ret.set(j, t);
				}
			}
		}
		return ret;
	}

	Map<String, int[]> init() {
		Map<String, int[]> ret = new HashMap<>();
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(i).length(); j++) {
				int dir = switch (in.get(i).charAt(j)) {
				case '^' -> _U;
				case '>' -> _R;
				case 'v' -> _D;
				case '<' -> _L;
				default -> -1;
				};
				if (dir == -1) {
					continue;
				}
				ret.put(i + "," + j, new int[] { i, j, dir, 0 });
				var s = in.get(i).toCharArray();
				s[j] = dir == _U || dir == _D ? '|' : '-';
				in.set(i, new String(s));
			}
		}
		return ret;
	}

	void print() {
		if (!_PRINT) {
			return;
		}
		for (int i = 0; i < in.size(); i++) {
			StringBuilder sb = new StringBuilder();
			char[] sar = in.get(i).toCharArray();
			for (int j = 0; j < sar.length; j++) {
				var k = i + "," + j;
				if (carts.containsKey(k)) {
					sb.append(_DC[carts.get(k)[2]]);
				} else {
					sb.append(sar[j]);
				}
			}
			System.out.println(sb.toString());
		}
		System.out.println();
		Util.readLine();
	}
}

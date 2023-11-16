package de.monx.aoc.year18;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y18D15 extends Day {
	GS initialState = new GS(getInputList());

	@Override
	public Object part1() {
		return new GS(initialState, 3).run();
	}

	@Override
	public Object part2() {
		IntPair ret = new IntPair(-1, -1);
		int atk = 3;
		int eamt = initialState.ec;
		int namt = -1;
		while (ret.first != GS._E || eamt != namt) {
			GS gs = new GS(initialState, ++atk);
			ret = gs.run();
			namt = gs.ec;
		}
		return ret;
	}

	static class GS {
		static final int _E = 0;
		static final int _G = 1;
		static final IntPair[] _DIR = { //
				new IntPair(-1, 00), // U
				new IntPair(00, -1), // L
				new IntPair(00, 01), // R
				new IntPair(01, 00), // D
		};
		List<String> grid = null;
		List<IntPair> units = new ArrayList<>();
		Map<IntPair, int[]> stats = new HashMap<>();
		int gc = 0;
		int ec = 0;
		int winner = -1;
		int score = 0;
		int gatk = 3;
		int eatk = 3;

		public GS(List<String> in) {
			grid = in;
			for (int i = 0; i < grid.size(); i++) {
				for (int j = 0; j < grid.get(i).length(); j++) {
					if (grid.get(i).charAt(j) == 'G') {
						var ip = new IntPair(i, j);
						units.add(ip);
						stats.put(ip, new int[] { _G, 200 });
						gc++;
					} else if (grid.get(i).charAt(j) == 'E') {
						var ip = new IntPair(i, j);
						units.add(ip);
						stats.put(ip, new int[] { _E, 200 });
						ec++;
					}
				}
			}
		}

		public GS(GS o, int eatk) {
			this.eatk = eatk;
			grid = o.grid;
			o.units.forEach(x -> units.add(x.clone()));
			o.stats.entrySet().forEach(x -> stats.put( //
					x.getKey().clone(), //
					new int[] { x.getValue()[0], x.getValue()[1] }) //
			);
			gc = o.gc;
			ec = o.ec;
		}

		IntPair run() {
			int round = 0;
			while (ec > 0 && gc > 0) {
				Collections.sort(units, ipComp);
				// Loop of Units
				for (int i = 0; i < units.size(); i++) {
					var ip = units.get(i);
					var st = stats.get(ip);
					// is something in range?
					var ir = inRange(ip, st[0]);
					// no? -> move
					if (ir.isEmpty()) {
						var nip = move(ip, st[0]);
						if (nip == null) {
							continue;
						}
						stats.put(nip, st);
						stats.remove(ip);
						units.set(i, nip);
						ip = nip;
						ir = inRange(ip, st[0]);
					}
					if (ir.isEmpty()) {
						continue;
					}
					Collections.sort(ir, atkComp);
					var tar = ir.get(0);
					int tra = stats.get(tar)[0];
					stats.get(tar)[1] -= tra != _E ? eatk : gatk;
					if (stats.get(tar)[1] <= 0) {
						if (tra == _E) {
							ec--;
						} else {
							gc--;
						}
						stats.remove(tar);
						for (int j = 0; j < units.size(); j++) {
							if (units.get(j).equals(tar)) {
								units.remove(j);
								if (j <= i) {
									i--;
								}
							}
						}
					}
				}
				round++;
			}
			if (ec == 0) {
				winner = _G;
			} else {
				winner = _E;
			}
			score = units.stream().map(x -> stats.get(x)[1]).mapToInt(i -> i).sum();
			return new IntPair(winner, score * (round - 1));
		}

		List<IntPair> inRange(IntPair ip, int race) {
			List<IntPair> ret = new ArrayList<>();
			for (var d : _DIR) {
				var nip = ip.add(d);
				if (stats.containsKey(nip) && stats.get(nip)[0] != race) {
					ret.add(nip);
				}
			}
			return ret;
		}

		IntPair move(IntPair ip, int race) {
			Map<IntPair, Integer> weight = new HashMap<>();
			ArrayDeque<Pair<IntPair, IntPair>> stack = new ArrayDeque<>();
			int minSteps = Integer.MAX_VALUE;
			List<Pair<IntPair, IntPair>> vals = new ArrayList<>();

			stack.push(new Pair<IntPair, IntPair>(ip, new IntPair(0, -1)));
			while (!stack.isEmpty()) {
				var cur = stack.pollLast();
				var cip = cur.first;
				var cst = cur.second;
				if (cst.first >= minSteps || (weight.containsKey(cip) && weight.get(cip) <= cst.first)) {
					continue;
				}
				weight.put(cip, cst.first);
				for (int i = 0; i < _DIR.length; i++) {
					var nip = _DIR[i].add(cip);
					var nst = new IntPair(cst.first + 1, cst.second < 0 ? i : cst.second);

					if (stats.containsKey(nip)) {
						if (stats.get(nip)[0] == race) {
							continue;
						}
						minSteps = nst.first;
						vals.add(new Pair<IntPair, IntPair>(cip, nst));
						continue;
					}
					if (nip.first < 0 || nip.first >= grid.size() || nip.second < 0
							|| nip.second >= grid.get(i).length() || grid.get(nip.first).charAt(nip.second) == '#') {
						continue;
					}
					stack.push(new Pair<IntPair, IntPair>(nip, nst));
				}
			}
			if (vals.isEmpty()) {
				return null;
			}
			Collections.sort(vals, mipComp);
			return _DIR[vals.get(0).second.second].add(ip);
		}

		Comparator<IntPair> atkComp = new Comparator<>() {
			@Override
			public int compare(IntPair o1, IntPair o2) {
				if (stats.get(o1)[1] != stats.get(o2)[1]) {
					return stats.get(o1)[1] - stats.get(o2)[1];
				}
				if (o1.first != o2.first) {
					return o1.first - o2.first;
				}
				return o1.second - o2.second;
			}
		};

		Comparator<IntPair> ipComp = new Comparator<>() {
			@Override
			public int compare(IntPair o1, IntPair o2) {
				if (o1.first != o2.first) {
					return o1.first - o2.first;
				}
				return o1.second - o2.second;
			}
		};

		Comparator<Pair<IntPair, IntPair>> mipComp = new Comparator<>() {
			@Override
			public int compare(Pair<IntPair, IntPair> op1, Pair<IntPair, IntPair> op2) {
				var o1 = op1.first;
				var o2 = op2.first;
				if (o1.first != o2.first) {
					return o1.first - o2.first;
				}
				return o1.second - o2.second;
			}
		};

		String[] EG = { "E", "G" };

		void print() {
			System.out.println("\n--------------------------\n");
			for (int i = 0; i < grid.size(); i++) {
				String str = grid.get(i);
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < str.length(); j++) {
					IntPair ip = new IntPair(i, j);
					if (stats.containsKey(ip)) {
						sb.append(EG[stats.get(ip)[0]]);
					} else if (str.charAt(j) == 'E' || str.charAt(j) == 'G') {
						sb.append('.');
					} else {
						sb.append(str.charAt(j));
					}
				}
				System.out.println(sb.toString());
			}
		}
	}
}

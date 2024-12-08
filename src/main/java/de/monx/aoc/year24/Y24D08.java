package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D08 extends Day {

	List<String> in = getInputList();
	Map<Character, List<int[]>> coords = new HashMap<>();
	int r1 = 0;
	int r2 = 0;

	@Override
	public Object part1() {
		solve();
		return r1;
	}

	@Override
	public Object part2() {
		return r2;
	}

	void solve() {
		for (int i = 0; i < in.size(); i++) { // init, find coords
			for (int j = 0; j < in.get(i).length(); j++) {
				char c = in.get(i).charAt(j);
				if (c != '.') {
					coords.putIfAbsent(c, new ArrayList<>());
					coords.get(c).add(new int[] { i, j });
				}
			}
		}
		Set<IntPair> antis = new HashSet<>(); // anti beacons for p1
		Set<IntPair> manti = new HashSet<>(); // additional beacons for p2
		for (var cors : coords.values()) {
			for (int i = 0; i < cors.size(); i++) {
				var ci = cors.get(i);
				for (int j = i + 1; j < cors.size(); j++) {
					var cj = cors.get(j);
					int[] del = { cj[0] - ci[0], cj[1] - ci[1] };
					for (int m = 0; true; m++) {
						boolean d1 = false;
						boolean d2 = false;
						IntPair p1 = new IntPair(ci[0] - m * del[0], ci[1] - m * del[1]);
						if (p1.first >= 0 && p1.second >= 0 && p1.second < in.get(0).length()) {
							if (m == 1) { // p1 only includes with del mult == 1
								antis.add(p1);
							} else { // others for p2
								manti.add(p1);
							}
						} else {
							d1 = true;
						}
						IntPair p2 = new IntPair(cj[0] + m * del[0], cj[1] + m * del[1]);
						if (p2.first < in.size() && p2.second >= 0 && p2.second < in.get(0).length()) {
							if (m == 1) { // p1 only includes with del mult == 1
								antis.add(p2);
							} else { // others for p2
								manti.add(p2);
							}
						} else {
							d2 = true;
						}
						if (d1 && d2) {
							break;
						}
					}
				}
			}
		}
		r1 = antis.size();
		antis.addAll(manti);
		r2 = antis.size();
	}
}

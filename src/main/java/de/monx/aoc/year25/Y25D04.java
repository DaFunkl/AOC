package de.monx.aoc.year25;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y25D04 extends Day {

	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();

	static final IntPair[] _DIRS = { //
			new IntPair(-1, -1), // ul
			new IntPair(-1, 0), // u
			new IntPair(-1, 1), // ur
			new IntPair(1, -1), // dl
			new IntPair(1, 0), // d
			new IntPair(1, 1), // dr
			new IntPair(0, -1), // l
			new IntPair(0, 1), // r
	};
	List<IntPair> rems = new ArrayList<>();

	@Override
	public Object part1() {
		int ret = 0;
		IntPair ip = new IntPair(0, 0);
		for (ip.first = 0; ip.first < in.size(); ip.first++) {
			for (ip.second = 0; ip.second < in.get(0).length; ip.second++) {
				if (in.get(ip.first)[ip.second] == '@') {
					int c = 0;
					for (var d : _DIRS) {
						var np = ip.add(d);
						if (outOfBounce(np, 0, 0, in.size(), in.get(0).length)) {
							continue;
						}
						if (in.get(np.first)[np.second] == '@') {
							c++;
						}
					}
					if (c < 4) {
						ret++;
						rems.add(ip.clone());
					}
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (var r : rems) {
			in.get(r.first)[r.second] = 'x';
		}
		while (!rems.isEmpty()) {
			ret += rems.size();
			List<IntPair> nr = new ArrayList<>();
			for (var pp : rems) {
				for (var dd : _DIRS) {
					var ip = pp.add(dd);
					if (outOfBounce(ip, 0, 0, in.size(), in.get(0).length)) {
						continue;
					}
					if (in.get(ip.first)[ip.second] == '@') {
						int c = 0;
						for (var d : _DIRS) {
							var np = ip.add(d);
							if (outOfBounce(np, 0, 0, in.size(), in.get(0).length)) {
								continue;
							}
							if (in.get(np.first)[np.second] == '@') {
								c++;
							}
						}
						if (c < 4) {
							in.get(ip.first)[ip.second] = '.';
							nr.add(ip.clone());
						}
					}
				}
			}
			rems = nr;
		}
		return ret;
	}

	boolean outOfBounce(IntPair p, int xl, int yl, int xr, int yr) {
		return (p.first < xl || p.second < yl || p.first >= xr || p.second >= yr);
	}
}

package de.monx.aoc.year25;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y25D05 extends Day {

	List<String> in = getInputList();
	List<long[]> ranges = new ArrayList<>();
	List<Long> ids = new ArrayList<>();

	@Override
	public Object part1() {
		boolean blank = false;
		for (var i : in) {
			if (i.isBlank()) {
				blank = true;
			} else if (!blank) {
				var s = i.split("-");
				ranges.add(new long[] { Long.valueOf(s[0]), Long.valueOf(s[1]) });
			} else {
				ids.add(Long.valueOf(i));
			}
		}
		int ret = 0;
		for (var i : ids) {
			for (var r : ranges) {
				if (r[0] <= i && i <= r[1]) {
					ret++;
					break;
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < ranges.size(); i++) {
				var r1 = ranges.get(i);
				for (int j = i + 1; j < ranges.size(); j++) {
					var r2 = ranges.get(j);
					if ((r2[0] <= r1[1] && r1[1] <= r2[1]) //
							|| (r1[0] <= r2[1] && r2[1] <= r1[1]) //
							|| (r1[0] <= r2[0] && r2[1] <= r1[1]) //
							|| (r2[0] <= r1[0] && r1[1] <= r2[1]) //
					) {
						ranges.get(i)[0] = Math.min(r1[0], r2[0]);
						ranges.get(i)[1] = Math.max(r1[1], r2[1]);
						ranges.remove(j);
						changed = true;
						break;
					}
				}
				if (changed) {
					break;
				}
			}
		}
		long ret = 0;
		for (var r : ranges) {
			ret += r[1] - r[0] + 1;
		}
		return ret;
	}
}

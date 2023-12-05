package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y23D05 extends Day {

	List<List<long[]>> vals = init();

	@Override
	public Object part1() {
		long[] mapping = new long[vals.get(0).size()];
		for (int i = 0; i < vals.get(0).size(); i++) {
			mapping[i] = vals.get(0).get(i)[0];
		}
		for (int i = 1; i < vals.size(); i++) {
			long[] newMap = Arrays.copyOf(mapping, mapping.length);
			for (var opt : vals.get(i)) {
				for (int j = 0; j < mapping.length; j++) {
					if (opt[1] <= mapping[j] && mapping[j] < (opt[1] + opt[2])) {
						newMap[j] = mapping[j] - opt[1] + opt[0];
					}
				}
			}
			mapping = newMap;
		}
		return Arrays.stream(mapping).min().getAsLong();
	}

	@Override
	public Object part2() {
		List<long[]> mapping = new ArrayList<>();
		for (int i = 0; i < vals.get(0).size(); i += 2) {
			mapping.add(new long[] { vals.get(0).get(i)[0], vals.get(0).get(i + 1)[0] });
		}
		for (int i = 1; i < vals.size(); i++) {
			Set<Integer> done = new HashSet<>();
			List<long[]> newMap = new ArrayList<>();
			for (var opt : vals.get(i)) {
				for (int j = 0; j < mapping.size(); j++) {
					if (done.contains(j)) {
						continue;
					}
					var r = mapping.get(j);
					if (opt[1] <= r[0] && (r[0] + r[1] - 1) < opt[1] + opt[2]) { // it fits
						newMap.add(new long[] { r[0] - opt[1] + opt[0], r[1] });
						done.add(j);
					} else if (opt[1] <= (r[0] + r[1] - 1) && (r[0] + r[1] - 1) < opt[1] + opt[2]) { // cut left
						newMap.add(new long[] { //
								opt[0], //
								r[0] + r[1] - opt[1] //
						});
						mapping.add(new long[] { //
								r[0], //
								opt[1] - r[0] //
						});
						done.add(j);
					} else if (opt[1] <= r[0] && r[0] < opt[1] + opt[2]) { // cut right
						newMap.add(new long[] { //
								r[0] - opt[1] + opt[0], //
								opt[1] + opt[2] - r[0] //
						});
						mapping.add(new long[] { //
								opt[1] + opt[2], //
								r[0] + r[1] - (opt[1] + opt[2]) //
						});
						done.add(j);
					} else if (r[0] <= opt[1] && (opt[1] + opt[2]) < (r[0] + r[1])) { // cut middle
						newMap.add(new long[] { //
								opt[0], //
								opt[2] //
						});
						mapping.add(new long[] { // right
								opt[1] + opt[2], //
								(r[0] + r[1]) - (opt[1] + opt[2]) //
						});
						mapping.add(new long[] { // left
								r[0], //
								opt[1] - r[0] //
						});
						done.add(j);
					}
				}
			}
			for (int j = 0; j < mapping.size(); j++) {
				if (!done.contains(j)) {
					newMap.add(mapping.get(j));
				}
			}
			mapping = newMap;
		}
		return mapping.stream().mapToLong(x -> x[0]).min().getAsLong();
	}

	List<List<long[]>> init() {
		List<List<long[]>> vals = new ArrayList<>();
		int mode = 0;
		for (var line : getInputList()) {
			if (line.isBlank()) {
				mode++;
				continue;
			}
			if (mode == 0) {
				vals.add(Arrays.stream(line.split(": ")[1].split(" ")).map(x -> new long[] { Long.valueOf(x) })
						.toList());
			} else {
				if (line.endsWith(":")) {
					vals.add(new ArrayList<>());
					continue;
				}
				var spl = line.split(" ");
				long[] arr = { Long.valueOf(spl[0]), Long.valueOf(spl[1]), Long.valueOf(spl[2]) };
				vals.get(mode).add(arr);
			}
		}
		return vals;
	}
}

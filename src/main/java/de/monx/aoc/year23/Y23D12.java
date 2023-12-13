package de.monx.aoc.year23;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y23D12 extends Day {

	List<Pair<String, int[]>> in = getInputList().stream().map(x -> x.split(" ")) //
			.map(x -> new Pair<String, int[]>(x[0], //
					Arrays.stream(x[1].split(",")).mapToInt(Integer::valueOf).toArray()//
			)).toList();

	@Override
	public Object part1() {
		return in.stream().mapToLong(a -> arrangements(a.second, a.first, 0, new HashMap<String, Long>()))
				.reduce((a, b) -> a + b).getAsLong();
	}

	@Override
	public Object part2() {
		long ret = 0;
		for (var a : in) {
			int[] arr = new int[a.second.length * 5];
			String str = a.first + "?" + a.first + "?" + a.first + "?" + a.first + "?" + a.first;
			for (int i = 0; i < a.second.length; i++) {
				arr[i] = a.second[i];
				arr[i + 1 * a.second.length] = a.second[i];
				arr[i + 2 * a.second.length] = a.second[i];
				arr[i + 3 * a.second.length] = a.second[i];
				arr[i + 4 * a.second.length] = a.second[i];
			}
			long b = arrangements(arr, str, 0, new HashMap<String, Long>());
			ret += b;
		}
		return ret;
	}

	long arrangements(int[] er, String str, int x, Map<String, Long> m) {
		String k = str + "," + x;
		if (m.containsKey(str + "," + x)) {
			return m.get(k);
		}
		if (x >= er.length) {
			if (!str.contains("#")) {
				m.put(k, 1l);
				return 1;
			} else {
				m.put(k, 0l);
				return 0;
			}
		}
		if (str.isBlank()) {
			m.put(k, 0l);
			return 0;
		}
		long ret = 0;
		for (int i = 0; i <= str.length() - er[x]; i++) {
			String subStr = str.substring(i, i + er[x]);
			boolean isAllowed = i + er[x] == str.length() || str.charAt(i + er[x]) != '#';
			if (!subStr.contains(".") && isAllowed) {
				ret += arrangements(er, str.substring(Math.min(i + er[x] + 1, str.length())), x + 1, m);
			}
			if (str.charAt(i) == '#') {
				break;
			}
		}
		m.put(k, ret);
		return ret;
	}
}

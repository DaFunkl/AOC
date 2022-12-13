package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y22D13 extends Day {

	List<Object> packets = new ArrayList<>();

	@Override
	public Object part1() {
		int ret = 0;
		int idx = 1;
		for (var p : getFileString().split("\r\n\r\n")) {
			var spl = p.split("\r\n");
			var p1 = Util.gson.fromJson(spl[0], ArrayList.class);
			var p2 = Util.gson.fromJson(spl[1], ArrayList.class);
			packets.add(p1);
			packets.add(p2);
			if (compare(p1, p2) >= 0) {
				ret += idx;
			}
			idx++;
		}
		return ret;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object part2() {
		packets.add(Util.gson.fromJson("[[2]]", ArrayList.class));
		packets.add(Util.gson.fromJson("[[6]]", ArrayList.class));
		Collections.sort(packets, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return Y22D13.compare(o2, o1);
			}
		});
		int i2 = -1, i6 = -1;
		for (int i = 0; i < packets.size(); i++) {
			if (packets.get(i) instanceof ArrayList && ((ArrayList) packets.get(i)).size() == 1) {
				ArrayList o = (ArrayList) packets.get(i);
				if (o.size() == 1 && o.get(0) instanceof ArrayList) {
					o = (ArrayList) o.get(0);
					if (o.size() == 1 && o.get(0) instanceof Double) {
						double d = (double) o.get(0);
						if (d == 2) {
							i2 = i + 1;
						} else if (d == 6) {
							i6 = i + 1;
						}
					}
				}
			}
		}
		return i2 * i6;
	}

	@SuppressWarnings({ "rawtypes" })
	static int compare(Object in1, Object in2) {
		if (in1.getClass() == in2.getClass()) {
			if (in1 instanceof Double) {
				double d1 = (double) in1;
				double d2 = (double) in2;
				if (d1 < d2) {
					return 1;
				} else if (d2 < d1) {
					return -1;
				} else {
					return 0;
				}
			}
			ArrayList l1 = (ArrayList) in1;
			ArrayList l2 = (ArrayList) in2;
			for (int i = 0; i < Math.max(l1.size(), l2.size()); i++) {
				if (l1.size() <= i) {
					return 1;
				}
				if (l2.size() <= i) {
					return -1;
				}
				int cmp = compare(l1.get(i), l2.get(i));
				if (cmp != 0) {
					return cmp;
				}
			}
		} else {
			ArrayList l1 = in1 instanceof ArrayList ? (ArrayList) in1
					: new ArrayList<Double>(Arrays.asList((double) in1));
			ArrayList l2 = in2 instanceof ArrayList ? (ArrayList) in2
					: new ArrayList<Double>(Arrays.asList((double) in2));
			return compare(l1, l2);
		}
		return 0;
	}
}

package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D22 extends Day {
	List<String> in = getInputList();
	List<List<int[]>> data = new ArrayList<>();
	IntPair zero = null;
	static final int _SIZE = 0;
	static final int _Used = 1;
	static final int _AVAIL = 2;
	static final int _USE = 3;

	@Override
	public Object part1() {
		init();
		Map<Integer, Set<IntPair>> availMap = new HashMap<>();
		Map<Integer, Set<IntPair>> usedMap = new HashMap<>();
		for (int x = 0; x < data.size(); x++) {
			var xData = data.get(x);
			for (int y = 0; y < data.get(x).size(); y++) {
				var yData = xData.get(y);
				IntPair ip = new IntPair(x, y);
				availMap.computeIfAbsent(yData[_AVAIL], k -> new HashSet<>()).add(ip);
				usedMap.computeIfAbsent(yData[_Used], k -> new HashSet<>()).add(ip);
				if (yData[_Used] == 0) {
					zero = new IntPair(x, y);
				}
			}
		}
		int ret = 0;
		for (var um : usedMap.entrySet()) {
			for (var am : availMap.entrySet()) {
				if (um.getKey() > 0 && um.getKey() <= am.getKey()) {
					for (var us : um.getValue()) {
						ret += am.getValue().size();
						if (am.getValue().contains(us)) {
							ret--;
						}
					}
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		// get access to
		// /dev/grid/node-x34-y0 88T 69T 19T 78%
		IntPair goal = new IntPair(data.size() - 1, 0);
		int steps = 0;
		while (!zero.equals(goal)) {
			if (zero.second == 0) {
				zero.addi(1, 0);
			} else if (data.get(zero.first).get(zero.second - 1)[_Used] > 100) {
				zero.addi(-1, 0);
			} else {
				zero.addi(0, -1);
			}
			steps++;
		}
		steps += 5 * (data.size() - 2);
		return steps;
	}

	void printData() {
		for (var xData : data) {
			StringBuilder sb = new StringBuilder();
			for (var yData : xData) {
				sb.append(String.format("%03d", yData[_Used])).append(" / ").append(String.format("%03d", yData[_SIZE]))
						.append("\t");
//				sb.append(Arrays.toString(yData)).append("\t");
			}
			System.out.println(sb.toString());
		}
	}

	void init() {
		List<int[]> yList = new ArrayList<>();
		for (int i = 2; i < in.size(); i++) {
			// /dev/grid/node-x0-y0 94T 65T 29T 69%
			// /dev/grid/node-x19-y22 87T 72T 15T 82%
			String s = in.get(i);
			int[] suau = { //
					Integer.valueOf(s.substring(24, 27).trim()), //
					Integer.valueOf(s.substring(30, 33).trim()), //
					Integer.valueOf(s.substring(37, 40).trim()), //
					Integer.valueOf(s.substring(43, 46).trim()), //
			};
			var sar = s.substring(16, 23).trim().replace("y", "").split("-");
			int y = Integer.valueOf(sar[1]);
			if (y == 0) {
				if (!yList.isEmpty()) {
					data.add(yList);
				}
				yList = new ArrayList<>();
			}
			yList.add(suau);
		}
		if (!yList.isEmpty()) {
			data.add(yList);
		}
	}
}

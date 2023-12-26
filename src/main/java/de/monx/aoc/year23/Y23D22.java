package de.monx.aoc.year23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y23D22 extends Day {

	List<int[][]> in = getInputList().stream().map(x -> x.replace("~", ",").split(","))//
			.map(x -> new int[][] { //
					new int[] { Integer.valueOf(x[0]), Integer.valueOf(x[1]), Integer.valueOf(x[2]) },
					new int[] { Integer.valueOf(x[3]), Integer.valueOf(x[4]), Integer.valueOf(x[5]) } })
			.toList();

	@Override
	public Object part1() {
		Map<Integer, Set<Integer>> hasAbove = new HashMap<>();
		Map<Integer, Set<Integer>> hasBeneath = new HashMap<>();
		Map<Integer, Set<Integer>> touchingBeneath = new HashMap<>();
		Set<Integer> notDone = new HashSet<>();
		System.out.println("check");
		for (int i = 0; i < in.size(); i++) {
			var b1 = in.get(i);
			notDone.add(i);
			hasAbove.put(i, new HashSet<>());
			touchingBeneath.put(i, new HashSet<>());
			if (!hasBeneath.containsKey(i)) {
				hasBeneath.put(i, new HashSet<>());
			}
			for (int j = 0; j < in.size(); j++) {
				if (i == j) {
					continue;
				}
				var b2 = in.get(j);
				// b1 is not beneath b2
				if (b1[1][2] > b2[0][2]) {
					continue;
				}
				// they dont overlap
				if (b1[1][0] < b2[0][0] || b2[1][0] < b1[0][0] || b1[1][1] < b2[0][1] || b2[1][1] < b1[0][1]) {
					continue;
				}
				hasAbove.get(i).add(j);
				if (!hasBeneath.containsKey(j)) {
					hasBeneath.put(j, new HashSet<>());
				}
				hasBeneath.get(j).add(i);
			}
		}
		System.out.println("drop");
		while (!notDone.isEmpty()) {
			for (var i : notDone) {
				var b = in.get(i);
				if (b[0][2] == 1) {
					notDone.remove(i);
					break;
				}
				boolean freeToMove = true;
				boolean removed = false;
				for (var j : hasBeneath.get(i)) {
					var bj = in.get(j);
					if (b[0][2] - bj[1][2] == 1) {
						freeToMove = false;
						if (!notDone.contains(j)) {
							notDone.remove(i);
							removed = true;
						}
					}
				}
				if (removed) {
					break;
				}
				if (freeToMove) {
					in.get(i)[0][2]--;
					in.get(i)[1][2]--;
				}
			}
		}
		for (int i = 0; i < in.size(); i++) {
			var b = in.get(i);
			for (var j : hasBeneath.get(i)) {
				var bj = in.get(j);
				if (b[0][2] - bj[1][2] == 1) {
					touchingBeneath.get(i).add(j);
				}
			}
		}
		System.out.println("roll");
		Set<Integer> canRemove = new HashSet<>();
		for (int i = 0; i < in.size(); i++) {
			if (hasAbove.get(i).isEmpty()) {
				canRemove.add(i);
			}
			boolean removable = true;
			for (var r : hasAbove.get(i)) {
				if (touchingBeneath.get(r).size() <= 1) {
					removable = false;
					break;
				}
			}
			if (removable) {
				canRemove.add(i);
			}
		}
		return canRemove.size();
	}

	@Override
	public Object part2() {
		return null;
	}

}

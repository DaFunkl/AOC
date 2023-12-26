package de.monx.aoc.year23;

import java.util.ArrayList;
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
		Map<Integer, List<Integer>> above = new HashMap<>();
		Map<Integer, List<Integer>> below = new HashMap<>();
		Set<Integer> todos = new HashSet<>();
		for (int i = 0; i < in.size(); i++) {
			var bi = in.get(i);
			todos.add(i);
			above.putIfAbsent(i, new ArrayList<>());
			below.putIfAbsent(i, new ArrayList<>());
			for (int j = i + 1; j < in.size(); j++) {
				var bj = in.get(j);
				above.putIfAbsent(j, new ArrayList<>());
				below.putIfAbsent(j, new ArrayList<>());
				if (!intersect(bi, bj)) {
					continue;
				}
				if (bi[1][2] < bj[0][2]) {
					above.get(i).add(j);
					below.get(j).add(i);
				} else if (bj[1][2] < bi[0][2]) {
					above.get(j).add(i);
					below.get(i).add(j);
				} else {
					System.err.println("Someting Wong: " + i + "|" + j);
				}
			}
		}
		while (!todos.isEmpty()) {
			for (var i : todos) {
				var bi = in.get(i);
				int dropto = 1;
				boolean done = true;
				for (var j : below.get(i)) {
					dropto = Math.max(dropto, in.get(j)[1][2] + 1);
					if (todos.contains(j)) {
						done = false;
					}
				}
				if (dropto < bi[0][2]) {
					in.get(i)[1][2] -= in.get(i)[0][2] - dropto;
					in.get(i)[0][2] = dropto;
				}
				if (done) {
					todos.remove(i);
					break;
				}
			}
		}
		Map<Integer, List<Integer>> touchu = new HashMap<>();
		Map<Integer, List<Integer>> touchd = new HashMap<>();
		for (int i = 0; i < in.size(); i++) {
			touchu.put(i, new ArrayList<>());
			touchd.put(i, new ArrayList<>());
			var bi = in.get(i);
			for (int j : above.get(i)) {
				var bj = in.get(j);
				if (bi[0][2] - bj[1][2] == 1) {
					touchd.get(i).add(j);
				}
				if (bi[1][2] - bj[0][2] == -1) {
					touchu.get(i).add(j);
				}
			}
		}

		int ret = 0;
		for (int i = 0; i < in.size(); i++) {
			boolean canRemove = true;
			for (var j : touchu.get(i)) {
				if (touchd.get(j).size() < 2) {
					canRemove = false;
					break;
				}
			}
			if (canRemove) {
				System.out.println("can remove: " + i);
				ret++;
			}
		}
		System.out.println(ret);
		return ret;
	}

	boolean intersect(int[][] b1, int[][] b2) {
		if (b1[0][0] <= b2[1][0] && b1[0][1] <= b2[1][1] && //
				b2[0][0] <= b1[1][0] && b2[0][1] <= b1[1][1]) {
			return true;
		}
		return false;
	}

	@Override
	public Object part2() {
		return null;
	}

}

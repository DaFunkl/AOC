package de.monx.aoc.year22;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y22D19 extends Day {

	static final int _ORE = 0;
	static final int _CLAY = 1;
	static final int _OBSI = 2;
	static final int _GEOD = 3;
	List<Blueprint> blueprints = getInputList().stream().map(x -> new Blueprint(x)).toList();

	@Override
	public Object part1() {
		return blueprints.stream().mapToInt(x -> simulate(x, 24) * x.id).sum();
	}

	@Override
	public Object part2() {
		int sim1 = simulate(blueprints.get(0), 32);
		int sim2 = simulate(blueprints.get(1), 32);
		int sim3 = simulate(blueprints.get(2), 32);
		return sim1 * sim2 * sim3;
	}

	int simulate(Blueprint initState, int time) {
		ArrayDeque<Blueprint> stack = new ArrayDeque<>();
		Blueprint state = initState.copy();
		Map<String, Blueprint> map = new HashMap<>();
		state.time = time;
		stack.push(state);
		int max = 0;
		while (!stack.isEmpty()) {
			state = stack.pollFirst();
			max = Math.max(max, state.holding[_GEOD]);
			if (state.time == 0) {
				continue;
			}
			String hash = state.hash();
			if (map.containsKey(hash) && state.isLessValuable(map.get(hash))) {
				continue;
			}
			map.put(hash, state);
			if (state.holding[_ORE] < state.maxOreCost) {
				Blueprint newBP = state.copy();
				newBP.collect();
				stack.push(newBP.copy());
			}

			for (int[] buildOpts : state.buildOpts()) {
				Blueprint newBP = state.copy();
				newBP.collect();
				newBP.bots[buildOpts[0]]++;
				newBP.holding[_ORE] -= buildOpts[1];
				if (buildOpts[0] >= _OBSI) {
					newBP.holding[buildOpts[0] - 1] -= buildOpts[2];
				}
				stack.push(newBP);
			}
		}
		return max;
	}

	@Data
	class Blueprint {
		int id = 0;
		int[] bots = { 1, 0, 0, 0 };
		int[] holding = new int[4];
		int maxOreCost = 0;
		int time;
		int[][] costs = new int[4][2];

		String hash() {
			return Arrays.toString(bots) + time; // + Arrays.toString(holding);
		}

		public Blueprint() {
		}

		public Blueprint(String line) {
			var sar = line.split(":");
			id = Integer.valueOf(sar[0].split(" ")[1]);
			sar = sar[1].trim().split("\\.");
			for (var sen : sar) {
				var arr = sen.trim().split(" ");
				int idx = fetchId(arr[1]);
				costs[idx][_ORE] = Integer.valueOf(arr[4]);
				if (idx >= 2) {
					costs[idx][1] = Integer.valueOf(arr[7]);
				}
			}
			maxOreCost = Math.max(costs[0][_ORE],
					Math.max(costs[1][_ORE], Math.max(costs[2][_ORE], Math.max(costs[3][_ORE], id))));
		}

		List<int[]> buildOpts() {
			List<int[]> ret = new ArrayList<>();
			for (int i = 3; i >= 0; i--) {
				if (costs[i][0] <= holding[0] && (i == 0 || costs[i][1] <= holding[i - 1])) {
					ret.add(new int[] { i, costs[i][_ORE], costs[i][1] });
					if (i == 3) {
						break;
					}
				}
			}
			return ret;
		}

		int[] weight() {
			return new int[] { //
					(time * bots[0]) + holding[0], //
					(time * bots[1]) + holding[1], //
					(time * bots[2]) + holding[2], //
					(time * bots[3]) + holding[3], //
			};
		}

		boolean isLessValuable(Blueprint bp) {
			int[] w1 = weight();
			int[] w2 = bp.weight();
			for (int i = 3; i >= 0; i--) {
				if (w1[i] < w2[i]) {
					return true;
				} else if (w1[i] > w2[i]) {
					return false;
				}
			}
			return true;
		}

		void collect() {
			for (int i = 0; i < 4; i++) {
				holding[i] += bots[i];
			}
			time--;
		}

		int fetchId(String str) {
			return switch (str) {
			case "ore" -> _ORE;
			case "clay" -> _CLAY;
			case "obsidian" -> _OBSI;
			case "geode" -> _GEOD;
			default -> -1;
			};
		}

		Blueprint copy() {
			Blueprint bp = new Blueprint();
			bp.id = id;
			for (int i = 0; i < 4; i++) {
				bp.costs[i] = Arrays.copyOf(costs[i], 2);
			}
			bp.bots = Arrays.copyOf(bots, 4);
			bp.holding = Arrays.copyOf(holding, 4);
			bp.maxOreCost = maxOreCost;
			bp.time = time;
			return bp;
		}
	}
}

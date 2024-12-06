package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D06 extends Day {
	List<String> grid = getInputList();
	IntPair startPos = new IntPair(0, 0);
	Set<IntPair> walls = new HashSet<>();
	static final IntPair[] _DIRS = { //
			new IntPair(-1, 00), // U
			new IntPair(00, 01), // R
			new IntPair(01, 00), // D
			new IntPair(00, -1), // L
	};
	Map<IntPair, List<IntPair>> wallHit = new HashMap<>();

	@Override
	public Object part1() {
		for (int i = 0; i < grid.size(); i++) { // init data
			for (int j = 0; j < grid.get(i).length(); j++) {
				if (grid.get(i).charAt(j) == '#') {
					walls.add(new IntPair(i, j));
				} else if (grid.get(i).charAt(j) == '^') {
					startPos.first = i;
					startPos.second = j;
				}
			}
		}
		return sim(startPos.clone(), 0, walls);
	}

	@Override
	public Object part2() {
		Map<IntPair, List<IntPair>> seen = new HashMap<>();
		var pos = startPos.clone(); // sim walking and place walls infront of head
		int cp = 0;
		int count = 0;
		Set<IntPair> loopWalls = new HashSet<>();
		while (pos.first >= 0 && pos.second >= 0 && pos.first < grid.size() && pos.second < grid.get(0).length()) {
			seen.putIfAbsent(pos, new ArrayList<>());
			seen.get(pos).add(new IntPair(cp, count));
			var np = pos.add(_DIRS[cp]);
			if (walls.contains(np)) {
				cp = (cp + 1) % 4;
			} else {
				walls.add(np);
				
				var pp = pos.clone();
				int cpp = cp;
				if (seen.containsKey(np)) { 
					pp = startPos.clone();
					cpp = 0;
				}
				
				if (sim(pp, cpp, walls) < 0) { // test if wall is alright
					loopWalls.add(np);
				}
				walls.remove(np);
				pos = np;
			}
			count++;
		}
		return loopWalls.size();
	}

	int sim(IntPair pos, int cp, Set<IntPair> walls) {
		Map<IntPair, List<IntPair>> seen = new HashMap<>();
//		var pos = startPos.clone(); // solve
//		int cp = 0;
		int count = 0;
		while (pos.first >= 0 && pos.second >= 0 && pos.first < grid.size() && pos.second < grid.get(0).length()) {
			final int fcp = cp;
			if (seen.getOrDefault(pos, new ArrayList<>()).stream().anyMatch(x -> x.first == fcp)) {
				return -1;
			}
			seen.putIfAbsent(pos, new ArrayList<>());
			seen.get(pos).add(new IntPair(cp, count));
			var np = pos.add(_DIRS[cp]);
			if (walls.contains(np)) {
				cp = (cp + 1) % 4;
			} else {
				pos = np;
			}
			count++;
		}
		return seen.size();
	}
}

package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_23_17;
import de.monx.aoc.util.anim.DP_24_06;
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
	Map<IntPair, List<IntPair>> walk = new HashMap<>();

	boolean isAnim = true;
	Animation anim = null;
	long sleep = 0;

	void drawAnim(long sleep, IntPair cp, Map<IntPair, List<IntPair>> walk1, Map<IntPair, List<IntPair>> walk2) {
		drawAnim(sleep, cp, walk1, walk2, false);
	}

	void drawAnim(long sleep, IntPair cp, Map<IntPair, List<IntPair>> walk1, Map<IntPair, List<IntPair>> walk2,
			boolean isLoop) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1560, 830, new DP_24_06());
			((DP_24_06) anim.pane).update(sleep, cp, new HashMap<>(), new HashMap<>(), isLoop);
			((DP_24_06) anim.pane).setGrid(grid);
			Util.readLine();
		}
		((DP_24_06) anim.pane).update(sleep, cp, walk1, walk2, isLoop);
	}

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
		var pos = startPos.clone(); // sim walking and place walls in front of head
		int cp = 0;
		int count = 0;
		Set<IntPair> loopWalls = new HashSet<>();
		while (pos.first >= 0 && pos.second >= 0 && pos.first < grid.size() && pos.second < grid.get(0).length()) {
			walk.putIfAbsent(pos, new ArrayList<>());
			walk.get(pos).add(new IntPair(cp, count));
			var np = pos.add(_DIRS[cp]);
			if (walls.contains(np)) {
				cp = (cp + 1) % 4;
			} else {
				walls.add(np);

				var pp = pos.clone();
				int cpp = cp;
				if (walk.containsKey(np)) {
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
//			drawAnim(10, pos, walk, walk);
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
				drawAnim(50, pos, walk, seen, true);
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
//			if(count % 100 == 0) {
//				drawAnim(1, pos, walk, seen);
//			} else {
//				drawAnim(0, pos, walk, seen);
//			}
		}
		drawAnim(10, pos, walk, seen);
		return seen.size();
	}
}

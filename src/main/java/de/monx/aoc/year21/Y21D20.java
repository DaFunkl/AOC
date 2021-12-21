package de.monx.aoc.year21;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_21_20;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y21D20 extends Day {

	boolean[] lookup = new boolean[512];
	Map<IntPair, Boolean> dots = new HashMap<>();
	int p1 = 0, p2 = 0;

	boolean isAnim = false;
	Animation anim = null;
	long sleep = 100;

	void drawAnim(long sleep, Map<IntPair, Boolean> in, boolean unseen) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(900, 900, new DP_21_20());
		}
		((DP_21_20) anim.pane).update(sleep, in, unseen);
	}

	@Override
	public Object part1() {
		solve();
		return p1;
	}

	@Override
	public Object part2() {
		return p2;
	}

	void solve() {
		init();

		var output = enhance(dots, false);
		drawAnim(sleep, output, false);
//		Util.readLine();
		for (int i = 1; i < 50; i++) {
			drawAnim(sleep, output, i % 2 == 1 ? lookup[0] : lookup[511]);
			output = enhance(output, i % 2 == 1 ? lookup[0] : lookup[511]);
//			output = enhance(output, false); // this is used for example Test
			if (i == 1) {
				p1 = output.entrySet().stream().map(x -> x.getValue() ? 1 : 0).reduce(Integer::sum).get();
			}
		}
		drawAnim(sleep * 10, output, lookup[511]);

		p2 = output.entrySet().stream().map(x -> x.getValue() ? 1 : 0).reduce(Integer::sum).get();
//		print(output, lookup[511]);
	}

	Map<IntPair, Boolean> enhance(Map<IntPair, Boolean> in, boolean unseen) {
		Map<IntPair, Boolean> output = new HashMap<>();
		Set<IntPair> done = new HashSet<>();
		for (var d : in.keySet()) {
			for (int ry = -1; ry < 2; ry++) {
				for (int rx = -1; rx < 2; rx++) {
					var dd = d.add(ry, rx);
					if (done.contains(dd)) {
						continue;
					}
					done.add(dd);
					int idx = 0;
					for (int y = -1; y < 2; y++) {
						for (int x = -1; x < 2; x++) {
							idx <<= 1;
							var p = dd.add(y, x);
							boolean lit = in.containsKey(p) ? in.get(p) : unseen;
							if (lit) {
								idx |= 1;
							}
						}
					}
					output.put(dd, lookup[idx]);
				}
			}
		}
		return output;
	}

	int[] fetchMM(Map<IntPair, Boolean> dots) {
		int[] mm = new int[] { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE };
		for (var d : dots.keySet()) {
			mm[0] = Math.min(mm[0], d.first);
			mm[1] = Math.max(mm[1], d.first);
			mm[2] = Math.min(mm[2], d.second);
			mm[3] = Math.max(mm[3], d.second);
		}
		return mm;
	}

	void print(Map<IntPair, Boolean> dots, boolean unseen) {
		int[] mm = fetchMM(dots);

		for (int y = mm[0]; y <= mm[1]; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = mm[2]; x <= mm[3]; x++) {
				IntPair ip = new IntPair(y, x);
				boolean lit = dots.containsKey(ip) ? dots.get(ip) : unseen;
				if (lit) {
					sb.append("#");
				} else {
					sb.append(".");
				}
			}
			System.out.println(sb.toString());
		}

	}

	void init() {
		List<String> in = getInputList();
		char[] carl = in.get(0).toCharArray();
		for (int i = 0; i < 512; i++) {
			if (carl[i] == '#') {
				lookup[i] = true;
			}
		}
		for (int i = 2; i < in.size(); i++) {
			char[] car = in.get(i).toCharArray();
			for (int j = 0; j < car.length; j++) {
				dots.put(new IntPair(i - 2, j), car[j] == '#');
			}
		}
	}
}

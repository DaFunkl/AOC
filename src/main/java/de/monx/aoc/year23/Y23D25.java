package de.monx.aoc.year23;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y23D25 extends Day {

	List<String> in = getInputList();
	Map<String, Set<String>> cons = init();

	@Override
	public Object part1() {
		List<String> keys = new ArrayList<>(cons.keySet());
		Collections.sort(keys, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return cons.get(o1).size() - cons.get(o2).size();
			}
		});
		Set<String> combosDone = new HashSet<>();
		for (int i = 7; i < keys.size(); i++) {
			System.out.println("i [" + i + "] / " + keys.size());
			String si1 = keys.get(i);
			for (int j = i + 1; j < keys.size(); j++) {
				System.out.println("i [" + i + "] j [" + j + "] / " + keys.size());
				String sj1 = keys.get(j);
				for (int k = j + 1; k < keys.size(); k++) {
					for (var si2 : cons.get(si1)) {
						for (var sj2 : cons.get(sj1)) {
							if (sj1.equals(si2) || sj2.equals(si1) || sj1.equals(si1) || sj1.equals(si1)) {
								continue;
							}
//							System.out.println("i [" + i + "] j [" + j + "] k [" + k + "] / " + keys.size());
							String sk1 = keys.get(k);
							for (var sk2 : cons.get(sk1)) {
								if (sk1.equals(si2) || sk2.equals(si1) || sk2.equals(si2) || sk1.equals(si1)) {
									continue;
								}
								if (sj1.equals(sk2) || sj2.equals(sk1) || sj2.equals(sk2) || sj1.equals(sk1)) {
									continue;
								}
								String[] combo = { //
										(si1.compareTo(si2) < 0 ? si1 + si2 : si2 + si1), //
										(sj1.compareTo(sj2) < 0 ? sj1 + sj2 : sj2 + sj1), //
										(sk1.compareTo(sk2) < 0 ? sk1 + sk2 : sk2 + sk1) };
								Arrays.sort(combo);
								var cs = Arrays.toString(combo);
								if (combosDone.contains(cs)) {
									continue;
								}
								combosDone.add(cs);
								int cuts = tryCut(new String[][] { { si1, si2 }, { sj1, sj2 }, { sk1, sk2 } });
//								System.out.println("-> " + cuts + "\n");
								if (cuts > 0) {
									return cuts;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	int tryCut(String[][] cuts) {
//		System.out.println(Arrays.toString(cuts[0]));
//		System.out.println(Arrays.toString(cuts[1]));
//		System.out.println(Arrays.toString(cuts[2]));
//
//		Set<String> test = new HashSet<>();
//		for (var x : cuts) {
//			for (var y : x) {
//				if ( //
//				y.equals("hfx") || //
//						y.equals("pzl") || //
//						y.equals("bvb") || //
//						y.equals("cmg") || //
//						y.equals("nvd") || //
//						y.equals("jqt")//
//				) {
//					test.add(y);
//				}
//			}
//		}
//		if(test.size() == 6) {
//			System.out.println("here");
//		}

		Set<String> graph = new HashSet<>();
		ArrayDeque<String> todo = new ArrayDeque<>();
		todo.add(cuts[0][0]);
		while (!todo.isEmpty()) {
			var cur = todo.pop();
			if (graph.contains(cur)) {
				continue;
			}
			graph.add(cur);
			for (var s : cons.get(cur)) {
				boolean isCut = false;
				for (int i = 0; i < 3; i++) {
					if ((cuts[i][0].equals(s) && cuts[i][1].equals(cur))
							|| (cuts[i][1].equals(s) && cuts[i][0].equals(cur))) {
						isCut = true;
						break;
					}
				}
				if (isCut) {
					continue;
				}
				todo.add(s);
			}
		}
		if (graph.size() == cons.size()) {
			return -1;
		}
		return graph.size() * (cons.size() - graph.size());
	}

	@Override
	public Object part2() {
		return "It's done!";
	}

	Map<String, Set<String>> init() {
		Map<String, Set<String>> ret = new HashMap<>();
		for (var s : in) {
			var spl = s.split(": ");
			var k = spl[0];
			spl = spl[1].split(" ");
			ret.putIfAbsent(k, new HashSet<>());
			for (var v : spl) {
				ret.putIfAbsent(v, new HashSet<>());
				ret.get(k).add(v);
				ret.get(v).add(k);
			}
		}
		return ret;
	}
}

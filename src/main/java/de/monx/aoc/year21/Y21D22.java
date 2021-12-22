package de.monx.aoc.year21;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y21D22 extends Day {
	List<String> in = getInputList();
	List<Pair<Boolean, long[][]>> ops = new ArrayList<>();

	long p1, p2;

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

		List<long[][]> ons = new ArrayList<>();
		int cc = 0;

		long[][] irange = { //
				{ -50, 50 }, //
				{ -50, 50 }, //
				{ -50, 50 } //
		};
		for (var opp : ops) {
			System.out.println(cc++ + "/" + (ops.size() - 1));
			Deque<Pair<Boolean, long[][]>> stack = new ArrayDeque<>();
			stack.push(opp);
			while (!stack.isEmpty()) {
				var op = stack.pop();
				long[][] opCube = null;
				opCube = op.second;
				if (opCube == null) {
					continue;
				}
				int endIter = ons.size();
				for (int i = 0; i < endIter; i++) {
					var on = ons.get(i);
					long[][] intersect = intersect(opCube, on);
					if (intersect == null) {
						continue;
					} else {

						if (!op.first) {
							ons.remove(i);
							endIter--;
							i--;
							if (!same(on, intersect)) {
								var onCut = cut(on, intersect);

								ons.add(onCut.get(0));
								for (int c = 1; c < onCut.size(); c++) {
									ons.add(onCut.get(c));
								}
							}
						}
						if (same(opCube, intersect)) {
							opCube = null;
							break;
						}
						var cuts = cut(opCube, intersect);
						opCube = cuts.get(0);
						for (int c = 1; c < cuts.size(); c++) {
							stack.push(new Pair<Boolean, long[][]>(op.first, cuts.get(c)));
						}

					}
				}
				if (opCube != null && op.first) {
					ons.add(opCube);
				}
				merge(ons);
			}
		}
		p1 = ons.stream().map(x -> cubes(intersect(x, irange))).reduce(Long::sum).get();
		p2 = ons.stream().map(x -> cubes(x)).reduce(Long::sum).get();
	}

	long cubes(long[][] arr) {
		if (arr == null) {
			return 0l;
		}
		return (1 + Math.abs(arr[0][1] - arr[0][0])) //
				* (1 + Math.abs(arr[1][1] - arr[1][0])) //
				* (1 + Math.abs(arr[2][1] - arr[2][0]));
	}

	long[][] intersect(long[][] a, long[][] b) {
		long[][] ret = new long[3][2];
		for (int i = 0; i < 3; i++) {
			if (a[i][1] < b[i][0] || b[i][1] < a[i][0]) {
				return null;
			} else {
				ret[i][0] = Math.max(a[i][0], b[i][0]);
				ret[i][1] = Math.min(a[i][1], b[i][1]);
			}
		}
		return ret;
	}

	void merge(List<long[][]> in) {
		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < in.size(); i++) {
				var a = in.get(i);
//				print(a);
				for (int j = i + 1; j < in.size(); j++) {
					var b = in.get(j);
//					print(b);
					if (isInside(a, b)) {
//						System.out.println("isInside(a, b)");
						in.remove(j);
						i--;
						changed = true;
						break;
					}
					if (isInside(b, a)) {
//						System.out.println("isInside(b, a)");
						in.remove(i);
						i--;
						changed = true;
						break;
					}
					var append = tryAppend(a, b);
//					System.out.println("tryAppend(a, b)");
					if (append != null) {
						in.set(i, append);
						in.remove(j);
						i--;
						changed = true;
						break;
					}
					append = tryAppend(b, a);
//					System.out.println("tryAppend(b, a)");
					if (append != null) {
						in.set(i, append);
						in.remove(j);
						i--;
						changed = true;
						break;
					}
				}
			}
		}
	}

	long[][] tryAppend(long[][] a, long[][] b) {
		boolean xe = a[0][0] == b[0][0] && a[0][1] == b[0][1];
		boolean ye = a[1][0] == b[1][0] && a[1][1] == b[1][1];
		boolean ze = a[2][0] == b[2][0] && a[2][1] == b[2][1];
		if (xe && ye && a[2][0] <= b[2][0] && b[2][0] <= a[2][1] + 1) {
			return new long[][] { //
					{ a[0][0], a[0][1] }, //
					{ a[1][0], a[1][1] }, //
					{ a[2][0], b[2][1] },//
			};
		}
		if (ye && ze && a[0][0] <= b[0][0] && b[0][0] <= a[0][1] + 1) {
			return new long[][] { //
					{ a[0][0], b[0][1] }, //
					{ a[1][0], a[1][1] }, //
					{ a[2][0], a[2][1] }, //
			};
		}
		if (ze && xe && a[1][0] <= b[1][0] && b[1][0] <= a[1][1] + 1) {
			return new long[][] { //
					{ a[0][0], a[0][1] }, //
					{ a[1][0], b[1][1] }, //
					{ a[2][0], a[2][1] }, //
			};
		}
		return null;
	}

	boolean isInside(long[][] a, long[][] b) {
		for (int i = 0; i < 3; i++) {
			if (!(a[i][0] <= b[i][0] && b[i][1] <= a[i][1])) {
				return false;
			}
		}
		return true;
	}

	long[] fetchMM(long[][] a, long[][] b, int i, int d) {
		return switch (i) {
		case 0 -> new long[] { //
				a[d][0], // --> always a 0
				Math.max(a[d][0], (b[d][0] - 1)) //
			};
		case 1 -> new long[] { //
				b[d][0], // --> always d0
				b[d][1] // --> always d1
			};
		case 2 -> new long[] { //
				Math.min(a[d][1], (b[d][1] + 1)), //
				a[d][1] // --> always a 1
			};
		default -> null;
		};
	}

	List<long[][]> cut(long[][] a, long[][] b) {
		List<long[][]> ret = new ArrayList<>();
		if (same(a, b)) {
			return null;
		}

		for (int z = 0; z < 3; z++) {
			long[] zz = fetchMM(a, b, z, 2);
			for (int y = 0; y < 3; y++) {
				long[] yy = fetchMM(a, b, y, 1);
				for (int x = 0; x < 3; x++) {
					if (z == 1 && y == 1 && x == 1) {
						continue;
					}
					long[] xx = fetchMM(a, b, x, 0);

					long[][] cut = { { xx[0], xx[1] }, { yy[0], yy[1] }, { zz[0], zz[1] } };

					boolean ignoreCut = false;
					if (intersect(b, cut) != null) {
						ignoreCut = true;
					} else {
						for (var r : ret) {
							if (same(r, cut)) {
								ignoreCut = true;
								break;
							}
						}
					}
					if (!ignoreCut) {
						ret.add(cut);
					}
				}
			}
		}
		merge(ret);
		return ret;
	}

	void print(long[][] arr) {
//		System.out.println("=======================");
		for (var a : arr) {
			for (var i : a) {
				System.out.print(i + ", ");
			}
//			System.out.println(Arrays.toString(a));
		}
		System.out.println();
	}

	boolean same(long[][] a, long[][] b) {
		for (int i = 0; i < 3; i++) {
			if (a[i][0] != b[i][0] || a[i][1] != b[i][1]) {
				return false;
			}
		}
		return true;
	}

	void init() {

		for (String s : in) {
			boolean on = s.startsWith("on");
			var xyz = s.split(" ")[1].replaceAll("[xyz=]", "").replace("..", ",").split(",");
			ops.add(new Pair<Boolean, long[][]>(on, new long[][] { //
					{ Long.valueOf(xyz[0]), Long.valueOf(xyz[1]) }, //
					{ Long.valueOf(xyz[2]), Long.valueOf(xyz[3]) }, //
					{ Long.valueOf(xyz[4]), Long.valueOf(xyz[5]) }, //
			}));
		}
	}
}

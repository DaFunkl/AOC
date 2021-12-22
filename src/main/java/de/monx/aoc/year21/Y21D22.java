package de.monx.aoc.year21;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class Y21D22 extends Day {
	List<String> in = getInputList();
	List<Pair<Boolean, long[][]>> ops = new ArrayList<>();

	@Override
	public Object part2() {

		return null;
	}

	@Override
	public Object part1() {
		init();

		boolean skip = true;

		long[][] a = { //
				{ 0, 1 }, //
				{ 0, 0 }, //
				{ 0, 0 }, //
		};

		long[][] b = { //
				{ 1, 1 }, //
				{ 0, 0 }, //
				{ 0, 0 }, //
		};

		var cut = cut(a, b);
		for (var cc : cut) {
			System.out.println("=====");
			for (var c : cc) {
				System.out.println(Arrays.toString(c));
			}
		}

		if (skip) {
			return null;
		}

		List<long[][]> ons = new ArrayList<>();
		int cc = 0;
		for (var opp : ops) {
			System.out.println(cc++);
			System.out.println(ons.stream().map(x -> cubes(x)).reduce(Long::sum));
			Deque<Pair<Boolean, long[][]>> stack = new ArrayDeque<>();
			stack.push(opp);
			while (!stack.isEmpty()) {
				var op = stack.pop();
				long[][] opCube = null;
				opCube = op.second;
				for (int i = 0; i < ons.size(); i++) {
					var on = ons.get(i);
					long[][] intersect = intersect(opCube, on);
					if (intersect == null) {
						continue;
					} else {
						if (!op.first) {
							ons.remove(i--);
							if (!same(on, intersect)) {
								var onCut = cut(on, intersect);

								if (onCut.isEmpty()) { // bug
									continue;
								}

								ons.add(onCut.get(0));
								for (int c = 1; c < onCut.size(); c++) {
									ons.add(onCut.get(c));
								}
							}
						}
						if (same(opCube, intersect)) { // bug
							opCube = null;
							break;
						}
						var cuts = cut(opCube, intersect);
						if (cuts.isEmpty()) {
							cuts = cut(opCube, intersect);
							continue;
						}
						opCube = cuts.get(0);
						for (int c = 1; c < cuts.size(); c++) {
							stack.push(new Pair<Boolean, long[][]>(op.first, cuts.get(c)));
						}

					}
				}
				if (opCube != null) {
					ons.add(opCube);
				}
				merge(ons);
			}
		}
		return ons.stream().map(x -> cubes(x)).reduce(Long::sum).get();
	}

	long cubes(long[][] arr) {
		return Math.abs((arr[0][1] - arr[0][0]) * (arr[1][1] - arr[1][0]) * (arr[2][1] - arr[2][0]));
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
				for (int j = i + 1; j < in.size(); j++) {
					var b = in.get(j);
					if (isInside(a, b)) {
						in.remove(j);
						j--;
						changed = true;
						continue;
					}
					if (isInside(b, a)) {
						in.remove(i);
						i--;
						changed = true;
						break;
					}
					var append = tryAppend(a, b);
					if (append != null) {
						in.set(i, append);
						in.remove(j);
						i--;
						changed = true;
						break;
					}
					append = tryAppend(b, a);
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
		if (xe && ye) {
			if (a[2][0] <= b[2][0] && a[2][1] + 1 <= b[2][0]) {
				return new long[][] { //
						{ a[0][0], a[0][1] }, //
						{ a[1][0], a[1][1] }, //
						{ a[2][0], b[2][1] },//
				};
			}
		}
		if (ye && ze) {
			if (a[0][0] <= b[0][0] && a[0][1] + 1 <= b[0][0]) {
				return new long[][] { //
						{ a[0][0], b[0][1] }, //
						{ a[1][0], a[1][1] }, //
						{ a[2][0], a[2][1] }, //
				};
			}
		}
		if (ze && xe) {
			if (a[1][0] <= b[1][0] && a[1][1] + 1 <= b[1][0]) {
				return new long[][] { //
						{ a[0][0], a[0][1] }, //
						{ a[1][0], b[1][1] }, //
						{ a[2][0], a[2][1] }, //
				};
			}
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

	List<long[][]> cut(long[][] a, long[][] b) {
		List<long[][]> ret = new ArrayList<>();
		if (same(a, b)) {
			return null;
		}
		for (int z = 0; z < 3; z++) {
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					long[][] cut = new long[3][2];
					cut[0][0] = x == 0 ? a[0][0] : x == 1 ? b[0][0] : b[0][1] + (b[0][1] == b[0][0] ? 0 : 1);
					cut[0][1] = x == 0 ? b[0][1] - (b[0][1] == b[0][0] ? 0 : 1) : x == 1 ? b[0][1] : a[0][1];

					cut[1][0] = y == 0 ? a[1][0] : y == 1 ? b[1][0] : b[1][1] + (b[1][1] == b[1][0] ? 0 : 1);
					cut[1][1] = y == 0 ? b[1][1] - (b[1][1] == b[1][0] ? 0 : 1) : y == 1 ? b[1][1] : a[1][1];

					cut[2][0] = z == 0 ? a[2][0] : z == 1 ? b[2][0] : b[2][1] + (b[2][1] == b[2][0] ? 0 : 1);
					cut[2][1] = z == 0 ? b[2][1] - (b[2][1] == b[2][0] ? 0 : 1) : z == 1 ? b[2][1] : a[2][1];

//					long[][] cut = new long[][] { //
//							{ //
//									x == 0 ? a[0][0] : (x == 2 ? 1 : 0) + b[0][x == 1 ? 0 : 1], //
//									x == 2 ? a[0][1] : (x == 0 ? -1 : 0) + b[0][x == 0 ? 0 : 1]//
//							}, { //
//									y == 0 ? a[1][0] : (y == 2 ? 1 : 0) + +b[1][y == 1 ? 0 : 1], //
//									y == 2 ? a[1][1] : (y == 0 ? -1 : 0) + b[1][y == 0 ? 0 : 1] //
//							}, { //
//									z == 0 ? a[1][0] : (z == 2 ? 1 : 0) + +b[2][z == 1 ? 0 : 1], //
//									z == 2 ? a[2][1] : (z == 0 ? -1 : 0) + b[2][z == 0 ? 0 : 1] //
//							} //
//					};
					boolean ignoreCut = false;
					for (int i = 0; i < 3; i++) {
						if (cut[i][0] > cut[i][1]) {
							ignoreCut = true;
							break;
						}
					}
					if (!ignoreCut) {
						ret.add(cut);
					}
				}
			}
		}
		for (int i = 0; i < ret.size(); i++) {
			if (null != intersect(b, ret.get(i))) {
				ret.remove(i);
				i--;
			}
		}
		merge(ret);
		return ret;
	}

	boolean same(long[][] a, long[][] b) {
		for (int i = 0; i < 3; i++) {
			if (a[i][0] != b[i][0] || a[i][1] != b[i][1]) {
				return false;
			}
		}
		return true;
	}

	@ToString
	@EqualsAndHashCode
	static class Arr<T> {
		T[] ay;

		@SafeVarargs
		public Arr(T... v) {
			ay = v;
		}
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

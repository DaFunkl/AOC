package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y18D23 extends Day {
	List<long[]> in = getInputList().stream() // pos=<89663068,44368890,80128768>, r=95149488
			.map(x -> x.substring(5).replace(">, r=", ",").split(",")).map(x -> new long[] { //
					Long.valueOf(x[0]), Long.valueOf(x[1]), Long.valueOf(x[2]), Long.valueOf(x[3]) //
			}).toList();

	@Override
	public Object part1() {
		long[] strongest = in.get(0);
		for (int i = 1; i < in.size(); i++) {
			if (in.get(i)[3] > strongest[3]) {
				strongest = in.get(i);
			}
		}
		int ret = 0;
		for (int i = 0; i < in.size(); i++) {
			var nb = in.get(i);
			long[] dis = { //
					nb[0] - strongest[0], //
					nb[1] - strongest[1], //
					nb[2] - strongest[2]//
			};
			if (manhattenDistance(dis) <= strongest[3]) {
				ret++;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		List<long[][]> cubes = new ArrayList<>();
		List<Integer> signals = new ArrayList<>();
		cubes.add(toCube(in.get(0)));
		signals.add(1);

		for (int i = 1; i < in.size(); i++) {
			var inb = toCube(in.get(i));

		}
		return null;
	}

	long[][] toCube(long[] c) {
		long[][] ret = new long[3][2];
		for (int i = 0; i < 3; i++) {
			ret[i][0] = c[i] - c[3];
			ret[i][0] = c[i] + c[3];
		}
		return ret;
	}

	long[][] overlap(long[][] a, long[][] b) {
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


	boolean same(long[][] a, long[][] b) {
		for (int i = 0; i < 3; i++) {
			if (a[i][0] != b[i][0] || a[i][1] != b[i][1]) {
				return false;
			}
		}
		return true;
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
						i--;
						changed = true;
						break;
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

	boolean isInside(long[][] a, long[][] b) {
		for (int i = 0; i < 3; i++) {
			if (!(a[i][0] <= b[i][0] && b[i][1] <= a[i][1])) {
				return false;
			}
		}
		return true;
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

	long manhattenDistance(long[] l) {
		return Math.abs(l[0]) + Math.abs(l[1]) + Math.abs(l[2]);
	}
}

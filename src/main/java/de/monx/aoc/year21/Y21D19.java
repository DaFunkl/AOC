package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class Y21D19 extends Day {

	List<List<int[][]>> scans = new ArrayList<>();
	List<int[][]> scansMM = new ArrayList<>();
	static final int _DIST = 1000;

	static final int _X = 0;
	static final int _Y = 1;
	static final int _Z = 2;

	static final int[] _SIDES = { 0, 4, 8, 12, 16, 20 };

	Set<IAW> merged = new HashSet<>();
	Map<Integer, int[]> scanPos = new HashMap<>();
	Map<Integer, Integer> scanRots = new HashMap<>();

	@Data
	@EqualsAndHashCode
	@AllArgsConstructor
	class IAW {
		int[] p;
	}

	@Override
	public Object part1() {
		init();
		findOverlaps();
//		merge = mergeOverlaps(overlaps);
		return merged.size();
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < scanPos.size(); i++) {
			for (int j = i + 1; j < scanPos.size(); j++) {
				ret = Math.max(ret, vertDist(subVert(scanPos.get(i), scanPos.get(j))));
			}
		}
		return ret;
	}

	void findOverlaps() {
		scanPos.put(0, new int[] { 0, 0, 0 });
		scanRots.put(0, 0);
		Map<Integer, Set<Integer>> tries = new HashMap<>();
		for (var s : scans.get(0)) {
			merged.add(new IAW(s[0]));
		}
		int fin = scans.size() - 1;
		while (scanPos.size() < scans.size()) {
			System.out.println(scanPos.size() + "/" + fin);
			for (int i = 1; i < scans.size(); i++) {
				if (scanPos.containsKey(i)) {
					continue;
				}
				tries.putIfAbsent(i, new HashSet<>());
				Set<Integer> keys = new HashSet<>(scanPos.keySet());
				int[][] dove = null;
				for (int j : keys) {
					if (tries.get(i).contains(j)) {
						continue;
					}
					dove = doOverlap(i, j);
					if (dove != null) {
						var jPos = scanPos.get(j);
						var iOff = addVert(jPos, dove[3]);
						scanPos.put(i, iOff);
						scanRots.put(i, dove[1][0]);
						for (var s : scans.get(i)) {
							merged.add(new IAW(addVert(s[dove[1][0]], iOff)));
						}
						break;
					} else {
						tries.get(i).add(j);
					}
				}
				if (dove != null) {
					break;
				}
			}
		}
	}

	int[][] doOverlap(int aIdx, int bIdx) {
		List<int[][]> scanA = scans.get(aIdx);
		List<int[][]> scanB = scans.get(bIdx);

		for (int rotationA = 0; rotationA < 24; rotationA++) { // for each possible Rotation of A
			for (int aBeaconIdx = 0; aBeaconIdx < scanA.size(); aBeaconIdx++) { // decide a guiding beacon from A
				int[] aBeacon = scanA.get(aBeaconIdx)[rotationA];
				for (int bBeaconIdx = 0; bBeaconIdx < scanB.size(); bBeaconIdx++) { // decide a guiding beacon from B
					int[] bBeacon = scanB.get(bBeaconIdx)[scanRots.get(bIdx)];
					var aOffset = subVert(bBeacon, aBeacon); // <-- offset B -> A
					int overlaps = 0; // count overlaps
					for (int vb = 0; vb < scanB.size() - (11 - overlaps); vb++) { // search in A more overlaps
						int[] bSubBeacon = scanB.get(vb)[scanRots.get(bIdx)];
						int[] aSubBeaconSearch = subVert(bSubBeacon, aOffset);
						for (int i = 0; i < scanA.size(); i++) {
							if (equalVert(aSubBeaconSearch, scanA.get(i)[rotationA])) {
								overlaps++; // overlap was found
								if (overlaps == 12) {
									return new int[][] { //
											{ aIdx, bIdx }, //
											{ rotationA, scanRots.get(bIdx) }, //
											{ aBeaconIdx, bBeaconIdx }, //
											{ aOffset[0], aOffset[1], aOffset[2] }, //
									};
								}
								break;
							}
						}
					}

				}
			}
		}
		return null;
	}

	int[] addVert(int[] a, int[] b) {
		return new int[] { //
				a[0] + b[0], //
				a[1] + b[1], //
				a[2] + b[2], //
		};
	}

	int[] subVert(int[] a, int[] b) {
		return new int[] { //
				a[0] - b[0], //
				a[1] - b[1], //
				a[2] - b[2], //
		};
	}

	int vertDist(int[] v) {
		return Math.abs(v[0]) + Math.abs(v[1]) + Math.abs(v[2]);
	}

	boolean equalVert(int[] a, int[] b) {
		return a[0] == b[0] && a[1] == b[1] && a[2] == b[2];
	}

	int[] rotate(int[] v, int rot) {
		return switch (rot) {
		case _X -> new int[] { v[0], -1 * v[2], v[1] };
		case _Y -> new int[] { v[2], v[1], -1 * v[0] };
		case _Z -> new int[] { -1 * v[1], v[0], v[2] };
		default -> throw new IllegalArgumentException("Unexpected value: " + rot);
		};
	}

	void init() {
		List<int[][]> l = new ArrayList<>();
		int[][] mm = null;
		for (var str : getInputList()) {
			if (str.startsWith("---")) {
				l = new ArrayList<>();
				mm = new int[][] { //
						{ Integer.MAX_VALUE, Integer.MIN_VALUE }, // mm x
						{ Integer.MAX_VALUE, Integer.MIN_VALUE }, // mm y
						{ Integer.MAX_VALUE, Integer.MIN_VALUE } // mm z
				};
			} else if (str.isBlank()) {
				scans.add(l);
				scansMM.add(mm);
			} else {
				var sar = str.split(",");
				int[] coords = new int[] { //
						Integer.valueOf(sar[0]), //
						Integer.valueOf(sar[1]), //
						Integer.valueOf(sar[2])//
				};
				int[][] lr = new int[24][3];
				// collect all possible Rotations for this coord
				for (int x = 0; x < 4; x++) { // Sides -> 0, 4, 8, 12
					lr[x * 4] = Arrays.copyOf(coords, 3);
					coords = rotate(coords, _X);
					for (int zy = 0; zy < 3; zy++) {
						coords = rotate(coords, x % 2 == 0 ? _Y : _Z);
						lr[(x * 4) + zy + 1] = Arrays.copyOf(coords, 3);
					}
					coords = rotate(coords, x % 2 == 0 ? _Y : _Z);
				}
				coords = rotate(coords, _Y);
				lr[16] = Arrays.copyOf(coords, 3);
				for (int x = 0; x < 3; x++) {
					coords = rotate(coords, _X);
					lr[17 + x] = Arrays.copyOf(coords, 3);
				}
				coords = rotate(coords, _X);
				coords = rotate(coords, _Y);
				coords = rotate(coords, _Y);
				lr[20] = Arrays.copyOf(coords, 3);
				for (int x = 0; x < 3; x++) {
					coords = rotate(coords, _X);
					lr[21 + x] = Arrays.copyOf(coords, 3);
				}
				l.add(lr);

				for (int i = 0; i < 3; i++) {
					mm[i][0] = Math.min(mm[i][0], coords[i]);
					mm[i][1] = Math.max(mm[i][1], coords[i]);
				}
			}
		}
		scans.add(l);
		scansMM.add(mm);

	}
}

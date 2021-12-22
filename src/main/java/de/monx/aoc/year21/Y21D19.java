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

	static final int _X = 0, _Y = 1, _Z = 2;
	List<List<int[][]>> scans = new ArrayList<>();
	List<int[][]> scansMM = new ArrayList<>();

	Set<IAW> merged = new HashSet<>();
	Map<Integer, int[]> scanNorm = new HashMap<>();

	Map<Integer, Set<Integer>> matches = new HashMap<>();

	@Data
	@EqualsAndHashCode
	@AllArgsConstructor
	class IAW {
		int[] p;
	}

	@Override
	public Object part1() {
		init();
		placeScanners();
		System.out.println("Matches: ");
		for (var ms : matches.entrySet()) {
			var sn = scanNorm.get(ms.getKey());
			System.out.println(
					ms.getKey() + " - " + Arrays.toString(sn) + " -> " + Arrays.toString(ms.getValue().toArray()));
			System.out.println("Scanner: " + ms.getKey() + " rotated[" + sn[3] + "] beacons: ");
			for (var b : scans.get(ms.getKey())) {
				System.out.println(Arrays.toString(b[sn[3]]));
			}
			System.out.println();
		}
		return merged.size();
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < scanNorm.size(); i++) {
			for (int j = i + 1; j < scanNorm.size(); j++) {
				ret = Math.max(ret, vertDist(subVert(scanNorm.get(i), scanNorm.get(j))));
			}
		}
		return ret;
	}

	void placeScanners() {
		scanNorm.put(0, new int[] { 0, 0, 0, 0 });
		Map<Integer, Set<Integer>> tries = new HashMap<>();
		for (var s : scans.get(0)) {
			merged.add(new IAW(s[0]));
		}
		int fin = scans.size() - 1;
		while (scanNorm.size() < scans.size()) { // search untill all Scanners have been placed
			System.out.println(scanNorm.size() + "/" + fin);
			for (int i = 1; i < scans.size(); i++) {
				if (scanNorm.containsKey(i)) {
					continue;
				} // tries is used to skips failed tries
				tries.putIfAbsent(i, new HashSet<>());
				int[][] overlap = null;
				for (int j : scanNorm.keySet()) {
					if (tries.get(i).contains(j)) {
						continue;
					}
					overlap = findOverlap(i, j);
					if (overlap != null) {
						matches.computeIfAbsent(i, k -> new HashSet<>()).add(j);
						matches.computeIfAbsent(j, k -> new HashSet<>()).add(i);

						var jNorm = scanNorm.get(j);
						var iOff = addVert(jNorm, overlap[3]);
						scanNorm.put(i, new int[] { iOff[0], iOff[1], iOff[2], overlap[1][0] });
						for (var s : scans.get(i)) {
							merged.add(new IAW(addVert(s[overlap[1][0]], iOff)));
						}
						break;
					} else {
						tries.get(i).add(j);
					}
				}
				if (overlap != null) {
					break;
				}
			}
		}
	}

	int[][] findOverlap(int aIdx, int bIdx) {
		List<int[][]> scanA = scans.get(aIdx);
		List<int[][]> scanB = scans.get(bIdx);

		for (int rotationA = 0; rotationA < 24; rotationA++) { // for each possible Rotation of A
			for (int aBeaconIdx = 0; aBeaconIdx < scanA.size(); aBeaconIdx++) { // decide a guiding beacon from A
				int[] aBeacon = scanA.get(aBeaconIdx)[rotationA];
				for (int bBeaconIdx = 0; bBeaconIdx < scanB.size(); bBeaconIdx++) { // decide a guiding beacon from B
					int[] bBeacon = scanB.get(bBeaconIdx)[scanNorm.get(bIdx)[3]];
					var aOffset = subVert(bBeacon, aBeacon); // <-- offset B -> A
					int overlaps = 0; // count overlaps
					for (int vb = 0; vb < scanB.size() - (11 - overlaps); vb++) { // search in A more overlaps
						int[] bSubBeacon = scanB.get(vb)[scanNorm.get(bIdx)[3]];
						int[] aSubBeaconSearch = subVert(bSubBeacon, aOffset);
						for (int i = 0; i < scanA.size(); i++) {
							if (equalVert(aSubBeaconSearch, scanA.get(i)[rotationA])) {
								overlaps++; // overlap was found
								if (overlaps == 12) {
									return new int[][] { //
											{ aIdx, bIdx }, //
											{ rotationA, scanNorm.get(bIdx)[3] }, //
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

package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D19 extends Day {

	List<List<int[][]>> scans = new ArrayList<>();
	List<int[][]> scansMM = new ArrayList<>();
	static final int _DIST = 1000;

	static final int _X = 0;
	static final int _Y = 1;
	static final int _Z = 2;

	static final int[] _SIDES = { 0, 4, 8, 12, 16, 20 };

	@Override
	public Object part1() {
		init();
		var overlaps = findOverlaps();

		for (var ov : overlaps) {
			System.out.println(Arrays.toString(ov[0]) + " | " + Arrays.toString(ov[1]));
		}
		int ret = 0;
		for (var scan : scans) {
			ret += scan.size();
		}
		ret -= (overlaps.size() * 12);
		return ret;
	}

	@Override
	public Object part2() {

		return null;
	}

	List<int[][]> findOverlaps() {
		List<int[][]> overlaps = new ArrayList<>();
		for (int i = 0; i < scans.size(); i++) {
			System.out.println(i + "/" + scans.size());
			for (int j = i + 1; j < scans.size(); j++) {
				var dove = doOverlap(i, j);
				if (dove != null) {
					overlaps.add(dove);
					continue;
				}
				dove = doOverlap(j, i);
				if (dove != null) {
					overlaps.add(dove);
				}
			}
		}
		return overlaps;
	}

	int[][] doOverlap(int aIdx, int bIdx) {
		List<int[][]> scanA = scans.get(aIdx);
		List<int[][]> scanB = scans.get(bIdx);

		for (int rotationA = 0; rotationA < 24; rotationA++) { // for each possible Rotation of A
			for (int aBeaconIdx = 0; aBeaconIdx < scanA.size(); aBeaconIdx++) { // decide a guiding beacon from A
				int[] aBeacon = scanA.get(aBeaconIdx)[rotationA];
				for (int bBeaconIdx = 0; bBeaconIdx < scanB.size(); bBeaconIdx++) { // decide a guiding beacon from B
					int[] bBeacon = scanB.get(bBeaconIdx)[0];

					var bOffset = subVert(aBeacon, bBeacon); // <-- offset A -> B

					// todo: check this out
//					var bOffsetInv = subVert(bBeacon, aBeacon);
//					if (vertDist(bOffsetInv) < vertDist(bOffset)) {
//						bOffset = bOffsetInv;
//					}

					int overlaps = 1; // count overlaps
					for (int va = 0; va < scanA.size(); va++) { // search in A more overlaps
						if (va == aBeaconIdx) {
							continue;
						}
						int[] aSubBeacon = scanA.get(va)[rotationA];
						// convert A-Beacon into B-Beacon coords, and look for it in B-Beacons
						int[] bSubBeaconSearch = subVert(aSubBeacon, bOffset);
//						System.out.println(Arrays.toString(aSubBeacon));
//						System.out.println(Arrays.toString(bOffset));
//						System.out.println(Arrays.toString(bSubBeaconSearch));
						for (int i = 0; i < scanB.size(); i++) {
							if (i == bBeaconIdx) {
								continue;
							}
//							System.out.println(Arrays.toString(bSubBeaconSearch));
//							System.out.println(Arrays.toString(scanB.get(i)[0]));
							if (equalVert(bSubBeaconSearch, scanB.get(i)[0])) {
								overlaps++; // overlap was found
								if (overlaps == 12) {
									return new int[][] { //
											{ aIdx, bIdx }, //
											{ rotationA, 0 } //
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

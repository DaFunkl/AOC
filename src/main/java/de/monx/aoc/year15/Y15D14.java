package de.monx.aoc.year15;

import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y15D14 extends Day {
	int seconds = 2503; // 2503;

	@Override
	public Object part1() {
		return getInputList().parallelStream().map(x -> x.split(" "))//
				.map(x -> new int[] { //
						Integer.valueOf(x[3]), // km/s
						Integer.valueOf(x[6]), // flytime
						Integer.valueOf(x[13]), // rest
				}).map(x -> new int[] { //
						x[1], // flytime
						x[0], // km/s
						(seconds / (x[1] + x[2])) * x[1], // ensured flight
						seconds % (x[1] + x[2]) // mod flight
				}).map(x -> x[1] * (x[2] + Math.min(x[0], x[3]))) //
				.mapToInt(Integer::intValue).max().getAsInt();
	}

	static final int KMS = 0;
	static final int FLYTIME = 1;
	static final int RESTTIME = 2;
	static final int DISTANCE = 3;
	static final int POINTS = 4;
	static final int TIME = 5;
	static final int STATE = 6;
	static final int STATE_FLY = 1;
	static final int STATE_REST = -1;

	@Override
	public Object part2() {
		var data = fetchData();

		for (int i = 0; i < seconds; i++) {
			// update
			for (int j = 0; j < data.size(); j++) {
				data.get(j)[TIME]++;
				if (data.get(j)[STATE] == STATE_FLY) {
					data.get(j)[DISTANCE] += data.get(j)[KMS];
					if (data.get(j)[TIME] == data.get(j)[FLYTIME]) {
						data.get(j)[STATE] = STATE_REST;
						data.get(j)[TIME] = 0;
					}
				}
				if (data.get(j)[TIME] == data.get(j)[RESTTIME]) {
					data.get(j)[STATE] = STATE_FLY;
					data.get(j)[TIME] = 0;
				}
			}
			// fetch Lead
			int maxDistance = data.parallelStream().map(x -> x[DISTANCE]).mapToInt(Integer::intValue).max().getAsInt();
			// apply points
			for (int j = 0; j < data.size(); j++) {
				if (data.get(j)[DISTANCE] == maxDistance) {
					data.get(j)[POINTS]++;
				}
			}
		}
		return data.parallelStream().map(x -> x[POINTS]).mapToInt(Integer::intValue).max().getAsInt();
	}

	List<int[]> fetchData() {
		return getInputList().parallelStream().map(x -> x.split(" "))//
				.map(x -> new int[] { //
						Integer.valueOf(x[3]), // km/s
						Integer.valueOf(x[6]), // flytime
						Integer.valueOf(x[13]), // rest
						0, // distance,
						0, // points,
						0, // time
						STATE_FLY // state
				}).collect(Collectors.toList());

	}

}

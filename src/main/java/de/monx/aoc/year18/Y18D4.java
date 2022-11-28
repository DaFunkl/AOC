package de.monx.aoc.year18;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y18D4 extends Day {
	private static final int _BS = 0;
	private static final int _FA = 1;
	private static final int _WU = 2;

//[1518-04-08 00:18] falls asleep
//[1518-09-20 23:59] Guard #2011 begins shift
//[1518-07-30 00:27] wakes up
	List<Log> in = getInputList().stream().map(x -> new Log(x)) //
			.sorted(new Comparator<Log>() {
				@Override
				public int compare(Log o1, Log o2) {
					return o1.ts.compareTo(o2.ts);
				}
			}).toList();

	Map<Integer, int[]> tt = new HashMap<>();

	@Override
	public Object part1() {
		Map<Integer, Integer> gs = new HashMap<>();
		int[] mgs = { 0, 0 };
		int logId = -1;
		Date faDate = null;
		Date wuDate = null;
		for (var i : in) {
			switch (i.state) {
			case _BS:
				logId = i.id;
				gs.putIfAbsent(logId, 0);
				break;
			case _FA:
				faDate = i.getTs();
				break;
			case _WU:
				wuDate = i.getTs();
				int delMin = (int) ((wuDate.getTime() - faDate.getTime()) / (1000 * 60));
				int delHour = delMin / 60;
				delMin = delMin - (delHour * 60);
				@SuppressWarnings("deprecation")
				int startMin = faDate.getMinutes();
				tt.putIfAbsent(logId, new int[60]);
				for (int m = startMin; m < startMin + delMin; m++) {
					tt.get(logId)[m % 60] += delHour + 1;
					gs.put(logId, gs.get(logId) + delHour + 1);
					if (mgs[0] < gs.get(logId)) {
						mgs[0] = gs.get(logId);
						mgs[1] = logId;
					}
				}
				break;
			}
		}
		int id = mgs[1];
		int[] mm = { -1, 0 };
		for (int i = 0; i < tt.get(id).length; i++) {
			if (mm[0] < tt.get(id)[i]) {
				mm[0] = tt.get(id)[i];
				mm[1] = i;
			}
		}
		return mm[1] * id;
	}

	@Override
	public Object part2() {
		int[] mm = { -1, -1, -1 };
		for (int i = 0; i < 60; i++) {
			for (int id : tt.keySet()) {
				if (mm[0] < tt.get(id)[i]) {
					mm[0] = tt.get(id)[i];
					mm[1] = id;
					mm[2] = i;
				}
			}
		}
		return mm[1] * mm[2];
	}

	@Data
	static class Log {
		String msg;
		Date ts;
		int state;
		int id;

		private static final SimpleDateFormat _SDF = new SimpleDateFormat("[yyyy-MM-dd hh:mm");

		public Log(String str) {
			msg = str;
			if (msg.endsWith("asleep")) {
				state = _FA;
			} else if (msg.endsWith("up")) {
				state = _WU;
			} else if (msg.endsWith("shift")) {
				state = _BS;
				id = Integer.valueOf(str.split("#")[1].split(" ")[0]);
			} else {
				System.err.println("Unknown Type: " + msg);
			}
			try {
				ts = _SDF.parse(str.split("] ")[0]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}

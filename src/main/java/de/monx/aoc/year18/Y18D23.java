package de.monx.aoc.year18;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.BronKerbosch;
import de.monx.aoc.util.Day;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

	long manhattenDistance(long[] l) {
		return Math.abs(l[0]) + Math.abs(l[1]) + Math.abs(l[2]);
	}

	@Override
	public Object part2() {
		List<Nanobot> bots = in.stream().map(x -> new Nanobot(x)).toList();
		Map<Nanobot, Set<Nanobot>> neighbours = new HashMap<>();
		for (var bot : bots) {
			Set<Nanobot> within = new HashSet<>();
			for (var wb : bots) {
				if (bot.withinRangeOfSharedPoint(wb) && !bot.equals(wb)) {
					within.add(wb);
				}
			}
			neighbours.put(bot, within);
		}
		var clique = new BronKerbosch<Nanobot>(neighbours).largestClique();
		long[] origin = { 0, 0, 0, 0 };
		return clique.stream().map(x -> x.distanceTo(origin) - x.bot[3]).max(Long::compare).get();
	}

	@Data
	@EqualsAndHashCode
	static class Nanobot {
		long[] bot;

		public Nanobot(long[] l) {
			bot = l;
		}

		boolean inRange(Nanobot o) {
			return distanceTo(o.bot) <= bot[3];
		}

		boolean withinRangeOfSharedPoint(Nanobot o) {
			return distanceTo(o.bot) <= bot[3] + o.bot[3];
		}

		long distanceTo(long[] o) {
			return Math.abs(bot[0] - o[0]) + Math.abs(bot[1] - o[1]) + Math.abs(bot[2] - o[2]);
		}
	}
}

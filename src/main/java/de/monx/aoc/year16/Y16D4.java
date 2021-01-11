package de.monx.aoc.year16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;
import de.monx.aoc.util.common.pairs.StrPair;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Y16D4 extends Day {
	List<StrPair> input = getInputList().stream() //
			.map(x -> x.substring(0, x.length() - 1).split("\\[")) //
			.map(x -> new StrPair(x[0], x[1])) //
			.collect(Collectors.toList());

	@Override
	public Object part1() {
		return input.stream() //
				.filter(this::isRoomValid) //
				.map(x -> fetchSectionId(x).second) //
				.reduce(0, (a, e) -> a + e);
	}

	@Override
	public Object part2() {
		return input.stream() //
				.filter(this::isRoomValid) //
				.map(this::decrpyt) //
				.filter(x -> x.first.equals("northpole object storage")) //
				.findFirst().get().second; //
	}

	StrPair decrpyt(StrPair sp) {
		var sid = fetchSectionId(sp);
		StringBuilder msg = new StringBuilder();
		for (char c : sid.first.toCharArray()) {
			msg.append(rotChar(c, sid.second));
		}
		var ret = new StrPair(msg.toString(), sid.second + "");
		return ret;
	}

	static final int minC = (int) 'a';

	char rotChar(char c, int sid) {
		if (c == '-') {
			return ' ';
		}
		int v = ((int) c) - minC;
		v = (v + sid) % 26;
		return (char) (v + minC);
	}

	Pair<String, Integer> fetchSectionId(StrPair sp) {
		var spl = sp.first.split("-");
		StringBuilder sb = new StringBuilder(spl[0]);
		for (int i = 1; i < spl.length - 1; i++) {
			sb.append("-");
			sb.append(spl[i]);
		}
		var sid = Integer.valueOf(spl[spl.length - 1]);
		return new Pair(sb.toString(), sid);
	}

	boolean isRoomValid(StrPair sp) {
		var map = fetchMap(sp);
		var car = sp.second.toCharArray();
		var prev = map.get(car[0]);
		for (int i = 1; i < car.length; i++) {
			var curr = map.get(car[i]); //@formatter:off
			if(prev.first == 0 || curr.first == 0) return false;
			if(prev.first < curr.first) return false;
			if(prev.first == curr.first && prev.second > curr.second) return false;
			prev = curr; //@formatter:on
		}
		return true;
	}

	Map<Character, IntPair> fetchMap(StrPair sp) {
		Map<Character, IntPair> map = new HashMap<>();
		for (var c : sp.second.toCharArray()) {
			map.put(c, new IntPair(0, (int) c));
		}
		var car = sp.first.toCharArray();
		for (int i = 0; i < car.length; i++) {
			var c = car[i];
			if (map.containsKey(c)) {
				map.get(c).first++;
			}
		}
		return map;
	}
}

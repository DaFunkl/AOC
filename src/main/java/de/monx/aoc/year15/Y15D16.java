package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y15D16 extends Day {

	List<List<Pair<String, Integer>>> data = getInputList().parallelStream() //
			.map(x -> x.replace(":", "").replace(",", "").split(" ")) //
			.map(x -> parseLine(x)).collect(Collectors.toList());

	@SuppressWarnings("serial")
	Map<String, Integer> aunt = new HashMap<>() {
		{
			put("children", 3);
			put("cats", 7);
			put("samoyeds", 2);
			put("pomeranians", 3);
			put("akitas", 0);
			put("vizslas", 0);
			put("goldfish", 5);
			put("trees", 3);
			put("cars", 2);
			put("perfumes", 1);
		}
	};

	@Override
	public Object part1() {
		return data.stream().filter(x -> matches1(x)).findFirst().get().get(0).second;
	}

	@Override
	public Object part2() {
		return data.stream().filter(x -> matches2(x)).findFirst().get().get(0).second;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	List<Pair<String, Integer>> parseLine(String[] x) {
		List<Pair<String, Integer>> ret = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			ret.add(new Pair(x[i * 2], Integer.valueOf(x[i * 2 + 1])));
		}
		return ret;
	}

	boolean matches1(List<Pair<String, Integer>> x) {
		for (int i = 1; i < x.size(); i++) {
			if (aunt.get(x.get(i).first) != x.get(i).second) {
				return false;
			}
		}
		return true;
	}

	boolean matches2(List<Pair<String, Integer>> x) {
		for (int i = 1; i < x.size(); i++) {
			var p = x.get(i);
			switch (p.first) { //@formatter:off
			case "cats": case "trees": 
				if (p.second <= aunt.get(p.first)) return false; 
				break;
			case "pomeranians": case "goldfish": 
				if (p.second >= aunt.get(p.first)) return false;
				break;
			default: if (p.second != aunt.get(p.first)) return false;
				break;
			} //@formatter:on
		}
		return true;
	}
}

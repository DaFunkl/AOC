package de.monx.aoc.year15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y15D5 extends Day {

	final Set<String> badStr = new HashSet<>() { //@formatter:off
		{ add("ab"); add("cd"); add("pq"); add("xy"); }	}; //@formatter:on

	final Set<Character> vowels = new HashSet<>() { //@formatter:off
		{ add('a'); add('e'); add('i'); add('o'); add('u');}	}; //@formatter:on

	@Override
	public Object part1() {
		return getInputList().parallelStream().filter(x -> isNiceStr1(x)).count();
	}

	boolean isNiceStr1(String s) { //@formatter:off
		for (var b : badStr) if (s.contains(b)) return false; 
		int vowelCount = 0;
		boolean tir = false;
		Character prev = null;
		for (var c : s.toCharArray()) {
			if(vowels.contains(c)) vowelCount++;
			if(prev != null && c == prev) tir = true;
			prev = c;
			if(vowelCount >= 3 && tir) return true;
		}
		return false;//@formatter:on
	}

	@Override
	public Object part2() {
		return getInputList().parallelStream().filter(x -> isNiceStr2(x)).count();
	}

	boolean isNiceStr2(String s) {
		char[] car = s.toCharArray();
		char[] prev = new char[] { '@', '!', '$' };
		boolean rule1 = false;
		boolean rule2 = false;
		for (int i = 0; i < car.length; i++) {
			prev[0] = prev[1];
			prev[1] = prev[2];
			prev[2] = car[i];
			String tw = prev[1] + "" + prev[2];
			if (i < s.length() - 2 && s.substring(i + 1).contains(tw))
				rule1 = true;
			if (prev[0] == prev[2])
				rule2 = true;
			if (rule1 && rule2)
				return true;
		}
		return false;
	}

}

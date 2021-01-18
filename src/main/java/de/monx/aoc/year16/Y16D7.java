package de.monx.aoc.year16;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y16D7 extends Day {

	List<String[]> input = getInputList().stream() //
			.map(x -> x.replace("]", "[").split("\\[")) //
			.collect(Collectors.toList());

	@Override
	public Object part1() {
		int count = 0;
		for (var ipa : input) {
			if (isValidP1(ipa)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Object part2() {
		int count = 0;
		for (var ipa : input) {
			if (isValidP2(ipa)) {
				count++;
			}
		}
		return count;
	}

	boolean isValidP2(String[] in) {
		Set<String> evenABAs = new HashSet<>();
		Set<String> unevenABAs = new HashSet<>();
		boolean even = true;
		for (String str : in) { //@formatter:off
			Set<String> abas = fetchAbas(str, even);
			if(even) evenABAs.addAll(abas); else unevenABAs.addAll(abas);
			for(var s : evenABAs) if(unevenABAs.contains(s)) return true;
			even = !even;
		}//@formatter:on
		return false;
	}

	Set<String> fetchAbas(String s, boolean even) {
		char[] accu = new char[3];
		Set<String> ret = new HashSet<>();
		for (int i = 0; i < s.length(); i++) {
			for (int c = 0; c < accu.length - 1; c++) {
				accu[c] = accu[c + 1];
			}
			accu[accu.length - 1] = s.charAt(i);
			if (i < 2)
				continue;
			String aba = isAba(accu, even);
			if (aba != null) {
				ret.add(aba);
			}
		}
		return ret;
	}

	String isAba(char[] car, boolean even) {
		if (car[0] == car[2] && car[0] != car[1]) {
			return even ? new String(car) : "" + car[1] + car[0] + car[1];
		}
		return null;
	}

	boolean isValidP1(String[] in) {
		boolean valid = false;
		for (int i = 0; i < in.length; i++) {
			boolean inBrackets = i % 2 == 1;
			if (containsAbba(in[i])) {
				if (!inBrackets) {
					valid = true;
				} else {
					return false;
				}
			}
		}
		return valid;
	}

	boolean containsAbba(String s) {
		char[] accu = new char[4];
		for (int i = 0; i < s.length(); i++) {
			for (int c = 0; c < accu.length - 1; c++) {
				accu[c] = accu[c + 1];
			}
			accu[accu.length - 1] = s.charAt(i);
			if (i >= 3 && isAbba(accu)) {
				return true;
			}
		}
		return false;
	}

	boolean isAbba(char[] in) {
		return in[0] != in[1] //
				&& in[0] == in[3] //
				&& in[1] == in[2]; //
	}

}

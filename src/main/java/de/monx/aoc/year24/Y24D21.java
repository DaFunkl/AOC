package de.monx.aoc.year24;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D21 extends Day {

	List<String> in = getInputList();
	static Map<Character, IntPair> pad1 = new HashMap<>();
	static Map<Character, IntPair> pad2 = new HashMap<>();
	static {
		Map<Character, IntPair> aMap = new HashMap<>();
		aMap.put('7', new IntPair(0, 0));
		aMap.put('8', new IntPair(0, 1));
		aMap.put('9', new IntPair(0, 2));
		aMap.put('4', new IntPair(1, 0));
		aMap.put('5', new IntPair(1, 1));
		aMap.put('6', new IntPair(1, 2));
		aMap.put('1', new IntPair(2, 0));
		aMap.put('2', new IntPair(2, 1));
		aMap.put('3', new IntPair(2, 2));
		aMap.put('0', new IntPair(3, 1));
		aMap.put('A', new IntPair(3, 2));
		pad1 = aMap;

		Map<Character, IntPair> bMap = new HashMap<>();
		bMap.put('^', new IntPair(0, 1));
		bMap.put('A', new IntPair(0, 2));
		bMap.put('<', new IntPair(1, 0));
		bMap.put('v', new IntPair(1, 1));
		bMap.put('>', new IntPair(1, 2));
		pad2 = bMap;
	}

	@Override
	public Object part1() {
		int ret = 0;
		for (var str : in) {
			char c1 = 'A';
			char c2 = 'A';
			char c3 = 'A';
			String m1 = "";
			String m2 = "";
			String m3 = "";
			for (var ca : str.toCharArray()) {
				var dlt1 = pad1.get(c1).sub(pad1.get(ca));
				var moves1 = toMoves(dlt1);
				m1 += moves1;
				c1 = ca;
				for (var cb : moves1.toCharArray()) {
					var dlt2 = pad2.get(c2).sub(pad2.get(cb));
					var moves2 = toMoves(dlt2);
					m2 += moves2;
					c2 = cb;
					for (var cc : moves1.toCharArray()) {
						var dlt3 = pad2.get(c3).sub(pad2.get(cc));
						var moves3 = toMoves(dlt3);
						m3 += moves3;
						c3 = cc;
					}
				}
			}
			ret += m2.length() * m3.length();
			System.out.println(str + " " + m2.length() + " * " + m3.length() + " = " + (m2.length() * m3.length())
					+ " => " + ret + " " + m1 + ", " + m2 + ", " + m3);
		}
		return ret;
	}

	@Override
	public Object part2() {
		return null;
	}

	String toMoves(IntPair ip) {
		String ret = "";
		int y = ip.first;
		int iy = y < 0 ? 1 : -1;
		int x = ip.second;
		int ix = x < 0 ? 1 : -1;
		while (y != 0) {
			ret += y < 0 ? '^' : 'v';
			y += iy;
		}
		while (x != 0) {
			ret += x < 0 ? '<' : '>';
			x += ix;
		}
		return ret + "A";
	}
}

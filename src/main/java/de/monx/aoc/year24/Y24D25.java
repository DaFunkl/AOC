package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D25 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		List<int[]> keys = new ArrayList<>();
		List<int[]> locks = new ArrayList<>();
		int key = 0;
		int[] ci = new int[5];
		for (var str : in) {
			if (str.isBlank()) {
				if (key > 0) {
					keys.add(ci);
				} else {
					locks.add(ci);
				}
				key = 0;
				continue;
			}
			if (key == 0) {
				key = str.charAt(0) == '.' ? 1 : -1;
				if (key < 0) {
					ci = new int[] { 6, 6, 6, 6, 6 };
				} else {
					ci = new int[] { -1, -1, -1, -1, -1 };
				}
				continue;
			}
			for (int i = 0; i < 5; i++) {
				if ((key > 0 && str.charAt(i) == '#') || (key < 0 && str.charAt(i) == '.')) {
					ci[i] += key;
				}
			}
		}
		if (key > 0) {
			keys.add(ci);
		} else {
			locks.add(ci);
		}
		int ret = 0;
		for (var l : locks) {
			for (var k : keys) {
				boolean overlap = false;
				for (int i = 0; i < 5; i++) {
					if (k[i] + l[i] > 5) {
						overlap = true;
						break;
					}
				}
				if (!overlap) {
					ret++;
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return "\n" // @formatter:off
				+ "             _/_  \\_   __\r\n"
				+ "              \\\\_ /    \\_/\r\n"
				+ "             ,<  ==-o  _|  *\r\n"
				+ "               \\ __,  /_)\r\n"
				+ "                \\_\\__//\r\n"
				+ "         *     /  ^\\-'\r\n"
				+ "              //\\   \\\r\n"
				+ "              \\\\ \\  ')\r\n"
				+ "              /_) ),-.\r\n"
				+ "                 //  \\\\\r\n"
				+ "                ((    ))  *\r\n"
				+ "                _\\\\  //_\r\n"
				+ "           . ..((__)(__))..b'ger"
				+ "\n";// @formatter:on
	}

}

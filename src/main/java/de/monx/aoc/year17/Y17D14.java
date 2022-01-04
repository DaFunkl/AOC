package de.monx.aoc.year17;

import java.util.Stack;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y17D14 extends Day {
	char[][] grid = new char[128][128];

	@Override
	public Object part1() {
		String prefix = getInputString();

		String[] hashes = new String[128];
		for (int i = 0; i < hashes.length; i++) {
			hashes[i] = Y17D10.knotHash(prefix + "-" + i, 64);
		}
		return count(hashes);
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '0') {
					continue;
				}
				ret++;
				Stack<IntPair> stack = new Stack<>();
				stack.push(new IntPair(i, j));
				while (!stack.isEmpty()) {
					var ip = stack.pop();
					if (grid[ip.first][ip.second] == '0') {
						continue;
					}
					grid[ip.first][ip.second] = '0';
					for (var d : Util._DIRS4) {
						var nip = d.add(ip);
						if (nip.first < 0 || nip.second < 0 || nip.first >= 128 || nip.second >= 128) {
							continue;
						}
						if (grid[nip.first][nip.second] == '1') {
							stack.push(nip);
						}
					}
				}
			}
		}
		return ret;
	}

	int count(String[] hxs) {
		int ret = 0;
		int i = 0;
		for (var h : hxs) {
			int j = 0;
			for (var hc : h.toCharArray()) {
				String rpl = hxc2str(hc);
				for (char c : rpl.toCharArray()) {
					if (c == '1') {
						ret++;
					}
					grid[i][j++] = c;
				}
			}
			i++;
		}
		return ret;
	}

	String hxc2str(char c) {
		return switch (c) {
		case '0' -> "0000";
		case '1' -> "0001";
		case '2' -> "0010";
		case '3' -> "0011";
		case '4' -> "0100";
		case '5' -> "0101";
		case '6' -> "0110";
		case '7' -> "0111";
		case '8' -> "1000";
		case '9' -> "1001";
		case 'a' -> "1010";
		case 'b' -> "1011";
		case 'c' -> "1100";
		case 'd' -> "1101";
		case 'e' -> "1110";
		case 'f' -> "1111";
		default -> null;
		};
	}
}

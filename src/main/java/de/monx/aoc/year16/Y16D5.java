package de.monx.aoc.year16;

import java.util.Arrays;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y16D5 extends Day {

	String input = getInputString();

	@Override
	public Object part1() {
		String ret = "";
		int i = 0;
		while (ret.length() < 8) {
			String hash = Util.md5Hash(input + i++);
			if (hash.startsWith("00000")) {
				ret += hash.charAt(5);
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		int i = 0;
		int replaced = 0;
		Character[] ret = new Character[] { null, null, null, null, null, null, null, null };
		while (replaced < 8) {
			String hash = Util.md5Hash(input + i++);
			if (hash.startsWith("00000")) {
				Integer num = Util.fetchNumber(hash.charAt(5) + "");
				if (num != null && num < 8 && ret[num] == null) {
					replaced++;
					ret[num] = hash.charAt(6);
				}
			}
		}
		return Arrays.stream(ret).map(x -> x + "").reduce("", (a, e) -> a + e);
	}

}

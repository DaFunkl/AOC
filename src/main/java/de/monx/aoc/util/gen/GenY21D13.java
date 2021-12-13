package de.monx.aoc.util.gen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenY21D13 {
	static Map<Character, String[]> charTable = new HashMap<>();
	static int height = 0;
	static final String path = "./lib/abcTable";

	public static void main(String[] args) throws IOException {
		fetchCharTable();
		String[] msgs = new String[] { //
				"WE'RE NO STRANGERS TO LOVE", //
				"YOU KNOW THE RULES AND SO DO I", //
				"A FULL COMMITMENT'S WHAT I'M THINKING OF", //
				"YOU WOULDN'T GET THIS FROM ANY OTHER GUY", //
				"I JUST WANNA TELL YOU HOW I'M FEELING", //
				"GOTTA MAKE YOU UNDERSTAND", //
				"NEVER GONNA GIVE YOU UP", //
				"NEVER GONNA LET YOU DOWN",//
		};

		var points = msgsToPoints(msgs);
		Collections.shuffle(points);
		printPoints(points);
	}

	static void printPoints(List<int[]> points) {
		for (var p : points) {
			System.out.println(p[1] + "," + p[0]);
		}
	}

	static void printMsgs(String[] msgs) {
		for (var msg : msgs) {
			System.out.println();
			for (var g : msgToArray(msg)) {
				System.out.println(g);
			}
		}
	}

	static List<int[]> msgsToPoints(String[] msgs) {
		List<int[]> dots = new ArrayList<>();
		for (int i = 0; i < msgs.length; i++) {
			String msg = msgs[i];
			String[] arr = msgToArray(msg);
			for (int j = 0; j < arr.length; j++) {
				for (int k = 0; k < arr[j].length(); k++) {
					if (arr[j].charAt(k) == '#') {
						dots.add(new int[] { (i * height + 1) + k, j });
					}
				}
			}
		}
		return dots;
	}

	static String[] msgToArray(String msg) {
		String[] ret = new String[height];
		for (char c : msg.toCharArray()) {
			String[] ct = charTable.get(c);
			for (int i = 0; i < height; i++) {
				if (ret[i] == null) {
					ret[i] = "";
				}
				ret[i] += ct[i];
			}
		}
		return ret;
	}

	static void fetchCharTable() throws IOException {
		List<String> lines = Files.lines(new File(path).toPath()).toList();
		height = lines.size();
		char curLetter = 'A';
		int start = 0;
		for (int i = 0; i < lines.get(0).length(); i++) {
			boolean skip = false;
			for (int j = 0; j < lines.size(); j++) {
				if (lines.get(j).charAt(i) != ' ') {
					skip = true;
					break;
				}
			}
			if (skip) {
				continue;
			}
			String[] table = new String[lines.size()];
			for (int j = 0; j < lines.size(); j++) {
				table[j] = lines.get(j).substring(start, i + 1);
			}
			charTable.put(curLetter, table);
			curLetter = (char) ((int) curLetter + 1);
			start = i + 1;
		}

		String[] wsTable = new String[lines.size()];
		for (var i = 0; i < wsTable.length; i++) {
			wsTable[i] = "    ";
		}
		charTable.put(' ', wsTable);

		String[] apostrophTable = new String[lines.size()];
		for (var i = 0; i < apostrophTable.length; i++) {
			if (i < apostrophTable.length / 2) {
				apostrophTable[i] = " #  ";
			} else {
				apostrophTable[i] = "    ";
			}
		}
		charTable.put('\'', apostrophTable);
	}
}

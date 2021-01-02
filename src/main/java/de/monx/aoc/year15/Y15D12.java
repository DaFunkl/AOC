package de.monx.aoc.year15;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import de.monx.aoc.util.Day;

public class Y15D12 extends Day {

	@Override
	public Object part1() {
		return solve1(getInputString());
//		return null;
	}

	@Override
	public Object part2() {
		var parser = new JsonParser();
		JsonElement je = parser.parse(getInputString());
		return solve2(je);
	}

	int solve2(JsonElement je) {
		int count = 0;
		if (je.isJsonArray()) {
			var arr = je.getAsJsonArray();
			for (var e : arr) {
				count += solve2(e);
			}
		} else if (je.isJsonObject()) {
			var obj = je.getAsJsonObject();
			var entries = obj.entrySet();
			for (var e : entries) {
				if (isRed(e.getValue())) {
					return 0;
				}
			}
			for (var e : entries) {
				count += solve2(e.getValue());
			}
		} else if (je.isJsonPrimitive()) {
			count += fetchInt(je);
		}
		return count;
	}

	boolean isRed(JsonElement je) {
		if (je.isJsonArray() || je.isJsonObject()) {
			return false;
		}
		var val = je.getAsString();
		if (val != null && val.equals("red")) {
			return true;
		}
		return false;
	}

	int fetchInt(JsonElement je) {
		try {
			return je.getAsInt();
		} catch (Exception e) {
			return 0;
		}
	}

	int solve1(String input) {
		input = input.replace("\"", ",");
		input = input.replace("[", ",");
		input = input.replace("]", ",");
		input = input.replace("{", ",");
		input = input.replace("}", ",");
		input = input.replace(":", ",");
		input = input.replaceAll("[a-z]+", ",");
		int l = 0;
		while (l != input.length()) {
			l = input.length();
			input = input.replaceAll(",,", ",");
		}
		String[] spl = input.split(",");
		int ret = 0;
		for (var s : spl) {
			if (s.isBlank()) {
				continue;
			}
			ret += Integer.valueOf(s);
		}
		return ret;
	}
}

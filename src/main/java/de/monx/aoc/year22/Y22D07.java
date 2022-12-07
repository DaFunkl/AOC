package de.monx.aoc.year22;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Y22D07 extends Day {

	List<String> in = getInputList();
	Dir root = null;
	Map<String, Integer> dirSizes = new HashMap<>();

	@Data
	@NoArgsConstructor
	static class Dir {
		String name;
		int size = 0;
		Map<String, Dir> dirs = new HashMap<>();
		Map<String, Integer> files = new HashMap<>();

		public Dir(String name) {
			this.name = name;
		}
	}

	@Override
	public Object part1() {
		int ret = 0;
		ArrayDeque<Dir> stack = new ArrayDeque<>();
		for (var i : in) {
			String[] arr = i.split(" ");
			if (i.startsWith("$ cd ..")) {
				Dir pop = stack.pop();
				dirSizes.put(pop.name, pop.size);
				if (pop.size <= 100000) {
					ret += pop.size;
				}
				stack.peek().size += pop.size;
			} else if (i.startsWith("$ cd")) {
				if (root == null) {
					root = new Dir(arr[2]);
					stack.push(root);
				} else {
					stack.push(stack.peek().getDirs().get(arr[2]));
				}
			} else if (i.startsWith("$ ls")) {

			} else if (i.startsWith("dir")) {
				stack.peek().dirs.put(arr[1], new Dir(arr[1]));
			} else {
				stack.peek().files.put(arr[1], Integer.valueOf(arr[0]));
				stack.peek().size += stack.peek().files.get(arr[1]);
			}
		}
		while (!stack.isEmpty()) {
			Dir pop = stack.pop();
			dirSizes.put(pop.name, pop.size);
			if (pop.size <= 100000) {
				ret += pop.size;
			}
			if (!stack.isEmpty()) {
				stack.peek().size += pop.size;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		int neededSpace = 30000000 - (70000000 - root.size);
		Pair<Integer, String> min = new Pair<>(70000000, "");
		for (var k : dirSizes.keySet()) {
			if (dirSizes.get(k) >= neededSpace && dirSizes.get(k) < min.first) {
				min.first = dirSizes.get(k);
				min.second = k;
			}
		}
		return min.first;
	}

}

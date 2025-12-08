package de.monx.aoc.year25;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y25D07 extends Day {
	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();
	IntPair ip = new IntPair(0, 0);
	int result_1 = 0;
	long result_2 = 1;

	@Override
	public Object part1() {
		for (int i = 0; i < in.get(0).length; i++) {
			if (in.get(0)[i] == 'S') {
				this.ip.second = i;
				break;
			}
		}
		ArrayDeque<Pair<IntPair, Long>> stack = new ArrayDeque<>();
		stack.add(new Pair<>(ip, 1l));
		while (!stack.isEmpty()) {
			Map<IntPair, Long> newPointers = new HashMap<>();
			while (!stack.isEmpty()) {
				var pair = stack.pop();
				var pointer = pair.first;
				if (pointer.first + 1 >= in.size()) {
					continue;
				}
				var c = in.get(pointer.first + 1)[pointer.second];
				if (c == '.') {
					var newPointer = pointer.add(1, 0);
					if (newPointers.containsKey(newPointer)) {
						newPointers.put(newPointer, newPointers.get(newPointer) + pair.second);
					} else {
						newPointers.put(newPointer, pair.second);
					}
				} else if (c == '^') {
					var newPointerLeft = pointer.add(1, -1);
					if (newPointers.containsKey(newPointerLeft)) {
						newPointers.put(newPointerLeft, newPointers.get(newPointerLeft) + pair.second);
					} else {
						newPointers.put(newPointerLeft, pair.second);
					}

					var newPointerRight = pointer.add(1, 1);
					if (newPointers.containsKey(newPointerRight)) {
						newPointers.put(newPointerRight, newPointers.get(newPointerRight) + pair.second);
					} else {
						newPointers.put(newPointerRight, pair.second);
					}
					result_2 += pair.second;
					result_1++;
				}
			}
			newPointers.entrySet().forEach(x -> stack.add(new Pair<>(x.getKey(), x.getValue())));
		}
		return result_1;
	}

	@Override
	public Object part2() {
		return result_2;
	}
}

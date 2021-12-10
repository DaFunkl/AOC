package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.monx.aoc.util.Day;

public class Y21D10 extends Day {

	List<String> in = getInputList();
	List<long[]> scores = new ArrayList<>();

	@Override
	public Object part1() {
		scores = in.stream().map(this::fetchScores).toList();
		return scores.stream().map(a -> a[0]).reduce(0l, (a, b) -> a + b);
	}

	@Override
	public Object part2() {
		var scores = this.scores.stream().filter(x -> x[0] == 0).map(x -> x[1]).sorted().toList();
		return scores.get(scores.size() / 2);
	}

	long[] fetchScores(String line) {
		Stack<Character> stack = new Stack<>();
		for (var c : line.toCharArray()) {
			if (c == '(' || c == '[' || c == '{' || c == '<') {
				stack.push(c); //@formatter:off
			} else switch (c) { 
				case ')': if(stack.pop() != '(') return new long[] {3,0}; break;
				case ']': if(stack.pop() != '[') return new long[] {57,0}; break;
				case '}': if(stack.pop() != '{') return new long[] {1197,0}; break;
				case '>': if(stack.pop() != '<') return new long[] {25137,0}; break;
				default: System.err.println("unexpected char: " + c);
			}//@formatter:on
		}
		long ret = 0;
		while (!stack.isEmpty()) {
			char c = stack.pop();
			switch (c) {//@formatter:off
				case '(': ret = ret * 5 + 1; break;
				case '[': ret = ret * 5 + 2; break;
				case '{': ret = ret * 5 + 3; break;
				case '<': ret = ret * 5 + 4; break;
				default: System.err.println("unexpected char: " + c);
			}
		}//@formatter:on
		return new long[] { 0, ret };
	}
}

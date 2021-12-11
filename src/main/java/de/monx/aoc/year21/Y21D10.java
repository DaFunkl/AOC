package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import de.monx.aoc.util.Day;

public class Y21D10 extends Day {

	List<String> in = getInputList();
	List<Long> p2 = new ArrayList<>();
	long p1 = 0;

	@Override
	public Object part1() {
		solve();
		return p1;
	}

	@Override
	public Object part2() {
		Collections.sort(p2);
		return p2.get(p2.size() / 2);
	}

	void solve() {
		for (var line : in) {
			var score = fetchScores(line);
			if (score[0] != 0) {
				p1 += score[0];
			} else {
				p2.add(score[1]);
			}
		}
	}

	long[] fetchScores(String line) {
		Stack<Character> stack = new Stack<>();
		for (var c : line.toCharArray()) {
			if (c == '(' || c == '[' || c == '{' || c == '<') {
				stack.push(c); //@formatter:off
			} else switch (c) { 
				case ')': if(stack.pop() != '(') return new long[] { 3, 0 }; break;
				case ']': if(stack.pop() != '[') return new long[] { 57, 0 }; break;
				case '}': if(stack.pop() != '{') return new long[] { 1197, 0 }; break;
				case '>': if(stack.pop() != '<') return new long[] { 25137, 0 }; break;
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

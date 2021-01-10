package de.monx.aoc.util.common.pairs;

import de.monx.aoc.util.common.Pair;

public class IntPair extends Pair<Integer, Integer> {

	public IntPair(Integer first, Integer second) {
		super(first, second);
	}

	public IntPair add(IntPair o) {
		return new IntPair(o.first + this.first, o.second + this.second);
	}

	public IntPair add(IntPair o, int a) {
		return new IntPair(o.first * a + this.first, o.second * a + this.second);
	}

	public void addi(IntPair o) {
		this.first += o.first;
		this.second += o.second;
	}

	public void addi(IntPair o, int a) {
		this.first += o.first * a;
		this.second += o.second * a;
	}

	public int manhattenDistance() {
		return Math.abs(first) + Math.abs(second);
	}
}

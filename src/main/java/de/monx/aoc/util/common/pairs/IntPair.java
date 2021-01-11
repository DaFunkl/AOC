package de.monx.aoc.util.common.pairs;

import de.monx.aoc.util.common.Pair;

public class IntPair extends Pair<Integer, Integer> {

	public IntPair() {
		super(0, 0);
	}

	public IntPair(int first, int second) {
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

	public void subi(IntPair o) {
		this.first -= o.first;
		this.second -= o.second;
	}

	public void addi(IntPair o, int a) {
		this.first += o.first * a;
		this.second += o.second * a;
	}

	public int manhattenDistance() {
		return Math.abs(first) + Math.abs(second);
	}

	@Override
	public IntPair clone() {
		return new IntPair(first, second);
	}

	public String strHash() {
		return first + "|" + second;
	}
}

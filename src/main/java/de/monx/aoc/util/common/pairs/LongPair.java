package de.monx.aoc.util.common.pairs;

import de.monx.aoc.util.common.Pair;

public class LongPair extends Pair<Long, Long> {

	public LongPair() {
		super(0L, 0L);
	}

	public LongPair(long first, long second) {
		super(first, second);
	}

	public LongPair(String first, String second) {
		super(Long.valueOf(first), Long.valueOf(second));
	}

	public LongPair add(LongPair o) {
		return new LongPair(o.first + this.first, o.second + this.second);
	}

	public LongPair add(LongPair o, long a) {
		return new LongPair(o.first * a + this.first, o.second * a + this.second);
	}

	public LongPair add(long a, long b) {
		return new LongPair(a + this.first, b + this.second);
	}

	public LongPair add(long a) {
		return add(a, a);
	}

	public void addi(LongPair o) {
		this.first += o.first;
		this.second += o.second;
	}

	public void addi(long o) {
		this.first += o;
		this.second += o;
	}

	public void addi(long a, long b) {
		this.first += a;
		this.second += b;
	}

	public void subi(LongPair o) {
		this.first -= o.first;
		this.second -= o.second;
	}

	public void addi(LongPair o, long a) {
		this.first += o.first * a;
		this.second += o.second * a;
	}

	@Override
	public LongPair clone() {
		return new LongPair(first, second);
	}

	public String strHash() {
		return first + "|" + second;
	}
}

package de.monx.aoc.util.common.pairs;

import de.monx.aoc.util.common.Pair;

public class IntPair extends Pair<Integer, Integer> {

	public IntPair() {
		super(0, 0);
	}

	public IntPair(int first, int second) {
		super(first, second);
	}

	public IntPair(String first, String second) {
		super(Integer.valueOf(first), Integer.valueOf(second));
	}

	public IntPair sub(IntPair o) {
		return new IntPair(o.first - this.first, o.second - this.second);
	}

	public IntPair add(IntPair o) {
		return new IntPair(o.first + this.first, o.second + this.second);
	}

	public IntPair add(IntPair o, int a) {
		return new IntPair(o.first * a + this.first, o.second * a + this.second);
	}

	public IntPair add(int a, int b) {
		return new IntPair(a + this.first, b + this.second);
	}

	public IntPair add(int a) {
		return add(a, a);
	}

	public int[] arr() {
		return new int[] { first, second };
	}

	public boolean bothEQ(int o) {
		return first == o && second == o;
	}

	public void addi(IntPair o) {
		this.first += o.first;
		this.second += o.second;
	}

	public void addi(int o) {
		this.first += o;
		this.second += o;
	}

	public void addi(int a, int b) {
		this.first += a;
		this.second += b;
	}

	public void subi(IntPair o) {
		this.first -= o.first;
		this.second -= o.second;
	}

	public void addi(IntPair o, int a) {
		this.first += o.first * a;
		this.second += o.second * a;
	}

	public void muli(int o) {
		this.first *= o;
		this.second *= o;
	}

	public IntPair mul(int o) {
		return new IntPair(o * this.first, o * this.second);
	}

	public void seti(IntPair o) {
		first = o.first;
		second = o.second;
	}

	public void revi() {
		int a = first;
		first = second;
		second = a;
	}

	public IntPair maxVals(IntPair o) {
		return new IntPair(Math.max(first, o.first), Math.max(second, o.second));
	}

	public void iMaxVals(IntPair o) {
		first = Math.max(first, o.first);
		second = Math.max(second, o.second);
	}

	public int manhattenDistance() {
		return Math.abs(first) + Math.abs(second);
	}

	public int manhattenDistance(IntPair o) {
		return this.sub(o).manhattenDistance();
	}

	@Override
	public IntPair clone() {
		return new IntPair(first, second);
	}

	public String strHash() {
		return first + "|" + second;
	}

	@Override
	public String toString() {
		return "[" + first + "," + second + "]";
	}

//	@Override
//	public boolean equals(Object o) {
//		if (o == this) {
//			return true;
//		}
//		if (!(o instanceof IntPair)) {
//			return false;
//		}
//		IntPair op = (IntPair) o;
//		return first == op.first && second == op.second;
//	}
//
//	@Override
//	public int hashCode() {
//		return (7 * 31 + first) * 31 + second;
//	}
}

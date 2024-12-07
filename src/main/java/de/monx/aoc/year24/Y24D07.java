package de.monx.aoc.year24;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D07 extends Day {

	List<BigInteger[]> in = getInputList().stream().map(x -> //
	Arrays.stream(x.replace(":", "").split(" ")).map(y -> new BigInteger(y)).toArray(BigInteger[]::new) //
	).toList();

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	BigInteger solve(boolean p2) {
		BigInteger sum = new BigInteger("0");
		for (var x : in) {
			List<BigInteger> vals = new ArrayList<>();
			vals.add(x[1]);
			for (int i = 2; i < x.length; i++) {
				List<BigInteger> nv = new ArrayList<>();
				for (var val : vals) {
					var a = val.add(x[i]);
					if (a.compareTo(x[0]) <= 0) {
						nv.add(a);
					}
					var m = val.multiply(x[i]);
					if (m.compareTo(x[0]) <= 0) {
						nv.add(m);
					}
					if (p2) {
						var c = new BigInteger(val.toString() + x[i].toString());
						if (c.compareTo(x[0]) <= 0) {
							nv.add(c);
						}
					}
				}
				vals = nv;
			}
			for (var val : vals) {
				if (val.equals(x[0])) {
					sum = sum.add(x[0]);
					break;
				}
			}
		}
		return sum;
	}
}

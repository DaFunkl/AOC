package de.monx.aoc.year23;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D24 extends Day {

//12, 31, 28 @ -1, -2, -1
	List<BigDecimal[]> in = getInputList().stream()//
			.map(x -> x.replace("@", ",").replaceAll("\\s", "").split(",")) //
			.map(x -> new BigDecimal[] { //
					new BigDecimal(x[0]), new BigDecimal(x[1]), new BigDecimal(x[2]), //
					new BigDecimal(x[3]), new BigDecimal(x[4]), new BigDecimal(x[5]) //
			}).toList();

	@Override
	public Object part1() {
		int ret = 0;
		BigDecimal[] area = { new BigDecimal("200000000000000"), new BigDecimal("400000000000000") };
		for (int i = 0; i < in.size(); i++) {
			for (int j = i + 1; j < in.size(); j++) {
				if (intersect(in.get(i), in.get(j), area)) {
					ret++;
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return null;
	}

	static final BigDecimal MO = new BigDecimal("-1");
	static final BigDecimal ZE = new BigDecimal("0");

	boolean intersect(BigDecimal[] bd1, BigDecimal[] bd2, BigDecimal[] area) {
		var d1 = bd1[3].divide(bd2[3], 3, RoundingMode.HALF_UP);
		var d2 = bd1[4].divide(bd2[4], 3, RoundingMode.HALF_UP);
		if (d1.equals(d2)) {
			return false;
		}
		var mula = getMul(bd1, bd2);
		if (mula.compareTo(ZE) < 0) {
			return false;
		}
		var mulb = getMul(bd2, bd1);
		if (mulb.compareTo(ZE) < 0) {
			return false;
		}
		var x = mula.multiply(bd2[3]).add(bd2[0]);
		var y = mula.multiply(bd2[4]).add(bd2[1]);
		x = mulb.multiply(bd1[3]).add(bd1[0]);
		y = mulb.multiply(bd1[4]).add(bd1[1]);
		boolean crossed = area[0].compareTo(x) <= 0 && x.compareTo(area[1]) <= 0 //
				&& area[0].compareTo(y) <= 0 && y.compareTo(area[1]) <= 0;
		return crossed;
	}

	BigDecimal getMul(BigDecimal[] bd1, BigDecimal[] bd2) {
		BigDecimal[][] mat = new BigDecimal[2][3];
		mat[0][2] = bd2[0].subtract(bd1[0]);
		mat[1][2] = bd2[1].subtract(bd1[1]);

		mat[0][0] = bd1[3];
		mat[1][0] = bd1[4];

		mat[0][1] = bd2[3].multiply(MO);
		mat[1][1] = bd2[4].multiply(MO);
		BigDecimal mula = mat[0][2].multiply(MO).multiply(mat[1][0])//
				.add(mat[1][2].multiply(mat[0][0])).divide( //
						(MO.multiply(mat[0][1].multiply(mat[1][0]))) //
								.add(mat[0][0].multiply(mat[1][1])) //
						, 3, RoundingMode.HALF_UP);
		return mula;
	}
}

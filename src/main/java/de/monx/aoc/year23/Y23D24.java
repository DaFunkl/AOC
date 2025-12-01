package de.monx.aoc.year23;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

	static final BigDecimal MO = new BigDecimal("-1");
	static final BigDecimal ONE = new BigDecimal("1");
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
		BigDecimal mul = mat[0][2].multiply(MO).multiply(mat[1][0])//
				.add(mat[1][2].multiply(mat[0][0])).divide( //
						(MO.multiply(mat[0][1].multiply(mat[1][0]))) //
								.add(mat[0][0].multiply(mat[1][1])) //
						, 3, RoundingMode.HALF_UP);
		return mul;
	}

	// shamelessly pulled p2 from:
	// https://github.com/zebalu/advent-of-code-2023/blob/master/aoc2023/src/main/java/io/github/zebalu/aoc2023/days/Day24.java#L8
	@Override
	public Object part2() {

		var m2 = createBDLinearMatrix(find3UsableHails(in));
		solve(m2);
		var x2 = m2[0][6];
		var y2 = m2[1][6];
		var z2 = m2[2][6];
		return x2.add(y2).add(z2).setScale(0, RoundingMode.HALF_EVEN).longValue();
	}

	private static final int PRECISION_100 = 100;

	private static void solve(BigDecimal[][] c) {
		for (int row = 0; row < c.length; row++) {
			// 1. set c[row][row] equal to 1
			BigDecimal factor = c[row][row];
			for (int col = 0; col < c[row].length; col++) {
				c[row][col] = c[row][col].divide(factor, PRECISION_100, RoundingMode.HALF_EVEN);
			}

			// 2. set c[row][row2] equal to 0
			for (int row2 = 0; row2 < c.length; row2++) {
				if (row2 != row) {
					BigDecimal factor2 = c[row2][row].negate();
					for (int col = 0; col < c[row2].length; col++) {
						c[row2][col] = c[row2][col].add(factor2.multiply(c[row][col]));
					}
				}
			}
		}
	}

	private static BigDecimal[][] createBDLinearMatrix(List<BigDecimal[]> hails) {
		if (hails.size() != 3) {
			throw new IllegalArgumentException("It can only work with 3 hails; got: " + hails.size());
		}

		BigDecimal zero = BigDecimal.ZERO;

		BigDecimal[] a = hails.get(0);
		BigDecimal[] b = hails.get(1);
		BigDecimal[] c = hails.get(2);
//@formatter:off
        BigDecimal[][] result = {
                {a[4].subtract(b[4]),          a[3].subtract(b[3]).negate(), zero,                         a[1].subtract(b[1]).negate(), a[0].subtract(b[0]),          zero,                         b[1].multiply(b[3]).subtract(b[0].multiply(b[4])).subtract(a[1].multiply(a[3]).subtract(a[0].multiply(a[4])))}, //
                {a[4].subtract(c[4]),          a[3].subtract(c[3]).negate(), zero,                         a[1].subtract(c[1]).negate(), a[0].subtract(c[0]),          zero,                         c[1].multiply(c[3]).subtract(c[0].multiply(c[4])).subtract(a[1].multiply(a[3]).subtract(a[0].multiply(a[4])))}, //
                {a[5].subtract(b[5]).negate(), zero,                         a[3].subtract(b[3]),          a[2].subtract(b[2]),          zero,                         a[0].subtract(b[0]).negate(), b[0].multiply(b[5]).subtract(b[2].multiply(b[3])).subtract(a[0].multiply(a[5]).subtract(a[2].multiply(a[3])))}, //
                {a[5].subtract(c[5]).negate(), zero,                         a[3].subtract(c[3]),          a[2].subtract(c[2]),          zero,                         a[0].subtract(c[0]).negate(), c[0].multiply(c[5]).subtract(c[2].multiply(c[3])).subtract(a[0].multiply(a[5]).subtract(a[2].multiply(a[3])))}, //
                {zero,                         a[5].subtract(b[5]),          a[4].subtract(b[4]).negate(), zero,                         a[2].subtract(b[2]).negate(), a[1].subtract(b[1]),          b[2].multiply(b[4]).subtract(b[1].multiply(b[5])).subtract(a[2].multiply(a[4]).subtract(a[1].multiply(a[5])))}, //
                {zero,                         a[5].subtract(c[5]),          a[4].subtract(c[4]).negate(), zero,                         a[2].subtract(c[2]).negate(), a[1].subtract(c[1]),          c[2].multiply(c[4]).subtract(c[1].multiply(c[5])).subtract(a[2].multiply(a[4]).subtract(a[1].multiply(a[5])))} //
        };
//@formatter:on
		return result;
	}

	private static List<BigDecimal[]> find3UsableHails(List<BigDecimal[]> hails) {
		List<BigDecimal[]> result = new ArrayList<>();
		for (int aInd = 0; aInd < hails.size() - 2 && result.size() < 3; ++aInd) {
			BigDecimal[] a = hails.get(aInd);
			for (int bInd = aInd + 1; bInd < hails.size() - 1 && result.size() < 3; ++bInd) {
				BigDecimal[] b = hails.get(bInd);
				if (a[3] == b[3] && a[4] == b[4] && a[5] == b[5]) {
					continue;
				}
				for (int cInd = bInd + 1; cInd < hails.size() && result.size() < 3; ++cInd) {
					BigDecimal[] c = hails.get(cInd);
					if (a[3] == c[3] && a[4] == c[4] && a[5] == c[5]) {
						continue;
					}
					if (b[3] == c[3] && b[4] == c[4] && b[5] == c[5]) {
						continue;
					}
					result.add(a);
					result.add(b);
					result.add(c);
				}
			}
		}
		if (result.size() < 3) {
			throw new NoSuchElementException("Can not find 3 usable hails");
		}
		return result;
	}
}

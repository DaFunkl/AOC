package de.monx.aoc.year17;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y17D15 extends Day {
	List<String> in = getInputList();
	long ga = Long.valueOf(in.get(0).split(" ")[4]);
	long gb = Long.valueOf(in.get(1).split(" ")[4]);
	long fa = 16807;
	long fb = 48271;
	long mod = 2147483647;

	@Override
	public Object part1() {
		long ga = this.ga;
		long gb = this.gb;
		long ret = 0;
		for (long i = 0; i < 40_000_000l; i++) {
			ga = (ga * fa) % mod;
			gb = (gb * fb) % mod;
			if (bin16(ga).equals(bin16(gb))) {
				ret++;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		long ga = this.ga;
		long gb = this.gb;
		long ret = 0;
		for (long i = 0; i < 5_000_000l; i++) {
			ga = (ga * fa) % mod;
			while (ga % 4 != 0) {
				ga = (ga * fa) % mod;
			}
			gb = (gb * fb) % mod;
			while (gb % 8 != 0) {
				gb = (gb * fb) % mod;
			}
			if (bin16(ga).equals(bin16(gb))) {
				ret++;
			}
		}
		return ret;
	}

	String bin16(long nr) {
		String bin = Long.toBinaryString(nr);
		if (bin.length() > 16) {
			bin = bin.substring(bin.length() - 16);
		} else {
			while (bin.length() < 16) {
				bin = "0" + bin;
			}
		}
		return bin;
	}

}

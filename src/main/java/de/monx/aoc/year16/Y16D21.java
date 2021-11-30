package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y16D21 extends Day {
	static final int _SP = 0;
	static final int _SL = 1;
	static final int _RL = 2;
	static final int _RR = 3;
	static final int _RB = 4;
	static final int _RP = 5;
	static final int _MP = 6;

	List<Instruction> opl = new ArrayList<>();

	@Override
	public Object part1() {
		opl = getInputList().stream().map(x -> new Instruction(x)).collect(Collectors.toList());
		String passcode = "abcdefgh";
		for (var op : opl) {
			passcode = op.operate(passcode);
		}
		return passcode;
	}

	@Override
	public Object part2() {
		String passcode = "fbgdceah";
		for (int i = opl.size() - 1; i >= 0; i--) {
			passcode = opl.get(i).reverse(passcode);
		}
		return passcode;
	}

	class Instruction {
		int instr;
		int ix;
		int iy;
		String cx;
		String cy;

		public Instruction(String s) {
			var spl = s.split(" ");
			if (s.startsWith("swap position")) {
				instr = _SP;
				ix = Integer.valueOf(spl[2]);
				iy = Integer.valueOf(spl[5]);
			} else if (s.startsWith("swap letter")) {
				instr = _SL;
				cx = spl[2];
				cy = spl[5];
			} else if (s.startsWith("rotate left")) {
				instr = _RL;
				ix = Integer.valueOf(spl[2]);
			} else if (s.startsWith("rotate right ")) {
				instr = _RR;
				ix = Integer.valueOf(spl[2]);
			} else if (s.startsWith("rotate based")) {
				instr = _RB;
				cx = spl[6];
			} else if (s.startsWith("reverse")) {
				instr = _RP;
				ix = Integer.valueOf(spl[2]);
				iy = Integer.valueOf(spl[4]);
			} else if (s.startsWith("move")) {
				instr = _MP;
				ix = Integer.valueOf(spl[2]);
				iy = Integer.valueOf(spl[5]);
			}
		}

		String operate(String s) {
			switch (instr) {
			case _SP:
				char[] car = s.toCharArray();
				char t = car[ix];
				car[ix] = car[iy];
				car[iy] = t;
				return new String(car);
			case _SL:
				s = s.replace(cx, "@");
				s = s.replace(cy, cx);
				return s.replace("@", cy);
			case _RL:
				return Util.leftrotate(s, ix);
			case _RR:
				return Util.rightrotate(s, ix);
			case _RB:
				return rb(s, cx);
			case _RP:
				String aRP = s.substring(0, ix);
				StringBuilder bRP = new StringBuilder(s.substring(ix, iy + 1));
				String cRP = s.substring(iy + 1);
				return aRP + bRP.reverse().toString() + cRP;
			case _MP:
				char cMP = s.charAt(ix);
				String aMP = s.substring(0, ix);
				s = aMP + s.substring(ix + 1);
				aMP = s.substring(0, iy);
				return aMP + cMP + s.substring(iy);
			}
			return s;
		}

		String reverse(String s) {
			switch (instr) {
			case _SP:
				char[] car = s.toCharArray();
				char t = car[ix];
				car[ix] = car[iy];
				car[iy] = t;
				return new String(car);
			case _SL:
				s = s.replace(cx, "@");
				s = s.replace(cy, cx);
				return s.replace("@", cy);
			case _RL:
				return Util.rightrotate(s, ix);
			case _RR:
				return Util.leftrotate(s, ix);
			case _RB:
				String ns = s;
				while (!rb(ns, cx).equals(s)) {
					ns = Util.leftrotate(ns, 1);
				}
				return ns;
			case _RP:
				String aRP = s.substring(0, ix);
				StringBuilder bRP = new StringBuilder(s.substring(ix, iy + 1));
				String cRP = s.substring(iy + 1);
				return aRP + bRP.reverse().toString() + cRP;
			case _MP:
				char cMP = s.charAt(iy);
				String aMP = s.substring(0, iy);
				s = aMP + s.substring(iy + 1);
				aMP = s.substring(0, ix);
				return aMP + cMP + s.substring(ix);
			}
			return s;
		}

		String rb(String s, String cx) {
			int idx = s.indexOf(cx);
			s = Util.rightrotate(s, 1);
			if (idx > 0) {
				s = Util.rightrotate(s, idx);
			}
			if (idx >= 4) {
				s = Util.rightrotate(s, 1);
			}
			return s;
		}
	}
}

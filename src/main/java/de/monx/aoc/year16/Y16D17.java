package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.pairs.IntPair;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Y16D17 extends Day {

	final static int _U_IDX = 0;
	final static int _D_IDX = 1;
	final static int _L_IDX = 2;
	final static int _R_IDX = 3;
	final static String _U_STR = "U";
	final static String _D_STR = "D";
	final static String _L_STR = "L";
	final static String _R_STR = "R";
	final static Set<Character> _CHARS_OPEN = Set.of('b', 'c', 'd', 'e', 'f');

	final String passcode = getInputString();

	@Override
	public Object part1() {
		List<State> states = new ArrayList<>();
		states.add(new State(new IntPair(0, 0), passcode, 0));
		var goal = new IntPair(3, 3);
		while (!states.isEmpty()) {
			var step = states.get(0);
			states.remove(0);

			var doors = fetchDoors(step.passcode);
			for (int i = 0; i < doors.length; i++) {
				if (doors[i]) {
					var move = step.move(i);
					if (move != null) {
						if (move.pos.equals(goal)) {
							return move.passcode.substring(passcode.length());
						}
						states.add(move);
					}
				}
			}
		}
		return "Error, couldn't find a solution.";
	}

	@Override
	public Object part2() {
		List<State> states = new ArrayList<>();
		states.add(new State(new IntPair(0, 0), passcode, 0));
		var goal = new IntPair(3, 3);
		int maxSteps = 0;
		while (!states.isEmpty()) {
			var step = states.get(0);
			states.remove(0);

			var doors = fetchDoors(step.passcode);
			for (int i = 0; i < doors.length; i++) {
				if (doors[i]) {
					var move = step.move(i);
					if (move != null) {
						if (move.pos.equals(goal)) {
							maxSteps = move.steps;
//							return move.passcode.substring(passcode.length());
						} else {
							states.add(move);
						}
					}
				}
			}
		}
		return maxSteps;
	}

	boolean[] fetchDoors(String code) {
		String hash = Util.md5Hash(code).toLowerCase().substring(0, 4);
		boolean[] ret = new boolean[4];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = _CHARS_OPEN.contains(hash.charAt(i));
		}
		return ret;
	}

	@Data
	@AllArgsConstructor
	class State {
		IntPair pos;
		String passcode;
		int steps = 0;

		public State() {
		}

		State move(int dir) {
			State s = new State();
			s.passcode = passcode;
			s.steps = steps + 1;
			switch (dir) { //@formatter:off
			case _U_IDX: s.pos = pos.add(-1,  0); s.passcode += _U_STR; break;
			case _D_IDX: s.pos = pos.add( 1,  0); s.passcode += _D_STR; break;
			case _L_IDX: s.pos = pos.add( 0, -1); s.passcode += _L_STR; break;
			case _R_IDX: s.pos = pos.add( 0,  1); s.passcode += _R_STR; break;
			} //@formatter:on
			if (s.isPosValid()) {
				return s;
			}
			return null;
		}

		boolean isPosValid() { //@formatter:off
			if(pos.first < 0) return false;
			if(pos.second < 0) return false;
			if(pos.first >= 4) return false;
			if(pos.second >= 4) return false;
			return true;
		} //@formatter:on
	}
}

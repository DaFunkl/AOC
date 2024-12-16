package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D15 extends Day {

	List<String> in = getInputList();
	List<String> grid = new ArrayList<>();
	List<String> mgr = new ArrayList<>();
	List<String> moves = new ArrayList<>();

	IntPair start = null;
	Set<IntPair> boxes1 = new HashSet<>();
	Set<IntPair> boxes2 = new HashSet<>();
	Map<Character, IntPair> _DIRS = new HashMap<>();

	@Override
	public Object part1() {
		init();
		var boxes = boxes1;
		var cp = start.clone();
		print(boxes, cp, '.', false);
		for (var move : moves) {
			for (var c : move.toCharArray()) {
				var d = _DIRS.get(c);
				var np = cp.add(d);
				if (getChar(np, false) == '#') {
//					continue;
				} else if (!boxes.contains(np)) {
					cp = np;
				} else {
					var nnp = np.clone();
					while (boxes.contains(nnp)) {
						nnp.addi(d);
					}
					if (getChar(nnp, false) != '#') {
						cp = np;
						boxes.remove(np);
						boxes.add(nnp);
					}
				}
				print(boxes, cp, c, false);
			}
		}
		return boxes.stream().mapToInt(x -> x.first * 100 + x.second).sum();
	}

	@Override
	public Object part2() {
		var boxes = boxes2;
		var cp = start.clone();
		cp.second *= 2;
		print(boxes, cp, '.', true);
		for (var move : moves) {
			for (var c : move.toCharArray()) {
				var d = _DIRS.get(c);
				var np = cp.add(d);
				if (getChar(np, true) == '#') {
//					continue;
				} else {
					Set<IntPair> rem = new HashSet<>();
					Set<IntPair> add = new HashSet<>();
					if (c == '^' || c == 'v') {
						var dnp = np.add(0, -1);
						if (!boxes.contains(np) && !boxes.contains(dnp)) {
							cp = np;
						} else {
							boolean canMove = true;
							ArrayDeque<IntPair> todos = new ArrayDeque<>();
							todos.add(boxes.contains(np) ? np : dnp);
							while (canMove && !todos.isEmpty()) {
								var c1 = todos.pop();
								var c2 = c1.add(0, 1);
								if (getChar(c1.add(d), true) == '#' || getChar(c2.add(d), true) == '#') {
									canMove = false;
									break;
								}
								rem.add(c1);
								add.add(c1.add(d));
								for (var ips : new IntPair[] { c1.add(d.first, -1), c1.add(d), c2.add(d) }) {
									if (boxes.contains(ips)) {
										todos.add(ips);
									}
								}
							}
							if (canMove) {
								cp = np;
								boxes.removeAll(rem);
								boxes.addAll(add);
							}
						}
					} else {
						var dd = d.clone();
						if (c == '<') {
							dd.addi(d);
						}
						var nnp = cp.add(dd);
						if (!boxes.contains(nnp)) {
							cp = np;
						} else {
							while (boxes.contains(nnp)) {
								rem.add(nnp.clone());
								nnp.addi(d);
								add.add(nnp.clone());
								nnp.addi(d);
							}
							if (getChar(c == '>' ? nnp : nnp.add(0, 1), true) != '#') {
								cp = np;
								boxes.removeAll(rem);
								boxes.addAll(add);
							}
						}
					}

				}
				print(boxes, cp, c, true);
			}
		}
		return boxes.stream().mapToInt(x -> x.first * 100 + x.second).sum();
	}

	boolean print = false;

	private void print(Set<IntPair> boxes, IntPair cp, char move, boolean p2) {
		if (!print) {
			return;
		}
		System.out.println("Next move: " + move);
		var grid = this.grid;
		if (p2) {
			grid = mgr;
		}
		StringBuilder sb = new StringBuilder();
		IntPair ip = new IntPair(0, 0);
		for (ip.first = 0; ip.first < grid.size(); ip.first++) {
			for (ip.second = 0; ip.second < grid.get(0).length(); ip.second++) {
				if (cp.equals(ip)) {
					sb.append("@");
				} else if (boxes.contains(ip)) {
					if (p2) {
						sb.append("[]");
						ip.second++;
					} else {
						sb.append("O");
					}
				} else if (getChar(ip, p2) == '#') {
					sb.append("#");
				} else {
					sb.append(".");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		System.out.println();
//		Util.readLine();
	}

	char getChar(IntPair ip, boolean p2) {
		if (p2) {
			return mgr.get(ip.first).charAt(ip.second);
		}
		return grid.get(ip.first).charAt(ip.second);
	}

	void init() {
		int i = 0;
		for (var str : in) {
			if (str.isBlank()) {
				i = -1;
			} else if (i < 0) {
				moves.add(str);
			} else {
				grid.add(str);
				String nstr = "";
				for (int j = 0; j < str.length(); j++) {
					if (str.charAt(j) == '@') {
						start = new IntPair(i, j);
					} else if (str.charAt(j) == 'O') {
						boxes1.add(new IntPair(i, j));
						boxes2.add(new IntPair(i, 2 * j));
					}
					nstr += switch (str.charAt(j)) {
					case '@' -> "@.";
					case '.' -> "..";
					case 'O' -> "[]";
					case '#' -> "##";
					default -> throw new IllegalArgumentException("Unexpected value: " + str.charAt(j));
					};
				}
				mgr.add(nstr);
				i++;
			}
		}
		_DIRS.put('^', new IntPair(-1, 00));
		_DIRS.put('>', new IntPair(00, 01));
		_DIRS.put('v', new IntPair(01, 00));
		_DIRS.put('<', new IntPair(00, -1));
	}
}

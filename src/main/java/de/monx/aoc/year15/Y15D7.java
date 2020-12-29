package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Y15D7 extends Day {
	//@formatter:off
	enum Operator { AND, OR, LSHIFT, RSHIFT, NOT, ASSIGN }
	@Data @AllArgsConstructor class Gate { public Operator op; public Pair<Pair<Integer, String>, Pair<Integer, String>> args; }
	//@formatter:on

	Map<String, Integer> values = new HashMap<>();
	Map<String, Gate> gates = new HashMap<>();
	boolean isParsed = false;

	@Override
	public Object part1() {
		if (!isParsed) {
			parse();
		}
		var stack = new ArrayList<String>();
		stack.add("a");
		while (!stack.isEmpty()) {
			String c = stack.get(0);
			stack.remove(0);
			var todos = fetchTodos(c);
			if (!todos.isEmpty()) {
				for (String todo : todos) {
					if (!stack.contains(todo)) {
						stack.add(todo);
					}
				}
				stack.add(c);
				continue;
			}

			operate(c);
		}
		return values.get("a");
	}

	@Override
	public Object part2() {
		int p1Res = (int) part1();
		isParsed = true;
		parse();
		values = new HashMap<>();
		values.put("b", p1Res);
		gates.remove("b");
		return part1();
	}

	void operate(String g) {
		Gate gate = gates.get(g);
		IntPair vals = null;
		if (gate != null) {
			vals = fetchVals(gate);
		} else {
			values.put(g, values.get(g));
			return;
		}
		switch (gate.op) {
		case AND:
			values.put(g, vals.first & vals.second);
			break;
		case OR:
			values.put(g, vals.first | vals.second);
			break;
		case LSHIFT:
			values.put(g, vals.first << vals.second);
			break;
		case RSHIFT:
			values.put(g, vals.first >> vals.second);
			break;
		case ASSIGN:
			values.put(g, vals.first);
			break;
		case NOT:
			values.put(g, ~vals.first);
			break;
		}
	}

	IntPair fetchVals(Gate g) {
		Integer i1 = null;
		Integer i2 = null;
		String t1 = g.args.first.second;
		if (t1 == null) {
			i1 = g.args.first.first;
		} else {
			i1 = values.get(t1);
		}
		if (g.args.second != null) {
			String t2 = g.args.second.second;
			if (t2 == null) {
				i2 = g.args.second.first;
			} else {
				i2 = values.get(t2);
			}
		}
		return new IntPair(i1, i2);
	}

	List<String> fetchTodos(String c) {
		var ret = new ArrayList<String>();
		Gate g = gates.get(c);
		if (g == null) {
			return ret;
		}
		String t1 = g.args.first.second;
		if (t1 != null && !values.containsKey(t1)) {
			ret.add(t1);
		}
		if (g.args.second != null) {
			String t2 = g.args.second.second;
			if (t2 != null && !values.containsKey(t2)) {
				ret.add(t2);
			}
		}
		return ret;
	}

	void parse() {
		for (var s : getInputList()) {
			String[] spl = s.split(" -> ");
			String des = spl[1];
			String g = spl[0];
			spl = g.split(" ");
			if (g.contains(" AND ")) {
				gates.put(des, new Gate(Operator.AND, makeArgs(spl[0], spl[2])));
			} else if (g.contains(" OR ")) {
				gates.put(des, new Gate(Operator.OR, makeArgs(spl[0], spl[2])));
			} else if (g.contains(" LSHIFT ")) {
				gates.put(des, new Gate(Operator.LSHIFT, makeArgs(spl[0], spl[2])));
			} else if (g.contains(" RSHIFT ")) {
				gates.put(des, new Gate(Operator.RSHIFT, makeArgs(spl[0], spl[2])));
			} else if (g.startsWith("NOT ")) {
				gates.put(des, new Gate(Operator.NOT, makeArgs(spl[1], null)));
			} else {
				var pis = pis(g);
				if (pis.first != null) {
					values.put(des, pis.first);
				}
				gates.put(des, new Gate(Operator.ASSIGN, makeArgs(g, null)));

			}
		}
	}

	Pair<Pair<Integer, String>, Pair<Integer, String>> makeArgs(String s1, String s2) {
		return new Pair<Pair<Integer, String>, Pair<Integer, String>>(pis(s1), pis(s2));
	}

	//@formatter:off
	Pair<Integer, String> pis(String s) {
		if(s == null) return null;
		try { return new Pair(Integer.valueOf(s), null); } 
		catch (Exception e) { return new Pair(null, s);}
	} //@formatter:on
}

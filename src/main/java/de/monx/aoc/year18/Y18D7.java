package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y18D7 extends Day {
	List<String[]> in = getInputList().stream().map(x -> x.split(" ")) //
			.map(x -> new String[] { x[1], x[7] }).toList();

	@Override
	public Object part1() {
		Set<String> done = new HashSet<>();
		String ret = "";
		Map<String, Set<String>> dependencies = new HashMap<>();
		Set<String> todo = new HashSet<>();
		for (var x : in) {
			dependencies.computeIfAbsent(x[1], k -> new HashSet<>()).add(x[0]);
			todo.add(x[1]);
			dependencies.putIfAbsent(x[0], new HashSet<>());
			todo.add(x[0]);
		}
		while (!todo.isEmpty()) {
			Set<String> adder = new HashSet<>();
			for (var x : todo) {
				if (allDone(dependencies.get(x), dependencies.keySet())) {
					adder.add(x);
					for (var y : dependencies.get(x)) {
						if (!done.contains(y)) {
							adder.add(y);
						}
					}
				}
			}
			List<String> add = new ArrayList<>(adder);
			Collections.sort(add);
			String x = add.get(0);
			ret += x;
			todo.remove(x);
			done.add(x);
			dependencies.remove(x);
			add = new ArrayList<>();
		}
		return ret;
	}

	boolean allDone(Set<String> a, Set<String> b) {
		if (a == null) {
			return true;
		}
		for (var x : a) {
			if (b.contains(x)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object part2() {
		int ret = 0;
		Map<String, Set<String>> dep = new HashMap<>();
		Set<String> todos = new HashSet<>();
		for (var i : in) {
			dep.putIfAbsent(i[0], new HashSet<>());
			dep.putIfAbsent(i[1], new HashSet<>());
			dep.get(i[1]).add(i[0]);
			todos.add(i[0]);
			todos.add(i[1]);
		}

		Set<String> done = new HashSet<>();
		List<String> inQueue = new ArrayList<>();
		int[] workTime = new int[5];
		String[] work = new String[workTime.length];
		String msg = "";
		while (!todos.isEmpty() || !inQueue.isEmpty()) {
			for (int i = 0; i < workTime.length; i++) {
				workTime[i]--;
				if (workTime[i] >= 0) {
					continue;
				}
				if (work[i] != null) {
					done.add(work[i]);
					msg += work[i];
					work[i] = null;
				}
				Set<String> rem = new HashSet<>();
				for (var todo : todos) {
					boolean available = true;
					for (var d : dep.get(todo)) {
						if (!done.contains(d)) {
							available = false;
							break;
						}
					}
					if (available) {
						inQueue.add(todo);
						rem.add(todo);
					}
				}
				todos.removeAll(rem);
				if (inQueue.size() <= 0) {
					continue;
				}
				Collections.sort(inQueue);
				work[i] = inQueue.remove(0);
				workTime[i] = 60 + work[i].charAt(0) - 'A';
			}
//			String sys = "" + ret;
//			for (int i = 0; i < work.length; i++) {
//				sys += "\t" + work[i];
//			}
//			System.out.println(sys + "\t" + msg);
			ret++;
		}
		ret--;
		return ret + Arrays.stream(workTime).max().getAsInt();
	}

}

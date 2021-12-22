package de.monx.aoc.year16;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y16D11 extends Day {

	static final int _PAIR = 0;
	static final int _GEN = 1;
	static final int _MIC = 2;

	final State initialState = parse();

	@Override
	public Object part1() {
		return solve(initialState.clone());
	}

	@Override
	public Object part2() {
		State p2State = initialState.clone();
		p2State.stages.get(0).addPair("elerium");
		p2State.stages.get(0).addPair("dilithium");
		return solve(p2State);
//		return null;
	}

	Object solve(State initialState) {
		Set<String> hashes = new HashSet<>();
		Deque<State> stack = new ArrayDeque<>();
		stack.push(initialState);
		hashes.add(initialState.hash());
		while (!(stack.isEmpty() || stack.peek().finished())) { //@formatter:off
			for(var n : fetchMore(stack.removeFirst())) { 
				if(n.finished()) return n.steps;
				if(!hashes.contains(n.hash())) {	
					hashes.add(n.hash()); 
					stack.addLast(n);
				}
			}
		}//@formatter:on

		if (stack.isEmpty()) {
			return "Error, Stack is empty!";
		}
		return stack.peekFirst().steps;
	}

	List<State> fetchMore(State s) {
		List<State> ret = new ArrayList<>();
		var todos = s.fetchTodos();
		var gens = todos[_GEN];
		var mics = todos[_MIC];
		var pair = todos[_PAIR];
		int p0 = s.elevator;
		int p1;
		State cs = null;
		State ct = null;

		if (s.canUp()) {
			p1 = p0 + 1;
			cs = s.clone();
			cs.up();

			if (pair.length == 1) { //@formatter:off
				ct = cs.clone();
				if(ct.move(p0, p1, _PAIR, pair[0])) ret.add(ct);
			} else {
				for (int i = 0; i < gens.length; i++) { 	var e0 = gens[i];
				for (int j = i; j < gens.length; j++) { var e1 = i == j ? null : gens[j];
					ct = cs.clone();
					if(ct.move(p0, p1, _GEN, e0, e1 )) ret.add(ct);
				}
			}

			for (int i = 0; i < mics.length; i++) { 	var e0 = mics[i];
				for (int j = i; j < mics.length; j++) { var e1 = i == j ? null : mics[j];
					ct = cs.clone();
					if(ct.move(p0, p1, _MIC, e0, e1 )) ret.add(ct);
				}
			}
			//@formatter:on

			}

		}

		if (s.canDown()) {
			p1 = p0 - 1;
			cs = s.clone();
			cs.down();

			//@formatter:off
			if (pair.length == 1) {
				ct = cs.clone();
				if(ct.move(p0, p1, _GEN, pair[0])) ret.add(ct);
				ct = cs.clone();
				if(ct.move(p0, p1, _MIC, pair[0])) ret.add(ct);
			}
			
			
			for (int i = 0; i < gens.length; i++) { 	var e0 = gens[i];
				for (int j = i; j < gens.length; j++) { var e1 = i == j ? null : gens[j];
					ct = cs.clone();
					if(ct.move(p0, p1, _GEN, e0, e1 )) ret.add(ct);
				}
			}
	
			for (int i = 0; i < mics.length; i++) { 	var e0 = mics[i];
				for (int j = i; j < mics.length; j++) { var e1 = i == j ? null : mics[j];
					ct = cs.clone();
					if(ct.move(p0, p1, _MIC, e0, e1 )) ret.add(ct);
				}
			}
			//@formatter:on

		}

		return ret;
	}

	@Data
	public class State {
		int steps = 0;
		int elevator = 0;
		List<Stage> stages = new ArrayList<>();

		public boolean finished() {
			for (int i = 0; i < stages.size() - 1; i++) { //@formatter:off
				if(!stages.get(i).isEmpty()) return false;
			}//@formatter:on
			return true;
		}

		public String hash() {
			return elevator + ";" + stages.stream().map(x -> x.hash()).reduce("", (a, e) -> a + "|" + e);
		}

		boolean canUp() {
			return elevator < stages.size() - 1;
		}

		boolean canDown() { //@formatter:off
			if(elevator == 0) return false;
			for( int i = 0; i < elevator; i++) if(!stages.get(i).isEmpty()) return true;
			return false; //@formatter:on
		}

		boolean move(int p0, int p1, int t, String... ss) {
			for (var s : ss) { //@formatter:off
				if(s == null) continue;
				switch (t) { 
				case _PAIR: stages.get(p0).removePair(s); stages.get(p1).addPair(s); break;
				case _GEN: stages.get(p0).removeGen(s); stages.get(p1).addGen(s); break;
				case _MIC: stages.get(p0).removeMic(s); stages.get(p1).addMic(s); break;
				default: System.err.println("Unknown Type(t): " + t); break;  //@formatter:off
				}
			}
			return stages.get(p0).check() && stages.get(p1).check();
		}
		
		boolean check(int... idxs) { // @formatter:off
			for(var i : idxs) if (!stages.get(i).check()) return false;
			return true;
		}// @formatter:on

		public String[][] fetchTodos() {
			return stages.get(elevator).fetchTodos();
		}

		public State clone() {
			State s = new State();
			s.steps = steps;
			s.elevator = elevator;
			for (var st : stages) {
				s.stages.add(st.clone());
			}
//			stages.forEach(x -> s.stages.add(x.clone()));
			return s;
		}

		public void up() {
			elevator++;
			steps++;
		}

		public void down() {
			elevator--;
			steps++;
		}
	}

	@Data
	public class Stage {
		Set<String> pairs = new HashSet<>();
		Set<String> gens = new HashSet<>();
		Set<String> mics = new HashSet<>();

		void addGen(String s) {
			if (mics.contains(s)) {
				mics.remove(s);
				pairs.add(s);
			} else {
				gens.add(s);
			}
		}

		boolean addMic(String s) {
			if (gens.contains(s)) {
				gens.remove(s);
				pairs.add(s);
			} else {
				mics.add(s);
			}
			return check();
		}

		void addPair(String s) {
			pairs.add(s);
		}

		String getPair() {
			for (var p : pairs) {
				return p;
			}
			return null;
		}

		void removeMic(String s) { //@formatter:off
			if(mics.contains(s)) mics.remove(s);
			else { pairs.remove(s); gens.add(s); }
		}//@formatter:on

		void removeGen(String s) { //@formatter:off
			if(gens.contains(s)) gens.remove(s);
			else { pairs.remove(s); mics.add(s); }
		}//@formatter:on

		void removePair(String s) {
			pairs.remove(s);
		}

		String[][] fetchTodos() { //@formatter:off
			String[][] ret = new String[3][];
			if(!pairs.isEmpty()) ret[0] = new String[]{getPair()};
			else ret[_PAIR] = new String[] {};
			ret[_GEN] = gens.toArray(new String[0]);
			ret[_MIC] = mics.toArray(new String[0]);
			return ret;
		}//@formatter:on

		boolean check() {
			return mics.isEmpty() || gens.isEmpty();
		}

		boolean isEmpty() {
			return pairs.isEmpty() && gens.isEmpty() && mics.isEmpty();
		}

		String hash() {
			return pairs.size() + ";" + gens.size() + ";" + mics.size();
		}

		public Stage clone() {
			Stage s = new Stage();
			pairs.forEach(x -> s.pairs.add(x));
			gens.forEach(x -> s.gens.add(x));
			mics.forEach(x -> s.mics.add(x));
			return s;
		}
	}

	State parse() {
		State state = new State();
		for (var s : getInputList()) { // @formatter:off
			Stage stage = new Stage();
			s = s.split("contains ")[1]; 
			if(s.startsWith("nothing")) {
				state.stages.add(stage);
				continue;
			}
			s = s.replace("a ", "").replace(".", "").replace(" and", ",").replace("-compatible", "");
			for (var es : s.split(",")) {
				if (es.isBlank()) continue; 
				var espl = es.trim().split(" ");
				if(espl[1].equals("generator")) stage.addGen(espl[0]);
				else stage.addMic(espl[0]);
			}// @formatter:on
			state.stages.add(stage);
		}
		return state;
	}
}

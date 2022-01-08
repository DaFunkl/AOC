package de.monx.aoc.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BronKerbosch<T> {
	private Set<T> bestR = new HashSet<>();
	private Map<T, Set<T>> neighbours = new HashMap<>();

	public BronKerbosch(Map<T, Set<T>> neighbours) {
		this.neighbours = neighbours;
	}

	public Set<T> largestClique() {
		execute(neighbours.keySet());
		return bestR;
	}

	void execute(Set<T> p) {
		execute(p, new HashSet<>(), new HashSet<>());
	}

	void execute(Set<T> p, Set<T> r, Set<T> x) {
		if (p.isEmpty() && x.isEmpty()) {
			if (r.size() > bestR.size()) {
				bestR = r;
			}
			return;
		}
		Set<T> px = new HashSet<>();
		px.addAll(p);
		px.addAll(x);
		T mostNeighborsOfPandX = px.stream().max(new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return Integer.compare(neighbours.get(o1).size(), neighbours.get(o1).size());
			}
		}).get();
		
		Set<T> pWithoutNeighbours = new HashSet<>();
		pWithoutNeighbours.addAll(p);
		pWithoutNeighbours.removeAll(neighbours.get(mostNeighborsOfPandX));

		pWithoutNeighbours.forEach(v -> {
			var nv = neighbours.get(v);

			Set<T> ip = new HashSet<>();
			ip.addAll(p);
			ip.retainAll(nv);

			Set<T> ix = new HashSet<>();
			ix.addAll(x);
			ix.retainAll(nv);

			Set<T> rv = new HashSet<>();
			rv.addAll(r);
			rv.add(v);

			execute(ip, rv, ix);
		});
	}

}

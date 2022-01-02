package de.monx.aoc.year17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

public class Y17D7 extends Day {
	List<String> in = getInputList();
	Map<String, Node> nodeRef = new HashMap<>();
	Node root = null;

	@Override
	public Object part1() {
		Set<String> nodeNames = new HashSet<>();
		List<Pair<String, String[]>> nodeLinks = new ArrayList<>();

		// init Nodes
		for (String s : in) {
			// mqdjo (83)
			// jzgxy (15) -> usdayz, zvbru
			String nodeStr = s;
			String[] links = null;
			if (s.contains(" -> ")) {
				var splNL = s.split(" -> ");
				nodeStr = splNL[0];
				links = splNL[1].split(", ");
			}
			Node node = new Node(nodeStr);
			nodeNames.add(node.name);
			nodeRef.put(node.name, node);
			if (links != null) {
				nodeLinks.add(new Pair<String, String[]>(node.name, links));
			}
		}

		// link Nodes
		for (var link : nodeLinks) {
			Node n = nodeRef.get(link.first);
			for (var l : link.second) {
				n.addNode(nodeRef.get(l));
				nodeNames.remove(l);
			}
		}

		// fetch root
		for (var x : nodeNames) {
			root = nodeRef.get(x);
			break;
		}

		return root.name;
	}

	@Override
	public Object part2() {
		nodeWeight(root);
		return ret2;
	}

	Integer ret2 = null;

	int nodeWeight(Node n) {
		if (n.nodes != null) {
			Map<Integer, List<Integer>> nws = new HashMap<>();
			for (int i = 0; i < n.nodes.size(); i++) {
				var nn = n.nodes.get(i);
				int nw = nodeWeight(nn);
				n.nodeWeights += nw;
				nws.computeIfAbsent(nw, k -> new ArrayList<>()).add(i);
			}
			if (ret2 == null && nws.size() > 1) {
				int v1 = 0, v2 = 0;
				int idx = 0;
				for (var k : nws.keySet()) {
					if (nws.get(k).size() == 1) {
						idx = nws.get(k).get(0);
						v2 = k;
					} else {
						v1 = k;
					}
				}
				Node nv1 = n.nodes.get(idx);
				ret2 = nv1.value + v1 - v2;
			}
		}
		n.nodeWeights += n.value;
		return n.nodeWeights;
	}

	@Data
	static class Node {
		String name;
		int value;
		List<Node> nodes;
		int nodeWeights = 0;

		public Node() {
		}

		public Node(String s) {
			// mqdjo (83)
			var sar = s.split(" ");
			name = sar[0];
			value = Integer.valueOf(sar[1].substring(1, sar[1].length() - 1));
		}

		void addNode(Node n) {
			if (nodes == null) {
				nodes = new ArrayList<>();
			}
			nodes.add(n);
		}
	}
}

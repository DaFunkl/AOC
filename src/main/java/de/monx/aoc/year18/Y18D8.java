package de.monx.aoc.year18;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y18D8 extends Day {
	int[] in = Arrays.stream(getFileString().split(" ")).mapToInt(Integer::valueOf).toArray();

	Node root = new Node(in[0], in[1]);

	@Override
	public Object part1() {
		int ret = 0;
		ArrayDeque<Node> stack = new ArrayDeque<>();
		stack.push(root);
		for (int i = 2; i < in.length; i++) {
			Node cur = stack.peek();
			if (cur.needSub()) {
				Node next = new Node(in[i], in[i + 1]);
				cur.subs[cur.subIdx++] = next;
				stack.push(next);
				i++;
			} else if (cur.needMd()) {
				cur.md[cur.mdIdx++] = in[i];
				ret += in[i];
			} else {
				stack.pop();
				i--;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return calc(root);
	}

	int calc(Node n) {
		if (n.subs.length < 1) {
			return Arrays.stream(n.md).sum();
		}
		int ret = 0;
		Map<Integer, Integer> map = new HashMap<>();
		for (int i : n.md) {
			i--;
			if (map.containsKey(i)) {
				ret += map.get(i);
			} else if (i < n.subIdx) {
				int res = calc(n.subs[i]);
				map.put(i, res);
				ret += res;
			}
		}
		return ret;
	}

	@Data
	class Node {
		Node[] subs;
		int subIdx = 0;
		int[] md;
		int mdIdx = 0;

		public Node(int n, int m) {
			subs = new Node[n];
			md = new int[m];
		}

		boolean needSub() {
			return subIdx < subs.length;
		}

		boolean needMd() {
			return mdIdx < md.length;
		}
	}

}

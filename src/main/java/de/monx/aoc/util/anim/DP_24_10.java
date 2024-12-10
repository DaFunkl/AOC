package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_24_10 extends JPanel {

	List<int[]> in = new ArrayList<>();
	int scale = 10;
	Set<IntPair> seen = new HashSet<>();

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 2260, 1230);
		IntPair ip = new IntPair(0, 0);
		for (; ip.first < in.size(); ip.first++) {
			for (; ip.second < in.get(0).length; ip.second++) {
				int re = 50 + (in.get(ip.first)[ip.second] * 15);
				int gr = 50 + (in.get(ip.first)[ip.second] * 15);
				int bl = 0;

				if (seen.contains(ip)) {
					re /= 3;
					gr += 50;
					bl = 90 + (in.get(ip.first)[ip.second] * 15);
				}

				g.setColor(new Color(re, gr, bl));
				g.fillRect( //
						(ip.first * scale) + 10, (ip.second * scale) + 15, scale - 2, scale - 2 //
				);
			}
			ip.second = 0;
		}

	}

	public void update(long sleep, Set<IntPair> seen) {
		this.seen = seen;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setGrid(List<int[]> in) {
		this.in = in;
	}
}

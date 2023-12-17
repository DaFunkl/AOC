package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_23_17 extends JPanel {

	int[][] in;
	int scale = 7;
	int offset = 3;
	Map<String, Integer> bw = new HashMap<>();

	public void paintComponent(Graphics g) {
//		g.setColor(Color.black);
//		g.fillRect(-100, -100, 1500, 600);
		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in.length; i++) {

			}
		}
	}

	public void update(long sleep, List<String> in, Map<IntPair, boolean[]> bw) {
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

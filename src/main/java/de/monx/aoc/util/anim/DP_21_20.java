package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_21_20 extends JPanel {
	Map<IntPair, Boolean> in;
	boolean unseen = false;
	int scale = 4;
	int yOffset = 15;
	int xOffset = 5;

	int[] mm = { -51, 151 };

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 800, 800);
		for (int y = mm[0]; y < mm[1]; y++) {
			for (int x = mm[0]; x < mm[1]; x++) {
				IntPair ip = new IntPair(y, x);
				if (in.containsKey(ip) && in.get(ip)) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.gray);
				}

//				g.fillRect((y - mm[0]) * scale + yOffset, (x - mm[0]) * scale + yOffset, scale - 1, scale - 1);
				g.fillRect((y - mm[0]) * scale + yOffset, (x - mm[0]) * scale + yOffset, scale, scale);
			}
		}
	}

	public void update(long sleep, Map<IntPair, Boolean> in, boolean unseen) {
		this.in = in;
		this.unseen = unseen;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

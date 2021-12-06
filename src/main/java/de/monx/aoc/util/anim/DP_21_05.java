package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_21_05 extends JPanel {
	int[][] grid = null;
	final static int _WALL = 0;
	final static int _FREE = 1;
	final static int _OXYG = 2;
	final static int _BOTY = 3;
	final static int _NONO = 4;

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				switch (grid[y][x]) { //@formatter:off
					case 0: g.setColor(Color.black); break;
					case 1: g.setColor(Color.white); break;
					case 2: g.setColor(Color.green); break;
					default: g.setColor(Color.cyan);
				} //@formatter:on
				g.fillRect(x - 30, y - 30, 2, 2); // Draw on g here e.g.
			}
		}

	}

	public void drawGrid(int[][] grid) {
		this.grid = grid;
		revalidate();
		repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
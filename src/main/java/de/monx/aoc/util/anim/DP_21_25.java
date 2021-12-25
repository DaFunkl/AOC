package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_21_25 extends JPanel {
	char[][] grid;
	int scale = 4;
	int yOffset = 15;
	int xOffset = 5;

	int[] mm = { -51, 151 };

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 800, 800);
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				switch (grid[y][x]) { //@formatter:off
				case '>' : g.setColor(Color.GREEN); break;
				case 'v' : g.setColor(Color.blue); break;
				default : g.setColor(Color.black); break;
				}//@formatter:on

				g.fillRect(y * scale + yOffset, x * scale + yOffset, scale - 1, scale - 1);
//				g.fillRect((y - mm[0]) * scale + yOffset, (x - mm[0]) * scale + yOffset, scale, scale);
			}
		}
	}

	public void update(long sleep, char[][] grid) {
		this.grid = grid;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

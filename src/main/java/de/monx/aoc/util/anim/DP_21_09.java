package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_21_09 extends JPanel {
	int[][] grid = null;
	int scale = 4;
	Color[] colorArray = { //
	};

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				g.setColor(colorArray[grid[y][x]]);
				g.fillRect(x, y, scale, scale); // Draw on g here e.g.
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

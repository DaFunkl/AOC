package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_21_09 extends JPanel {
	Random rand = new Random();
	int[][] grid = null;
	int scale = 6;
	Color[] colorArray = initColor(10);

	Color[] initColor(int n) {
		Color[] colorArray = new Color[n];
		for (int i = 0; i < n; i++) {
			colorArray[i] = randoColor();
		}
		return colorArray;
	}

	Color randoColor() {
		// Java 'Color' class takes 3 floats, from 0 to 1.
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r, g, b);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				g.setColor(colorArray[grid[y][x]]);
				g.fillRect(x * scale + 20, y * scale + 20, scale, scale); // Draw on g here e.g.
			}
		}

	}

	public void drawGrid(int[][] grid) {
		colorArray = initColor(10);
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

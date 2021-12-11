package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_21_11 extends JPanel {
	Random rand = new Random();
	int[][] grid = null;
	int scale = 75;
	Color[] colorArray = //
//			{ //
//					new Color(255, 255, 255), // 1
//					Color.black, // 0
//					new Color(38, 82, 20), // 10
//					new Color(54, 102, 34), // 9
//					new Color(70, 125, 46), // 8
//					new Color(99, 171, 68), // 7
//					new Color(133, 204, 102), // 6
//					new Color(154, 219, 125), // 5
//					new Color(197, 252, 172), // 4
//					new Color(212, 250, 195), // 3
//					new Color(227, 250, 217), // 2
//			};
			{ //
					Color.black, // 0
					Color.white, // 0
					Color.yellow, // 0
					Color.blue, // 0
					Color.red, // 0
					Color.PINK, // 0
					Color.MAGENTA, // 0
					Color.cyan, // 0
					Color.orange, // 0
					Color.GREEN, // 0
					Color.LIGHT_GRAY, // 0
			};

//			initColor(11);

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

	int count = 0;

	public void drawGrid(int[][] grid) {
//		if (count++ > 25) {
//			count = 0;
//			colorArray = initColor(11);
//			colorArray[0] = Color.black;
//			colorArray[10] = new Color(38, 82, 20);
//		}
		this.grid = grid;
		revalidate();
		repaint();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

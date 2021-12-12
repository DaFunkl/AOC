package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.text.AttributeSet.ColorAttribute;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_21_09 extends JPanel {
	Random rand = new Random();
	int[][] grid = null;
	Set<IntPair> seen = new HashSet<>();
	int scale = 8;
	Color[] colorArray = //
//		{ //
//			Color.black, // 0
//			new Color(255, 255, 255), // 1
//			new Color(227, 250, 217), // 2
//			new Color(212, 250, 195), // 3
//			new Color(197, 252, 172), // 4
//			new Color(154, 219, 125), // 5
//			new Color(133, 204, 102), // 6
//			new Color(99, 171, 68), // 7
//			new Color(70, 125, 46), // 8
//			new Color(54, 102, 34), // 9
//			new Color(38, 82, 20) // 10
//	};
			initColor(11);

	String state = "grid";

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
		if (state.equals("grid")) {
			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[0].length; x++) {
					g.setColor(colorArray[grid[y][x]]);
					g.fillRect(x * scale + 20, y * scale + 20, scale - 2, scale - 2); // Draw on g here e.g.
				}
			}
		} else {
			for (var s : seen) {
				int y = s.first;
				int x = s.second;

				g.setColor(colorArray[grid[y][x]]);
				g.fillRect(x * scale + 20, y * scale + 20, scale - 2, scale - 2); // Draw on g here e.g.
			}
		}

	}

	public void drawGrid(int[][] grid) {
		state = "grid";
		this.grid = grid;
		revalidate();
		repaint();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	int count = 0;

	public void patch(Set<IntPair> seen, int[][] grid) {
		state = "patch";
		if (count++ > 1) {
			count = 0;
			colorArray = initColor(11);
			colorArray[0] = Color.black;
			colorArray[10] = new Color(38, 82, 20);
		}
		this.seen = seen;
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

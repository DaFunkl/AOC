package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_22_14 extends JPanel {
	int[][] grid = new int[0][0];
	int yOffset = 15;
	int scale = 3;
	int xOffset = 5;

	static Random rando = new Random();

	public static Color randoColor() {
		return new Color(rando.nextInt(180, 230), rando.nextInt(180, 230), rando.nextInt(0, 50));
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 500, 800);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 300; j < grid[0].length; j++) {
				g.setColor(switch (grid[i][j]) {
				case 0 -> Color.black;
				case 1 -> Color.white;
				case 2 -> Color.yellow;
				case 3 -> randoColor();// Color.orange;
				default -> Color.black;
				});
				g.fillRect((j - 300) * scale + yOffset, i * scale + yOffset, scale, scale);
			}
		}

	}

	public void update(long sleep, int[][] grid) {
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

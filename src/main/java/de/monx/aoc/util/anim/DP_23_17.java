package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_23_17 extends JPanel {

	int[][] in;
	int scale = 7;
	int offset = 3;
	int[][][] bw;

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 1500, 1500);
		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in.length; j++) {
				if (bw[i][j][0] == 0 && bw[i][j][1] == 0) {
					g.setColor(new Color(10, 10, 25 * in[i][j]));
				} else {
					int r = 10 + (int) ((240d / 1200d) //
							* ((bw[i][j][0] / 2) + (bw[i][j][1] / 2) + 1));
					g.setColor(new Color(r, 75, 10));
				}
				g.fillRect(j * scale + offset, i * scale + offset, scale - 1, scale - 1);
			}
		}
	}

	public void update(long sleep, int[][] in, int[][][] bw) {
		this.in = in;
		this.bw = bw;

		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

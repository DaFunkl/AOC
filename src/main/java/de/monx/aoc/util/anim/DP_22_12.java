package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_22_12 extends JPanel {
	List<char[]> in = new ArrayList<>();
	static final int _PAINT_GRID = 0;
	static final int _UPDATE_GRID = 1;
	int state = _PAINT_GRID;
	int yOffset = 15;
	int scale = 8;
	int xOffset = 5;
	int[][][] weights = new int[0][0][1];
	int cIdx = 0;
	int[] iPos;

	public void paintComponent(Graphics g) {
		if (iPos == null) {
			g.setColor(Color.black);
			g.fillRect(-100, -100, 950, 950);
			for (int i = 0; i < in.size(); i++) {
				for (int j = 0; j < in.get(0).length; j++) {
					char c = in.get(i)[j];
					int val = 0;
					if (c == 'S') {
						g.setColor(Color.blue);
					} else if (c == 'E') {
						g.setColor(Color.red);
					} else {
						val = 50 + 7 * (c - 'a');
						if (weights.length > 0 && weights[i][j][0] != 0) {
							g.setColor(new Color(0, cIdx * val, ((cIdx + 1) % 2) * val));
						} else {
							g.setColor(new Color(val, val, val));
						}
					}
					g.fillRect(j * scale + yOffset, i * scale + yOffset, scale - 1, scale - 1);
				}
			}

		} else if (iPos != null) {
			int i = iPos[0];
			int j = iPos[1];
			char c = in.get(i)[j];
			if (c == 'S') {
				g.setColor(Color.blue);
			} else if (c == 'E') {
				g.setColor(Color.red);
			} else {
				int val = 50 + 7 * (c - 'a');
				g.setColor(new Color(val, 0, 0));
			}
			g.fillRect(j * scale + yOffset, i * scale + yOffset, scale - 1, scale - 1);
		}
	}

	public void update(long sleep, int[][][] weights, int cIdx, List<char[]> in, int[] iPos) {
		this.in = in;
		this.weights = weights;
		this.cIdx = cIdx;
		this.iPos = iPos;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

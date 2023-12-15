package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_23_14 extends JPanel {

	List<char[]> in;
	int scale = 9;
	int offset = 10;

	public void paintComponent(Graphics g) {
//		g.setColor(Color.black);
//		g.fillRect(-100, -100, 1500, 600);
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(0).length; j++) {
				if (in.get(i)[j] == '.') {
					g.setColor(Color.black);
					g.fillRect(i * scale + offset, j * scale + offset, scale, scale);
				}
				if (in.get(i)[j] == '#') {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(i * scale + offset, j * scale + offset, scale - 1, scale - 1);
				}
				if (in.get(i)[j] == 'O') {
					g.setColor(Color.GREEN);
					g.fillOval(i * scale + offset, j * scale + offset, scale - 1, scale - 1);
				}

			}
		}
	}

	public void update(long sleep, List<char[]> in) {
		this.in = in;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

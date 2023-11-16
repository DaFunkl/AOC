package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_18_13 extends JPanel {
	public List<String> in = new ArrayList<>();
	Map<String, int[]> carts = new HashMap<>();

	int yOffset = 15;
	int scale = 5;
	int xOffset = 5;

	static final int _U = 0; // ^
	static final int _R = 1; // >
	static final int _D = 2; // v
	static final int _L = 3; // <

	@Override
	public void paintComponent(Graphics g) {
		for (int y = 0; y < in.size(); y++) {
			var s = in.get(y);
			for (int x = 0; x < s.length(); x++) {
				var c = s.charAt(x);
				var k = y + "," + x;
				if (carts.containsKey(k)) {
					c = switch (carts.get(k)[2]) {
					case _U -> '^';
					case _R -> '>';
					case _D -> 'v';
					case _L -> '<';
					default -> throw new IllegalArgumentException("Unexpected value: " + carts.get(k)[2]);
					};
				}
				g.setColor(Color.black);
				g.fillRect(x * scale + xOffset, y * scale + yOffset, scale, scale);
				g.setColor(Color.white);
				switch (c) { //@formatter:off
					case ' ': break; //draw_E(x, y); break;
					case '|': draw_PV(x, y, g); break;
					case '-': draw_PH(x, y, g); break;
					case '\\': draw_PDLU(x, y, g); break;
					case '/': draw_PDRU(x, y, g); break;
					case '+': draw_PX(x, y, g); break;
					case '^': draw_CU(x, y, g); break;
					case '>': draw_CR(x, y, g); break;
					case 'v': draw_CD(x, y, g); break;
					case '<': draw_CL(x, y, g); break;
					default: g.setColor(Color.cyan);
				} //@formatter:on
			}
		}
	}

	void draw_E(int x, int y, Graphics g) {

	}

	void draw_PV(int x, int y, Graphics g) {
		g.fillRect(x * scale + xOffset + 3, y * scale + yOffset, 1, scale);
	}

	void draw_PH(int x, int y, Graphics g) {
		g.fillRect(x * scale + xOffset, y * scale + yOffset + 3, scale, 1);
	}

	void draw_PDLU(int x, int y, Graphics g) {
		g.fillRect(x * scale + xOffset + 3, y * scale + yOffset, 1, scale-2);
		g.fillRect(x * scale + xOffset + 2, y * scale + yOffset + 3, scale-2, 1);
	}

	void draw_PDRU(int x, int y, Graphics g) {

		g.fillRect(x * scale + xOffset + 3, y * scale + yOffset, 1, scale-2);
		g.fillRect(x * scale + xOffset + 2, y * scale + yOffset + 3, scale-2, 1);
		
	}

	void draw_PX(int x, int y, Graphics g) {
		draw_PH(x, y, g);
		draw_PV(x, y, g);
	}

	void draw_CU(int x, int y, Graphics g) {
		g.setColor(Color.green);
		draw_PH(x, y, g);
		g.fillRect(x * scale + xOffset + 1, y * scale + yOffset + 0, 1, 1);
	}

	void draw_CR(int x, int y, Graphics g) {
		g.setColor(Color.green);
		draw_PV(x, y, g);
		g.fillRect(x * scale + xOffset + 2, y * scale + yOffset, 1, scale - 2);
		g.fillRect(x * scale + xOffset + 3, y * scale + yOffset + 1, 1, 1);
	}

	void draw_CD(int x, int y, Graphics g) {
		g.setColor(Color.green);
		draw_PH(x, y, g);
		g.fillRect(x * scale + xOffset, y * scale + yOffset + 2, scale - 2, 1);
		g.fillRect(x * scale + xOffset + 1, y * scale + yOffset + 3, 1, 1);
	}

	void draw_CL(int x, int y, Graphics g) {
		g.setColor(Color.green);
		draw_PV(x, y, g);
		g.fillRect(x * scale + xOffset + 0, y * scale + yOffset + 1, 1, 1);
	}

	public void drawGrid(Map<String, int[]> carts, long sleep) {
		this.carts = carts;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
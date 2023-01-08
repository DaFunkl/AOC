package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import de.monx.aoc.year22.Y22D24.IP3;

@SuppressWarnings("serial")
public class DP_22_24 extends JPanel {
	Map<IP3, List<IP3>> state = new HashMap<>();
	Set<IP3> positions = new HashSet<>();
	IP3 start = null;
	IP3 end = null;

	int yOffset = 15;
	int scale = 10;
	int xOffset = 5;

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 1500, 600);
		IP3 poi = new IP3();
		for (int i = 0; i < 27; i++) {
			poi.p1 = i;
			for (int j = 0; j < 122; j++) {
				poi.p2 = j;
				if (poi.equals(start)) {
					g.setColor(Color.orange);
				} else if (poi.equals(end)) {
					g.setColor(Color.green);
				} else if (i == 0 || j == 0 || i == 26 || j == 121) {
					g.setColor(Color.white);
				} else if (positions.contains(poi)) {
					g.setColor(Color.cyan);
				} else if (state.containsKey(poi)) {
					int re = 150;
					int gr = 150;
					int bl = 150;
					for (var k : state.get(poi)) {
						if (k.p3 == 0) {
							re += 80;
						} else if (k.p3 == 1) {
							gr += 80;
						} else if (k.p3 == 2) {
							bl += 80;
						} else if (k.p3 == 3) {
							re += 40;
							bl += 40;
						}
					}
					re = Math.min(re, 200);
					gr = Math.min(gr, 200);
					bl = Math.min(bl, 200);
					g.setColor(new Color(re, gr, bl));
				} else {
					g.setColor(Color.gray);
				}
				g.fillRect(j * scale + yOffset, i * scale + yOffset, scale - 1, scale - 1);
			}
		}
	}

	public void update(long sleep, Map<IP3, List<IP3>> state, Set<IP3> positions, IP3 start, IP3 end) {
		this.state = state;
		this.positions = positions;
		this.start = start;
		this.end = end;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DP_22_09 extends JPanel {
	long sleep;
	int[][] knots;
	Set<String> seen;
	int yOffset = 15;
	int scale = 8;
	int xOffset = 5;
	Random rand = new Random();

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 950, 950);
		int scale = 3;
		Map<String, String> map = new HashMap<>();
		for (int i = 1; i < knots.length; i++) {
			map.putIfAbsent(knots[i][0] + "," + knots[i][1], "" + i);
		}
		map.put("0,0", "s");
		map.put(knots[0][0] + "," + knots[0][1], "H");
		int y = 0;
		for (int i = -100; i <= 19; i++) {
//		for (int i = -233; i <= 19; i++) {
			int x = 0;
			for (int j = -9; j <= 305; j++) {
				String s = i + "," + j;
				scale = this.scale;
				if (map.containsKey(s)) {
					g.setColor(switch (map.get(s)) {
					case "H" -> Color.yellow;
					case "s" -> Color.red;
					case "1" -> new Color(0, 255, 128);
					case "2" -> new Color(17, 250, 134);
					case "3" -> new Color(41, 255, 148);
					case "4" -> new Color(65, 250, 157);
					case "5" -> new Color(71, 222, 146);
					case "6" -> new Color(75, 209, 142);
					case "7" -> new Color(88, 219, 154);
					case "8" -> new Color(87, 207, 147);
					case "9" -> new Color(91, 194, 142);
					default -> Color.white;
					});
					g.fillOval(y * scale + yOffset, x * scale + yOffset, scale - 1, scale - 1);
					x++;
					continue;
				} else if (seen.contains(s)) {
//					g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));// Color.gray;
					g.setColor(Color.orange);
//					g.setColor(Color.gray);
				} else {
					g.setColor(Color.black);
				}
				g.fillRect(y * scale + yOffset, x * scale + yOffset, scale - 1, scale - 1);
				x++;
			}
			y++;
		}
	}

	public void update(long sleep, int[][] knots, Set<String> seen) {
		this.knots = knots;
		this.seen = seen;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

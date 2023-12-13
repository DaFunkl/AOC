package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_23_10 extends JPanel {

	List<String> in;
	Map<IntPair, Integer> eType;
	Map<IntPair, Integer> enclosed;
	Map<IntPair, Integer> weights;
	int eon = -2;
	int offset = 5;
	int scale = 6;
	int sh = scale / 3;

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 1500, 600);
		IntPair cp = new IntPair(0, 0);
		for (cp.first = 0; cp.first < in.size(); cp.addi(1, 0)) {
			for (cp.second = 0; cp.second < in.get(0).length(); cp.addi(0, 1)) {
				char c = getChar(cp);
				if (enclosed != null && enclosed.containsKey(cp)) {
					if (eon != -2 && enclosed.get(cp) == (eon + 1) % 2) {
						g.setColor(Color.orange);
					} else {
						g.setColor(enclosed.get(cp) == 0 ? Color.cyan
								: enclosed.get(cp) == 1 ? Color.magenta : Color.darkGray);
					}
					g.fillRect(cp.first * scale + offset, cp.second * scale + offset, scale, scale);
				} else if (eType.containsKey(cp)) {
					g.setColor(eType.get(cp) == 0 ? Color.cyan : Color.magenta);
					g.fillRect(cp.first * scale + offset, cp.second * scale + offset, scale, scale);
				}
				drawChar(cp, c, g);
			}
		}
	}

	void drawChar(IntPair cp, char c, Graphics g) {
		g.setColor(weights.containsKey(cp) ? Color.white : Color.gray);
		switch (c) {
		case '|': {
			g.fillRect(cp.first * scale + offset, cp.second * scale + offset, scale, sh);
			break;
		}
		case '-': {
			g.fillRect(cp.first * scale + offset, cp.second * scale + offset, sh, scale);
			break;
		}
		case 'L': {
			g.fillRect(cp.first * scale + offset, cp.second * scale + offset + sh, sh + 2, sh);
			g.fillRect(cp.first * scale + offset + sh, cp.second * scale + offset + sh, sh, sh);
			break;
		}
		case '7': {
			g.fillRect(cp.first * scale + offset + sh, cp.second * scale + offset, sh, sh + 2);
			g.fillRect(cp.first * scale + offset + sh, cp.second * scale + offset + sh, sh, sh);
			break;
		}
		case 'J': {
			g.fillRect(cp.first * scale + offset, cp.second * scale + offset + 2, sh + 2, sh);
			g.fillRect(cp.first * scale + offset + sh, cp.second * scale + offset + sh, sh, sh);
			break;
		}
		case 'F': {
			g.fillRect(cp.first * scale + offset + sh, cp.second * scale + offset, sh + 2, sh);
			g.fillRect(cp.first * scale + offset + sh, cp.second * scale + offset + sh, sh, sh);
			break;
		}
		case '.': {
			break;
		}
		case 'S': {
			g.fillRect(cp.first * scale + offset, cp.second * scale + offset, sh, scale);
			g.fillRect(cp.first * scale + offset, cp.second * scale + offset, scale, sh);
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + c);
		}
	}

	char getChar(IntPair ip) {
		if (ip.first < 0 || ip.first >= in.size() || ip.second < 0 || ip.second >= in.get(0).length()) {
			return 'X';
		}
		return in.get(ip.first).charAt(ip.second);
	}

	public void update(long sleep, List<String> in, Map<IntPair, Integer> weights, Map<IntPair, Integer> eType,
			Map<IntPair, Integer> enclosed, int eon) {
		this.in = in;
		this.weights = weights;
		this.eType = eType;
		this.enclosed = enclosed;
		this.eon = eon;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

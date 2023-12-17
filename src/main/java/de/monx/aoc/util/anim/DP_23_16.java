package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_23_16 extends JPanel {

	List<String> in;
	int scale = 9;
	int offset = 3;
	Map<IntPair, boolean[]> bw = new HashMap<>();

	public void paintComponent(Graphics g) {
//		g.setColor(Color.black);
//		g.fillRect(-100, -100, 1500, 600);
		IntPair ip = new IntPair(0, 0);
		for (ip.first = 0; ip.first < in.size(); ip.first++) {
			for (ip.second = 0; ip.second < in.get(0).length(); ip.second++) {
				var c = getChar(ip);
				var cc = bw.get(ip);

				if (cc != null) {
					g.setColor(new Color( //
							(cc[0] ? 100 : 0) + (cc[3] ? 40 : 0), //
							(cc[1] ? 120 : 0) + (cc[3] ? 20 : 0), //
							(cc[2] ? 110 : 0) + (cc[3] ? 40 : 0) //
					));
				} else {
					if (c == '.') {
						g.setColor(Color.black);
//						g.fillRect(ip.first * scale + offset, ip.second * scale + offset, scale, scale);
					} else {
						g.setColor(new Color(230, 230, 230));
//						g.fillRect(ip.first * scale + offset, ip.second * scale + offset, scale, scale);
					}
				}
				if (c == '.') {
					g.fillRect(ip.second * scale + offset + 2, ip.first * scale + offset + 2, scale - 2, scale - 2);
				} else {
					if (c == '-') {
						g.fillRect(ip.second * scale + offset, ip.first * scale + offset + 3, scale, 3);
					} else if (c == '|') {
						g.fillRect(ip.second * scale + offset + 3, ip.first * scale + offset, 3, scale);
					} else if (c == '/') {
						g.drawLine( //
								ip.second * scale + offset, // x1
								ip.first * scale + offset + scale - 2, // y1
								ip.second * scale + offset + scale - 2, // x2
								ip.first * scale + offset // y2
						);
						g.drawLine( //
								ip.second * scale + offset, // x1
								ip.first * scale + offset + scale - 1, // y1
								ip.second * scale + offset + scale - 1, // x2
								ip.first * scale + offset // y2
						);
						g.drawLine( //
								ip.second * scale + offset, // x1
								ip.first * scale + offset + scale, // y1
								ip.second * scale + offset + scale, // x2
								ip.first * scale + offset // y2
						);
						g.drawLine( //
								ip.second * scale + offset + 1, // x1
								ip.first * scale + offset + scale, // y1
								ip.second * scale + offset + scale, // x2
								ip.first * scale + offset + 1 // y2
						);
						g.drawLine( //
								ip.second * scale + offset + 2, // x1
								ip.first * scale + offset + scale, // y1
								ip.second * scale + offset + scale, // x2
								ip.first * scale + offset + 2// y2
						);

					} else if (c == '\\') {
						g.drawLine( //
								ip.second * scale + offset + 2, // x1
								ip.first * scale + offset, // y1
								ip.second * scale + offset + scale, // x2
								ip.first * scale + offset + scale - 2// y2
						);
						g.drawLine( //
								ip.second * scale + offset + 1, // x1
								ip.first * scale + offset, // y1
								ip.second * scale + offset + scale, // x2
								ip.first * scale + offset + scale - 1// y2
						);
						g.drawLine( //
								ip.second * scale + offset, // x1
								ip.first * scale + offset, // y1
								ip.second * scale + offset + scale, // x2
								ip.first * scale + offset + scale // y2
						);
						g.drawLine( //
								ip.second * scale + offset, // x1
								ip.first * scale + offset + 1, // y1
								ip.second * scale + offset + scale - 1, // x2
								ip.first * scale + offset + scale // y2
						);
						g.drawLine( //
								ip.second * scale + offset, // x1
								ip.first * scale + offset + 2, // y1
								ip.second * scale + offset + scale - 2, // x2
								ip.first * scale + offset + scale // y2
						);

					} else {
						System.err.println("sumtingwong");
					}
				}

			}
		}
	}

	char getChar(IntPair ip) {
		if (ip.first < 0 || ip.second < 0 || ip.first >= in.size() || ip.second >= in.get(0).length()) {
			return 'X';
		}
		return in.get(ip.first).charAt(ip.second);
	}

	public void update(long sleep, List<String> in, Map<IntPair, boolean[]> bw) {
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

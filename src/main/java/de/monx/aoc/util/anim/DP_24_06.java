package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_24_06 extends JPanel {

	List<String> grid = new ArrayList<>();
	Map<IntPair, List<IntPair>> walk1;
	Map<IntPair, List<IntPair>> walk2;
	IntPair cp = new IntPair();
	int xs = 7;
	int ys = 10;
	boolean isLoop = false;

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(-100, -100, 2260, 1230);
//				g.fillRect(j * scale + offset, i * scale + offset, scale - 1, scale - 1);
		IntPair ip = new IntPair(0, 0);
		for (; ip.first < grid.size(); ip.first++) {
			for (; ip.second < grid.get(0).length(); ip.second++) {
				if (ip.equals(cp)) {
					g.setColor(Color.magenta);
				} else if ('#' == grid.get(ip.first).charAt(ip.second)) {
					g.setColor(Color.gray);
				} else if (walk2.containsKey(ip)) {
					if (isLoop) {
						g.setColor(Color.CYAN);
					} else {
						g.setColor(Color.yellow);
					}
				} else if (walk1.containsKey(ip)) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.darkGray);
				}
				g.fillRect( //
						(ip.first * ys) + 5, (ip.second * xs) + 10, ys - 2, xs - 2 //
				);
			}
			ip.second = 0;
		}

	}

	public void update(long sleep, IntPair cp, Map<IntPair, List<IntPair>> walk1, Map<IntPair, List<IntPair>> walk2,
			boolean isLoop) {
		this.walk1 = walk1;
		this.isLoop = isLoop;
		this.walk2 = walk2;
		this.cp = cp;
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setGrid(List<String> grid) {
		this.grid = grid;
	}
}

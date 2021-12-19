package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings("serial")
public class DP_21_17 extends JPanel {

	int yScale = -10;
	int xScale = 3;

	int yOffset = 510;
	int xOffset = 10;

	int[][] target = null;
	Set<IntPair> dots = new HashSet<>();
//	Color dotColor = Util.randoColor();
	Color dotColor = Color.white;

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.green);
		g.drawLine(-50, yOffset, 700, yOffset);
		g.setColor(Color.green);
		g.drawLine(xOffset, -50, xOffset, 700);

		if (target != null) {
			g.setColor(Color.cyan);
			g.fillRect( //
					xOffset + (target[0][0] * xScale), //
					yOffset + (target[1][0] / yScale), //
					(target[0][1] - target[0][0]) * xScale, //
					(target[1][1] - target[1][0]) / yScale //
			);
		}

		g.setColor(dotColor);
		for (var d : dots) {
			g.fillRect( //
					xOffset + (d.first * xScale), //
					yOffset + (d.second / yScale), //
					3, 1); //
		}

	}

	public void update(long sleep) {
		revalidate();
		repaint();
		try {
			Thread.sleep(sleep * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setTarget(int[][] target) {
		this.target = target;
	}

	public void setDots(Set<IntPair> dots) {
//		dotColor = Util.randoColor();
		this.dots = dots;
	}
}

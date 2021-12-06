package de.monx.aoc.util.anim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Animation extends JFrame {
	public JPanel pane;

	public Animation() {
		super();
	}

	public Animation(int w, int h, JPanel dp) {
		super("AOC_Anim");
		pane = dp;
		setSize(w, h);
		setContentPane(pane);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		setBackground(Color.black);
		setTitle("AOC_Anim");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
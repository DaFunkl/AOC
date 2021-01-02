package de.monx.aoc;

import de.monx.aoc.year15.Y15;

public class App {
	static int year = 15;
	static int day = 14;

	public static void main(String[] args) {
		System.out.println("Year: " + year + ", Day: " + day);

// @formatter:off
		switch (year) {
		case 15: new Y15(day);break;
	//		case 16: new Y15(day);
	//		case 17: new Y15(day);
	//		case 18: new Y15(day);
	//		case 19: new Y15(day);
	//		case 20: new Y15(day);
		default: break;
		}
		// @formatter:on
	}
}

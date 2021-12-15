package de.monx.aoc;

import de.monx.aoc.year15.Y15;
import de.monx.aoc.year16.Y16;
import de.monx.aoc.year17.Y17;
import de.monx.aoc.year18.Y18;
import de.monx.aoc.year19.Y19;
import de.monx.aoc.year20.Y20;
import de.monx.aoc.year21.Y21;

public class App {
	static int year = 21;
	static int day = 15;

	public static void main(String[] args) {
		System.out.println("Year: " + year + ", Day: " + day);

//		use for warmup
//		for (int i = 0; i < 10; i++) {
//			System.out.println("Iteration: " + i);
//			switch (year) { // @formatter:off
//			case 15: new Y15(day);break;
//			case 16: new Y16(day);break;
//			case 17: new Y17(day);break;
//			case 18: new Y18(day);break;
//			case 19: new Y19(day);break;
//			case 20: new Y20(day);break;
//			case 21: new Y21(day);break;
//			default: break;
//			} // @formatter:on
//		}

		switch (year) { // @formatter:off
			case 15: new Y15(day);break;
			case 16: new Y16(day);break;
			case 17: new Y17(day);break;
			case 18: new Y18(day);break;
			case 19: new Y19(day);break;
			case 20: new Y20(day);break;
			case 21: new Y21(day);break;
			default: break;
		} // @formatter:on
	}
}

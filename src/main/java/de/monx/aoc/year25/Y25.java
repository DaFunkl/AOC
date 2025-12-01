package de.monx.aoc.year25;

public class Y25 {
	public Y25(int day) {
		// @formatter:off
		switch (day) {
			case  1: new Y25D01().run(); break;
			case  2: new Y25D02().run(); break;
			case  3: new Y25D03().run(); break;
			case  4: new Y25D04().run(); break;
			case  5: new Y25D05().run(); break;
			case  6: new Y25D06().run(); break;
			case  7: new Y25D07().run(); break;
			case  8: new Y25D08().run(); break;
			case  9: new Y25D09().run(); break;
			case 10: new Y25D10().run(); break;
			case 11: new Y25D11().run(); break;
			case 12: new Y25D12().run(); break;
			default: break;
		}
		// @formatter:on
	}
}

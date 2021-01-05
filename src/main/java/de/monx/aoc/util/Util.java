package de.monx.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
	static Scanner scan = new Scanner(System.in);

	public static String readLine() {
		return scan.nextLine();
	}

	public static <T> List<T> shallowCopy(List<T> l) {
		List<T> list = new ArrayList<T>();
		l.forEach(list::add);
		return l;
	}
}

package de.monx.aoc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public abstract class Day {

	int year = 0;
	String day = "0";

	public Day() {
		var name = this.getClass().getSimpleName().substring(1);
		var spl = name.split("D");
		year = Integer.valueOf(spl[0]);
		day = spl[1];
	}

	public void run() {
		long p1TimeStart = System.nanoTime();
		Object p1 = part1();
		if (p1 == null)
			p1 = "null";
		double p1Time = (System.nanoTime() - p1TimeStart) / 1e6;
		System.out.println("Part1: " + p1.toString() + " \t " + p1Time + " ms");

		long p2TimeStart = System.nanoTime();
		Object p2 = part2();
		if (p2 == null)
			p2 = "null";
		double p2Time = (System.nanoTime() - p2TimeStart) / 1e6;
		System.out.println("Part2: " + p2.toString() + " \t " + p2Time + " ms");

	}

	public abstract Object part1();

	public abstract Object part2();

	public String getInputString() {
		String str = "";
		try {
			BufferedReader brTest = new BufferedReader(new FileReader(fetchFilePath()));
			str = brTest.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public List<String> getInputList() {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(new File(fetchFilePath()).toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private String fetchFilePath() {
		return "input/" + year + "/" + day;
	}

}

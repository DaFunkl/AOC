package de.monx.aoc.year16;

import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D2 extends Day {

	List<char[]> input = getInputList().stream().map(x -> x.toCharArray()).collect(Collectors.toList());
	int[][] buttons1 = { //
			{ 1, 2, 3 }, //
			{ 4, 5, 6 }, //
			{ 7, 8, 9 }, //
	};

	char[][] buttons2 = { //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', }, //
			{ 'X', 'X', 'X', '1', 'X', 'X', 'X', }, //
			{ 'X', 'X', '2', '3', '4', 'X', 'X', }, //
			{ 'X', '5', '6', '7', '8', '9', 'X', }, //
			{ 'X', 'X', 'A', 'B', 'C', 'X', 'X', }, //
			{ 'X', 'X', 'X', 'D', 'X', 'X', 'X', }, //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', }, //
	};

	@Override
	public Object part1() {
		String ret = "";
		IntPair pos = new IntPair(1, 1);
		for (var carr : input) {
			for (char c : carr) {
				pos.addi(fetchDir(c)); //@formatter:off
				if(pos.first  < 0) pos.first  = 0;
				if(pos.second < 0) pos.second = 0;
				if(pos.first  > 2) pos.first  = 2;
				if(pos.second > 2) pos.second = 2;
			}//@formatter:off
			ret += buttons1[pos.first][pos.second];
		}
		return ret;
	}

	@Override
	public Object part2() {
		String ret = "";
		IntPair pos = new IntPair(3, 1);
		for (var carr : input) {
			for (char c : carr) {
				var dir = fetchDir(c); 
				pos.addi(dir);
				if(buttons2[pos.first][pos.second] == 'X') {
					pos.subi(dir);
				}
			}
			ret += buttons2[pos.first][pos.second];
		}
		return ret;
	}
	
	IntPair fetchDir(char c) {
		switch (c) { //@formatter:off
		case 'U': return new IntPair(-1,  0);
		case 'R': return new IntPair( 0,  1);
		case 'D': return new IntPair( 1,  0);
		case 'L': return new IntPair( 0, -1);
		default: return null; //@formatter:on
		}
	}
}

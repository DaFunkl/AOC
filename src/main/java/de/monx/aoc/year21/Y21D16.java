package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Y21D16 extends Day {

	String in = getInputString();
	String bin = hexStrToBinStr(in);

	String hexStrToBinStr(String in) {
		StringBuilder sb = new StringBuilder();
		for (char c : in.toCharArray()) {
			sb.append(switch (c) {
			case '0' -> "0000";
			case '1' -> "0001";
			case '2' -> "0010";
			case '3' -> "0011";
			case '4' -> "0100";
			case '5' -> "0101";
			case '6' -> "0110";
			case '7' -> "0111";
			case '8' -> "1000";
			case '9' -> "1001";
			case 'A' -> "1010";
			case 'B' -> "1011";
			case 'C' -> "1100";
			case 'D' -> "1101";
			case 'E' -> "1110";
			case 'F' -> "1111";
			default -> "";
			});
		}
		return sb.toString();
	}

	Packet solution = null;

	@Override
	public Object part1() {
		solution = Packet.parse(bin, 0).first;
		return solution.versionSum;
	}

	@Override
	public Object part2() {
		return solution.value;
	}

	@Data
	static class Packet {
		long versionSum = 0;
		int startIdx = 0;
		int version = -1;
		int type = -1;
		long value = 0l;
		int iLength = -1;
		int subPacketsLength = -1;
		List<Packet> subPackets = new ArrayList<>();

		static Pair<Packet, Integer> parse(String s, int idx) {
			Packet p = new Packet();
			p.startIdx = idx;
			p.version = Integer.valueOf(s.substring(idx, idx + 3), 2);
			p.versionSum = p.version;
			idx += 3;
			p.type = Integer.valueOf(s.substring(idx, idx + 3), 2);
			idx += 3;

			switch (p.type) {
			case 4: // literal Value
				p.value = 0l;
				while (true) {
					long cv = Long.valueOf(s.substring(idx, idx + 5), 2);
					idx += 5;
					if (cv > 15) {
						p.value <<= 4;
						p.value += cv - 16;
					} else {
						p.value <<= 4;
						p.value += cv;
						break;
					}
				}
				break;
			default: // Operator
				p.iLength = Character.getNumericValue(s.charAt(idx)) == 1 ? 11 : 15;
				idx++;
				p.subPacketsLength = Integer.valueOf(s.substring(idx, idx + p.iLength), 2);
				idx += p.iLength;
				int endIdx = idx + p.subPacketsLength;
				int count = 0;
				while (p.iLength == 15 ? idx < endIdx : count++ < p.subPacketsLength) {
					var sp = Packet.parse(s, idx);
					p.versionSum += sp.first.versionSum;
					p.subPackets.add(sp.first);
					idx = sp.second;
				}
				p.value = switch (p.type) {
				case 0 -> p.subPackets.stream().map(x -> x.value).reduce(0l, (a, b) -> a + b);
				case 1 -> p.subPackets.stream().map(x -> x.value).reduce(1l, (a, b) -> a * b);
				case 2 -> p.subPackets.stream().map(x -> x.value).min(Long::compare).get();
				case 3 -> p.subPackets.stream().map(x -> x.value).max(Long::compare).get();
				case 5 -> p.subPackets.get(0).value > p.subPackets.get(1).value ? 1l : 0l;
				case 6 -> p.subPackets.get(0).value < p.subPackets.get(1).value ? 1l : 0l;
				case 7 -> p.subPackets.get(0).value == p.subPackets.get(1).value ? 1l : 0l;
				default -> p.value;// errorCall(p);
				};

				break;
			}
			return new Pair(p, idx);
		}

	}

}

package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y18D24 extends Day {
	List<String> in = getInputList();
	Group immuneSystem = new Group();
	Group infection = new Group();

	@Override
	public Object part1() {
		init();
		return null;
	}

	@Override
	public Object part2() {

		return null;
	}

	static class Group {
		List<Units> units = new ArrayList<>();

		Units selectTarget() {
			return units.stream().max((o1, o2) -> Integer.compare(o1.power(), o2.power())).get();
		}
	}

	static class Units {
		int amt;
		int hp;
		int initiative;
		int dmg;
		String dmgType;
		Set<String> immune = new HashSet<>();
		Set<String> weak = new HashSet<>();

		int power() {
			return amt * dmg;
		}

		public Units(String line) {
			var sar = line.split(" ");
			amt = Integer.valueOf(sar[0]);
			hp = Integer.valueOf(sar[4]);
			initiative = Integer.valueOf(sar[sar.length - 1]);
			dmg = Integer.valueOf(sar[sar.length - 6]);
			dmgType = sar[sar.length - 5];

			var def = line.split("(")[1].split(")")[0].replace(",", "").replace("to", "").replace(";", "").split(" ");
			boolean immune = true;
			for (var s : def) {
				switch (s) {
				case "immune":
					immune = true;
					break;
				case "weak":
					immune = false;
					break;
				default:
					if (immune) {
						this.immune.add(s);
					} else {
						weak.add(s);
					}
				}
			}
		}
	}

	void init() {
		boolean immuneSystem = true;
		for (int i = 1; i < in.size(); i++) {
			String line = in.get(i);
			if (line.isBlank()) {
				i += 2;
				immuneSystem = false;
				line = in.get(i);
			}
			if (immuneSystem) {
				this.immuneSystem.units.add(new Units(line));
			} else {
				this.infection.units.add(new Units(line));
			}
		}
	}
}

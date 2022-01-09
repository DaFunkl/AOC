package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

public class Y18D24 extends Day {
	List<String> in = getInputList();
	Group imSys = new Group();
	Group inFec = new Group();

	@Override
	public Object part1() {
		return simFight(0).second;
	}

	@Override
	public Object part2() {
		int boost = 0;
		var fight = simFight(boost++);
		while (!fight.first.equals("Immune System")) {
			fight = simFight(boost++);
		}
		return fight.second;
	}

	Pair<String, Integer> simFight(int isBoost) {
		imSys = new Group();
		inFec = new Group();
		init();

		if (isBoost > 0) {
			for (var u : imSys.units) {
				u.dmg += isBoost;
			}
		}

		List<Units> winnerUnits = new ArrayList<Units>();
		String winner = "";
		while (winnerUnits.isEmpty()) {
			// fight:
			// target selection
			imSys.sort(); // sort choosing order
			inFec.sort(); // sort choosing order

			var isAtks = imSys.chooseTargets(inFec);
			var ifAtks = inFec.chooseTargets(imSys);

			if (isAtks.isEmpty() && ifAtks.isEmpty()) {
				winner = "draw";
				winnerUnits.add(new Units(-1));
				break;
			}

			// attacking
			int idxsa = 0, idxfa = 0;

			boolean draw = true;
			while (idxsa < isAtks.size() || idxfa < ifAtks.size()) {
				var atkSA = idxsa < isAtks.size() ? isAtks.get(idxsa) : null;
				var atkFA = idxfa < ifAtks.size() ? ifAtks.get(idxfa) : null;
				int isa = atkSA != null ? atkSA[2] : -1;
				int ifa = atkFA != null ? atkFA[2] : -1;

				if (isa > ifa) {
					int losses = inFec.defends(atkSA[1], imSys.units.get(atkSA[0]));
					if (losses > 0) {
						draw = false;
					}
					idxsa++;
				} else {
					int losses = imSys.defends(atkFA[1], inFec.units.get(atkFA[0]));
					if (losses > 0) {
						draw = false;
					}
					idxfa++;
				}
			}
			if (draw) {
				winner = "draw";
				winnerUnits.add(new Units(-1));
				break;
			}

			imSys.clean();
			inFec.clean();
			// do we have a winne?
			if (imSys.units.isEmpty()) {
				winner = "Infection";
				winnerUnits = inFec.units;
			} else if (inFec.units.isEmpty()) {
				winner = "Immune System";
				winnerUnits = imSys.units;
			}
		}
		return new Pair<String, Integer>(winner, winnerUnits.stream().map(Units::getAmt).reduce(Integer::sum).get());
	}

	@Data
	static class Group {
		List<Units> units = new ArrayList<>();

		List<int[]> chooseTargets(Group o) {
			var ou = o.units;
			List<int[]> ret = new ArrayList<>();

			Set<Integer> chosen = new HashSet<>();
			for (int i = 0; i < units.size(); i++) {
				var unit = units.get(i);
				int minDmg = -1;
				int jIdx = -1;
				int jPower = -1;
				for (int j = 0; j < ou.size(); j++) {
					if (chosen.contains(j)) {
						continue;
					}
					var simAtk = unit.simAttack(ou.get(j));
					if (simAtk <= 0) {
						continue;
					}
					var njp = ou.get(j).power();
					if (simAtk > minDmg || (simAtk == minDmg && jPower < njp)) {
						minDmg = simAtk;
						jIdx = j;
						jPower = njp;
					}
				}
				if (jIdx >= 0) {
					ret.add(new int[] { i, jIdx, unit.initiative, minDmg });
					chosen.add(jIdx);
				}
			}
			Collections.sort(ret, (o1, o2) -> Integer.compare(o2[2], o1[2]));
			return ret;
		}

		int defends(int idx, Units o) {
			return units.get(idx).receiveDmg(o.simAttack(units.get(idx)));
		}

		void clean() {
			for (int i = 0; i < units.size(); i++) {
				if (units.get(i).amt <= 0) {
					units.remove(i--);
				}
			}
		}

		void sort() {
			Collections.sort(this.units, new Comparator<Units>() {
				@Override
				public int compare(Units o1, Units o2) {
					int comp = Integer.compare(o2.power(), o1.power());
					if (comp == 0) {
						return Integer.compare(o2.initiative, o1.initiative);
					}
					return comp;
				}
			});
		}
	}

	@Data
	static class Units {
		int amt;
		int hp;
		int initiative;
		int dmg;
		String dmgType;
		Set<String> immunities = new HashSet<>();
		Set<String> weaknesses = new HashSet<>();

		int power() {
			return amt * dmg;
		}

		int simAttack(Units o) {
			int mul = 1;
			if (o.weaknesses.contains(dmgType)) {
				mul = 2;
			} else if (o.immunities.contains(dmgType)) {
				mul = 0;
			}
			return power() * mul;
		}

		int receiveDmg(int dmg) {
			int unitReduction = Math.min(amt, dmg / hp);
			amt -= unitReduction;
			return unitReduction;
		}

		public Units(int amt) {
			this.amt = amt;
		}

		public Units(String line) {
			var sar = line.split(" ");
			amt = Integer.valueOf(sar[0]);
			hp = Integer.valueOf(sar[4]);
			initiative = Integer.valueOf(sar[sar.length - 1]);
			dmg = Integer.valueOf(sar[sar.length - 6]);
			dmgType = sar[sar.length - 5];

			if (!line.contains("(")) {
				return;
			}

			var def = line.split("\\(")[1].split("\\)")[0].replace(",", "").replace(" to", "").replace(";", "")
					.split(" ");
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
						this.immunities.add(s);
					} else {
						weaknesses.add(s);
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
				this.imSys.units.add(new Units(line));
			} else {
				this.inFec.units.add(new Units(line));
			}
		}
	}
}

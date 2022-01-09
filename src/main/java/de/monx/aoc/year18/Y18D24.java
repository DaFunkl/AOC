package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y18D24 extends Day {
	List<String> in = getInputList();
	Group imSys = new Group();
	Group inFec = new Group();

	@Override
	public Object part1() {
		init();
		List<Units> winner = null;
		int fight = 1;
		while (winner == null) {
			// fight:
			System.out.println("Fight: " + fight++);
			// target selection
			imSys.sort(); // sort choosing order
			inFec.sort(); // sort choosing order
			var isAtks = imSys.chooseTargets(inFec);
			var ifAtks = inFec.chooseTargets(imSys);

			// attacking
			int idxsa = 0, idxfa = 0;
			while (idxsa < isAtks.size() || idxfa < ifAtks.size()) {
				int[] atkSA = idxsa < isAtks.size() ? isAtks.get(idxsa) : null;
				int[] atkFA = idxfa < ifAtks.size() ? ifAtks.get(idxfa) : null;
				int isa = atkSA != null ? atkSA[3] : -1;
				int ifa = atkFA != null ? atkFA[3] : -1;

				if (isa > ifa) {
//					inFec.units.get(atkSA[1]).receiveDmg(atkSA[2]);
					inFec.defends(atkSA[1], imSys.units.get(atkSA[0]));
					idxsa++;
				} else {
//					imSys.units.get(atkFA[1]).receiveDmg(atkFA[2]);
					imSys.defends(atkFA[1], inFec.units.get(atkFA[0]));
					idxfa++;
				}
			}

			imSys.clean();
			inFec.clean();
			// do we have a winne?
			if (imSys.units.isEmpty()) {
				winner = inFec.units;
			} else if (inFec.units.isEmpty()) {
				winner = imSys.units;
			}
		}
		return winner.stream().map(Units::getAmt).reduce(Integer::sum).get();
	}

	@Override
	public Object part2() {

		return null;
	}

	@Data
	static class Group {
		List<Units> units = new ArrayList<>();

		List<int[]> chooseTargets(Group o) {
			var ou = o.units;
			List<int[]> ret = new ArrayList<>();
			for (int i = 0; i < units.size(); i++) {
				var unit = units.get(i);
				int idx = 0;
				int dmg = unit.simAttack(ou.get(0));
				for (int j = 1; j < ou.size(); j++) {
					var oUnit = ou.get(j);
					var simDmg = unit.simAttack(oUnit);
					if (dmg < simDmg || (dmg == simDmg && ou.get(idx).power() < oUnit.power())) {
						dmg = simDmg;
						idx = j;
					}
				}
				ret.add(new int[] { i, idx, dmg, unit.initiative });
			}
			Collections.sort(ret,
					(o1, o2) -> Integer.compare(units.get(o2[0]).initiative, units.get(o1[0]).initiative));
			return ret;
		}

		void defends(int idx, Units o) {
			units.get(idx).receiveDmg(o.simAttack(units.get(idx)));
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
					int comp = Integer.compare(o1.power(), o2.power());
					if (comp == 0) {
						return Integer.compare(o1.initiative, o2.initiative);
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

		void receiveDmg(int dmg) {
			int unitReduction = Math.min(amt, dmg / hp);
			System.out.println(unitReduction + " -> " + this.toString());
			amt -= unitReduction;
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

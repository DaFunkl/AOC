package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;
import lombok.Data;

@SuppressWarnings({ "serial" })
public class Y15D21 extends Day {

	Player boss = fetchBoss();
	List<int[]> weapons = new ArrayList<>() {
		{
			add(new int[] { 8, 4, 0 });
			add(new int[] { 10, 5, 0 });
			add(new int[] { 25, 6, 0 });
			add(new int[] { 40, 7, 0 });
			add(new int[] { 74, 8, 0 });
		}
	};

	List<int[]> armors = new ArrayList<>() {
		{
			add(new int[] { 13, 0, 1 });
			add(new int[] { 31, 0, 2 });
			add(new int[] { 53, 0, 3 });
			add(new int[] { 75, 0, 4 });
			add(new int[] { 102, 0, 5 });
		}
	};

	List<int[]> rings = new ArrayList<>() {
		{
			add(new int[] { 25, 1, 0 });
			add(new int[] { 50, 2, 0 });
			add(new int[] { 100, 3, 0 });
			add(new int[] { 20, 0, 1 });
			add(new int[] { 40, 0, 2 });
			add(new int[] { 80, 0, 3 });
		}
	};

	IntPair solution = null;

	@Override
	public Object part1() {
		solution = solve();
		return solution.first;
	}

	@Override
	public Object part2() {
		return solution.second;
	}

	IntPair solve() {
		Player hero = new Player(100);
		int cost1 = Integer.MAX_VALUE;
		int cost2 = Integer.MIN_VALUE;
		for (int w = 0; w < weapons.size(); w++) {
			int[] weapon = weapons.get(w);
			hero.addEq(weapon);
			// fight without armor and rings
			for (int a = -1; a < armors.size(); a++) {
				int[] armor = null; //@formatter:off
				if(a >= 0) armor = armors.get(a); //@formatter:on
				hero.addEq(armor);
				for (int r2 = -2; r2 < rings.size(); r2++) {
					int[] ring2 = null; //@formatter:off
					if(r2 >= 0) ring2 = rings.get(r2); //@formatter:on
					hero.addEq(ring2);
					for (int r1 = r2 + 1; r1 < rings.size(); r1++) {
						int[] ring1 = null; //@formatter:off
						if(r1 >= 0) ring1 = rings.get(r1); //@formatter:on
						hero.addEq(ring1);
						if (hero.fight(boss) && hero.cost < cost1) {
							cost1 = hero.cost;
						}
						if (boss.fight(hero) && hero.cost > cost2) {
							cost2 = hero.cost;
						}
						hero.removeEq(ring1);
					}
					hero.removeEq(ring2);
				}
				hero.removeEq(armor);
			}
			hero.removeEq(weapon);
		}
		return new IntPair(cost1, cost2);
	}

	Player fetchBoss() {
		var l = getInputList();
		return new Player(//
				Integer.valueOf(l.get(0).split(": ")[1]), // hp
				Integer.valueOf(l.get(1).split(": ")[1]), // dmg
				Integer.valueOf(l.get(2).split(": ")[1]) // armor
		);

	}

	@Data
	static class Player {
		int hp;
		int dmg;
		int armor;
		int cost = 0;

		public Player(int hp) {
			this.hp = hp;
		}

		public Player(int hp, int dmg, int armor) {
			this.hp = hp;
			this.dmg = dmg;
			this.armor = armor;
		}

		public boolean fight(Player p) {
			return this.turnsToDie(p) > p.turnsToDie(this);
		}

		int turnsToDie(Player p) { //@formatter:off
			int pDamage = Math.max(0, p.dmg - armor);
			if(pDamage == 0) return Integer.MAX_VALUE;
			int rounds = hp / pDamage;
			if(hp % pDamage != 0) rounds++;
			return rounds;
		} //@formatter:on

		void addEq(int[] eq) { //@formatter:off
			if(eq == null) return; //@formatter:on
			cost += eq[0];
			dmg += eq[1];
			armor += eq[2];
		}

		void removeEq(int[] eq) { //@formatter:off
			if(eq == null) return; //@formatter:on
			cost -= eq[0];
			dmg -= eq[1];
			armor -= eq[2];
		}
	}
}

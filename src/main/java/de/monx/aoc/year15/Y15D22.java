package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import lombok.Data;

@SuppressWarnings("serial")
public class Y15D22 extends Day {

	Player boss = fetchBoss();

	static final Map<String, Integer> spells = new HashMap<>() {
		{
			put("Magic Missile", 53);
			put("Drain", 73);
			put("Shield", 113);
			put("Poison", 173);
			put("Recharge", 229);
		}
	};

	@Override
	public Object part1() {
		return sim(false);
	}

	@Override
	public Object part2() {
		return sim(true);
	}

	int sim(boolean p2) {

		List<Battle> stack = new ArrayList<>();
		stack.add(new Battle(new Player(), boss.clone(), p2));

		// was used for testing to accumulate mutliple solutions
		List<Integer> costs = new ArrayList<>();
		while (!stack.isEmpty() && costs.size() < 1) {
			// faster without sort 350 ms vs ~ 2 ms
//			if (p2)
//				stack.sort(new Comparator() {
//					@Override
//					public int compare(Object o1, Object o2) {
//						Battle b1 = (Battle) o1;
//						Battle b2 = (Battle) o2;
//						return b1.hero.manaUsed - b2.hero.manaUsed;
//					}
//				});
			Battle b = stack.get(0);
			stack.remove(0);
			for (String spell : spells.keySet()) {
				Battle bs = b.clone();
				if (!bs.cast(spell)) {
					continue;
				}
				int state = bs.resume();
				if (state < 0) {
					continue;
				} else if (state == 1) {
					costs.add(bs.hero.manaUsed);
				} else {
					stack.add(bs);
				}
			}
		}
		return minCost(costs);
	}

	int minCost(List<Integer> l) {//@formatter:off
		int ret = Integer.MAX_VALUE;
		for(int i : l) if(i < ret) ret = i;
		return ret;
	}//@formatter:on

	Player fetchBoss() {
		var l = getInputList();
		return new Player(//
				Integer.valueOf(l.get(0).split(": ")[1]), // hp
				Integer.valueOf(l.get(1).split(": ")[1]) // dmg
		);

	}

	@Data
	static class Player {
		int hp = 50;
		int dmg;
		int armor = 0;
		int mana = 500;
		int manaUsed = 0;

		public Player() {
		}

		public Player(int hp, int dmg) {
			this.hp = hp;
			this.dmg = dmg;
		}

		public Player clone() {
			Player p = new Player(hp, dmg);
			p.armor = armor;
			p.mana = mana;
			p.manaUsed = manaUsed;
			return p;
		}
	}

	@Data
	static class Battle {
		Player hero;
		Player boss;

		int shield = -1;
		int poison = -1;
		int recharge = -1;
		boolean p2 = false;

		public Battle(Player hero, Player boss, boolean p2) {
			this.hero = hero;
			this.boss = boss;
			this.p2 = p2;
		}

		public Battle clone() {
			Battle ret = new Battle(hero.clone(), boss.clone(), p2);
			ret.shield = shield;
			ret.poison = poison;
			ret.recharge = recharge;
			return ret;
		}

		public boolean cast(String spell) {
			if (p2) {
				hero.hp--;
				if (hero.hp <= 0)
					return false;
			}
			if (hero.mana < spells.get(spell)) {
				return false;
			}
			switch (spell) {
			case "Magic Missile":
				boss.hp -= 4;
				hero.mana -= 53;
				hero.manaUsed += 53;
				return true;
			case "Drain":
				boss.hp -= 2;
				hero.hp += 2;
				hero.mana -= 73;
				hero.manaUsed += 73;
				return true;
			case "Shield":
				if (shield > 0) {
					return false;
				}
				shield = 6;
				hero.armor = 7;
				hero.mana -= 113;
				hero.manaUsed += 113;
				return true;
			case "Poison":
				if (poison > 0) {
					return false;
				}
				poison = 6;
				hero.mana -= 173;
				hero.manaUsed += 173;
				return true;
			case "Recharge":
				if (recharge > 0) {
					return false;
				}
				recharge = 5;
				hero.mana -= 229;
				hero.manaUsed += 229;
				return true;
			}
			return false;
		}

		public int resume() {
			applyEffects();
			bossTurn();
			applyEffects();
			return didWin();
		}

		public void applyEffects() {//@formatter:off
			if(shield > 0) { shield--; if(shield == 0) hero.armor = 0; }
			if(poison > 0) {poison--; boss.hp -= 3;}
			if(recharge > 0) { recharge--; hero.mana += 101;}
		}// @formatter:on

		private void bossTurn() {
			hero.hp -= (boss.dmg - hero.armor);
		}

		public int didWin() { //@formatter:off
			if (boss.hp <= 0) return 1;
			if (hero.hp <= 0) return -1;
			return 0;
		} // @formatter:on
	}
}

package de.monx.aoc.year17;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y17D20 extends Day {
	List<Particle> particles = getInputList().stream().map(x -> new Particle(x)).toList();

	@Override
	public Object part1() {
		int minMD = Integer.MAX_VALUE;
		int idx = 0;
		for (int i = 0; i < particles.size(); i++) {
			int md = md(particles.get(i).acc);
			if (md < minMD) {
				minMD = md;
				idx = i;
			}
		}
		return idx;
	}

	int md(int[] arr) {
		int ret = 0;
		for (var i : arr) {
			ret += Math.abs(i);
		}
		return ret;
	}

	@Override
	public Object part2() {
		List<Particle> particles = new ArrayList<>();
		particles.addAll(this.particles);
		removeCollisions(particles);
		for (int steps = 0; steps < 50; steps++) {
			for (var p : particles) {
				for (int i = 0; i < 3; i++) {
					p.vel[i] += p.acc[i];
					p.pos[i] += p.vel[i];
				}
			}
			removeCollisions(particles);
		}
		return particles.size();
	}

	void removeCollisions(List<Particle> particles) {
		for (int i = 0; i < particles.size(); i++) {
			boolean col = false;
			var pi = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				var pj = particles.get(j);
				boolean eqp = true;
				for (int k = 0; k < 3; k++) {
					if (pi.pos[k] != pj.pos[k]) {
						eqp = false;
						break;
					}
				}
				if (eqp) {
					col = true;
					particles.remove(j--);
				}
			}
			if (col) {
				particles.remove(i--);
			}
		}
	}

	@Data
	static class Particle {
		int[] pos = new int[3];
		int[] vel = new int[3];
		int[] acc = new int[3];

		public Particle(String s) {
			var sar = s.split("<");

			// POS
			var arr = sar[1].split(">")[0].split(",");
			for (int i = 0; i < 3; i++) {
				pos[i] = Integer.valueOf(arr[i]);
			}

			// VEL
			arr = sar[2].split(">")[0].split(",");
			for (int i = 0; i < 3; i++) {
				vel[i] = Integer.valueOf(arr[i]);
			}

			// ACC
			arr = sar[3].split(">")[0].split(",");
			for (int i = 0; i < 3; i++) {
				acc[i] = Integer.valueOf(arr[i]);
			}

		}
	}
}

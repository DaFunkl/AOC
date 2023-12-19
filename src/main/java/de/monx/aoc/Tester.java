package de.monx.aoc;

import java.util.Arrays;

public class Tester {

	static String[] coolio(String[] text, String[] pat) {
		String[] ret = new String[text.length];
		for (int i = 0; i < ret.length; i++) {
			var spl = pat[i].split("\\*");
			if ((pat[i].length() - 1) <= text[i].length() && text[i].startsWith(spl[0]) && text[i].endsWith(spl[1])) {
				ret[i] = "YES";
			} else {
				ret[i] = "NO";
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		String[] text = { "code", "coder", "hackerrank", "amduhllahim", "amduhllahim", "aba" };
		String[] pat = { "co*d", "co*er", "hac*rank", "amdu*ahim", "amdux*ahim", "ab*ba" };
		System.out.println("p1: " + Arrays.toString(coolio(text, pat)));

//		int[] boxes = { 2, 4, 1 };
//		int[] boxes = { 2, 4, 1, 3 };

//		int[] boxes = { 2, 4, 1, 3, 15 };
		int[] boxes = { 15, 3, 4 };
		
		// 15, 3, 3 
		// 14, 4, 3 
		// 13, 4, 4 
		// 12, 5, 4 
		// 11, 5, 5 
		// 10, 6, 5 
		// 9,  6, 6 
		// 8,  7, 6 
		// 7,  7, 7 
		

		// 2, 4, 1, 3, 6
		// 2, 4, 2, 3, 5
		// 3, 4, 2, 3, 4
		// 3, 3, 3, 3, 4

		System.out.println("p2: " + cq2(boxes));
	}

	static int cq2(int[] boxes) {
		if (boxes.length <= 0) {
			return 0;
		}
		int ret = 0;
		int sum = 0;
		for (var i : boxes) {
			sum += i;
		}
		int quer = sum / boxes.length;
		// 7, 7, 6
		// 7+7+6 = 20
		// 20/3 = 6
		// 20 - (6*3) = 20 - 18 = 2
		int rest = sum - (quer * boxes.length);
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] > (quer + 1) && rest > 0) {
				ret += boxes[i] - (quer + 1);
				rest--;
			} else {
				ret += Math.abs(boxes[i] - quer);
			}
		}
		return ret / 2;
	}

	static int cq2_2(int[] boxes) {
		int ret = 0;
		int[][] mm = { // mm --> MinMax
				// index 0 = Zahl in boxes, index 1 = index der box
				{ 0, -1 }, // --> hauptsache mm[1][0] - mm[0][0] ist größer als 1
				{ 2, -1 }, //
		};
		// solange es eine Differenz zwischen höchsten und niedrigsten box von größer
		// als 1 gibt
		while ((mm[1][0] - mm[0][0]) > 1) {
			mm = new int[][] { //
					{ Integer.MAX_VALUE, -1 }, //
					{ Integer.MIN_VALUE, -1 }, //
			};
			// finde die größte und kleinste box
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] < mm[0][0]) {
					mm[0][0] = boxes[i];
					mm[0][1] = i;
				}
				if (boxes[i] > mm[1][0]) {
					mm[1][0] = boxes[i];
					mm[1][1] = i;
				}
			}
			// amt = hälfte der Differenz von groß und klein --> bringt beide ins
			// gleichgewicht
			int amt = Math.min(1, (mm[1][0] - mm[0][0]) / 2);
			// stelle amt box von der größten auf die kleinste
			boxes[mm[1][1]] -= amt;
			boxes[mm[0][1]] += amt;
			ret++;
		}
		// -1 wegen off by one error
		return ret - 1;
	}

}

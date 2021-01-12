package de.monx.aoc.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
	static Scanner scan = new Scanner(System.in);

	public static String readLine() {
		return scan.nextLine();
	}

	public static <T> List<T> shallowCopy(List<T> l) {
		List<T> list = new ArrayList<T>();
		l.forEach(list::add);
		return l;
	}

	public static String md5Hash(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte[] digest = md.digest();
			return bytesToHex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "error";
	}

	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

	public static String bytesToHex(byte[] bytes) {
		byte[] hexChars = new byte[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars, StandardCharsets.UTF_8);
	}

	public static Integer fetchNumber(String num) {
		try {
			return Integer.valueOf(num);
		} catch (Exception e) {
			return null;
		}
	}
}

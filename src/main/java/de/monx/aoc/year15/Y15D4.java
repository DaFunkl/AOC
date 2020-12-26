package de.monx.aoc.year15;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.monx.aoc.util.Day;

public class Y15D4 extends Day {

	@Override
	public Object part1() {
		StringBuilder str = new StringBuilder(getInputString());
		int iter = -1;
		String hash = "0";
		while(!hash.startsWith("00000")) {
			StringBuilder sb = new StringBuilder(str);
			sb.append(++iter);
			hash = md5Hash(sb.toString());
		}
		return iter;
	}

	@Override
	public Object part2() {
		StringBuilder str = new StringBuilder(getInputString());
		int iter = -1;
		String hash = "0";
		while(!hash.startsWith("000000")) {
			StringBuilder sb = new StringBuilder(str);
			sb.append(++iter);
			hash = md5Hash(sb.toString());
		}
		return iter;
	}

	public String md5Hash(String s) {
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

}

package de.monx.aoc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Util {
	public static final Gson gsonPretty = new Gson().newBuilder().setPrettyPrinting().create();
	public static final Gson gson = new Gson();
	static Scanner scan = new Scanner(System.in);

	public static final int[][] _DIRS4 = { //
			{ -1, 0 }, //
			{ 1, 0 }, //
			{ 0, -1 }, //
			{ 0, 1 } //
	};

	public static String readLine() {
		return scan.nextLine();
	}

	public static Integer isInteger(String s) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static String sortString(String s) {
		char[] car = s.toCharArray();
		Arrays.sort(car);
		return new String(car);
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

	public static String leftrotate(String str, int d) {
		return str.substring(d) + str.substring(0, d);
	}

	public static String rightrotate(String str, int d) {
		return leftrotate(str, str.length() - d);
	}

	public static boolean isLowerCase(String s) {
		for (var c : s.toCharArray()) {
			if (!Character.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isUpperCase(String s) {
		for (var c : s.toCharArray()) {
			if (Character.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean mkParentDirs(String path) {
		File parentFile = new File(path).getParentFile();
		if (parentFile != null && !parentFile.exists()) {
			return parentFile.mkdirs();
		}
		return true;
	}

	public static <T> boolean saveJsonToFile(String path, T obj) {
		try {
			mkParentDirs(path);
			gsonPretty.toJson(obj, new FileWriter(path));
			return true;
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}

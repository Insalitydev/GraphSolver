package ru.insality;

public class Log {
	public static final byte system = 0;
	public static final byte error = 1;
	public static final byte debug = 2;

	private static boolean systemShow = true;
	private static boolean loadShow = true;
	private static boolean debugShow = true;

	public static void print(byte type, String msg) {

		if (!systemShow && type == system)
			return;
		if (!loadShow && type == error)
			return;
		if (!debugShow && type == debug)
			return;

		if (type == system) {
			System.out.print("[System]: ");
		}
		if (type == error) {
			System.out.print("[Error]: ");
		}
		if (type == debug) {
			System.out.print("[Debug]: ");
		}

		System.out.println(msg);
	}
}

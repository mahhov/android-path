package manuk.path.game.util;

import java.util.Random;

public class Math3D {
	public static final double EPSILON = 0.00001, sqrt3 = Math.sqrt(3);
	private static Random random = new Random();
	private static final int MAX_SQTRT = 1000, SQRT_PRECISION = 10, SQRT_TABLE_SIZE = MAX_SQTRT * SQRT_PRECISION;
	private static final double SQRT_TABLE[] = new double[SQRT_TABLE_SIZE];
	
	static {
		double number = 0, increment = 1. / SQRT_PRECISION;
		for (int i = 1; i < SQRT_TABLE_SIZE; i++) {
			number += increment;
			SQRT_TABLE[i] = Math.sqrt(number);
		}
	}
	
	public static double magnitude(double x, double y) {
		return java.lang.Math.sqrt(x * x + y * y);
	}
	
	public static double[] setMagnitude(double x, double y, double mag) {
		double m = magnitude(x, y) / mag;
		if (m == 0)
			return new double[] {x, y};
		return new double[] {x / m, y / m};
	}
	
	public static double minMax(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}
	
	public static int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}
	
	public static double min(double a, double b) {
		if (a < b)
			return a;
		return b;
	}
	
	public static int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}
	
	public static double max(double a, double b) {
		if (a > b)
			return a;
		return b;
	}
	
	public static boolean isZero(double value) {
		return value < Math3D.EPSILON && value > -Math3D.EPSILON;
	}
	
	public static double dot(double x1, double y1, double x2, double y2) {
		return x1 * x2 + y1 * y2;
	}
	
	public static double cross(double x1, double y1, double x2, double y2) {
		return x1 * y2 - y1 * x2;
	}
	
	public static double pythagorean(double hyp, double side) {
		return sqrt(hyp * hyp - side * side);
	}
	
	public static double sqrt(double number) {
		if (number < 0)
			return 0;
		int n = (int) (number * SQRT_PRECISION);
		return n < SQRT_TABLE_SIZE ? SQRT_TABLE[n] : SQRT_TABLE[SQRT_TABLE_SIZE - 1];
	}
	
	public static double abs(double v) {
		return v < 0 ? -v : v;
	}
	
	public static double random() {
		return random.nextDouble();
	}
	
	public static double random(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
}

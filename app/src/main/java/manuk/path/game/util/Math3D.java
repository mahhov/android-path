package manuk.path.game.util;

import java.util.Random;

public class Math3D {
	public static final double EPSILON = 0.00001, sqrt3 = Math.sqrt(3);
	private static Random random = new Random();
	
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
	
	public static boolean isZero(double value) {
		return value < Math3D.EPSILON && value > -Math3D.EPSILON;
	}
	
	public static double random() {
		return random.nextDouble();
	}
	
	public static double random(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
}

package manuk.path.game.util;

public class Math {
	public static double magnitude(double x, double y) {
		return java.lang.Math.sqrt(x * x + y * y);
	}
	
	public static double[] setMagnitude(double x, double y, double mag) {
		double m = magnitude(x, y) / mag;
		return new double[] {x / m, y / m};
	}
	
	public static double[] setMagnitudeMax(double x, double y, double mag) {
		double m = mag / magnitude(x, y);
		if (m > 1)
			m = 1;
		return new double[] {x * m, y * m};
	}
	
	public static double minMax(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}
	
	public static int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}
	
	public static int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}
}

package manuk.path.game;

class Util {
	static double magnitude(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
	
	static double[] setMagnitude(double x, double y, double mag) {
		double m = magnitude(x, y) / mag;
		return new double[] {x / m, y / m};
	}
	
	static double[] setMagnitudeMax(double x, double y, double mag) {
		double m = mag / magnitude(x, y);
		if (m > 1)
			m = 1;
		return new double[] {x * m, y * m};
	}
	
	static double minMax(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}
}

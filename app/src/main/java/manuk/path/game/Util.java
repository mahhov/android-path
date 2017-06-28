package manuk.path.game;

class Util {
	static double magnitude(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
	
	static double[] setMagnitude(double x, double y, double mag) {
		double m = magnitude(x, y) / mag;
		return new double[] {x / m, y / m};
	}
	
	static double[] setMagnitudeMax(double x, double y, double mag) { // todo: rewrite this
		double m = magnitude(x, y);
		if (m > mag)
			return new double[] {x / m * mag, y / m * mag};
		else
			return new double[] {x, y};
	}
	
	static double minMax(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}
}

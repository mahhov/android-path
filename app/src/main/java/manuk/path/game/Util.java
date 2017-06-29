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
	
	static int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}
	
	static int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}
	
	static class Frames {
		boolean running;
		private long start, current;
		private int fps;
		private int frameCount;
		
		Frames() {
			running = true;
		}
		
		int getFPS() {
			frameCount++;
			current = System.currentTimeMillis();
			if (current - start > 1000) {
				start = current;
				fps = frameCount;
				frameCount = 0;
			}
			return fps;
		}
	}
}

package manuk.path.game.mapgenerator;

import java.util.Random;

public abstract class MapGenerator {
	private static Random random = new Random();
	public int[][][] map;
	public int startX, startY;
	
	public abstract int[][][] generate(int width, int length, int height);
	
	static int randInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}
	
	static boolean randFlip() {
		return random.nextBoolean();
	}
}
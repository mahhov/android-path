package manuk.path.game.mapgenerator;

import java.util.Random;

public abstract class MapGenerator {
	private static long SEED = 29;
	private static Random random = new Random(SEED);
	public int[][][] map;
	public int spawnX, spawnY;
	
	public abstract int[][][] generate(int width, int length, int height);
	
	MapGenerator() {
		random.setSeed(++SEED);
	}
	
	static int randInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}
	
	static boolean randFlip() {
		return random.nextBoolean();
	}
}
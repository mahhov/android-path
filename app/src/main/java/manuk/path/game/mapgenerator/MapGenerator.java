package manuk.path.game.mapgenerator;

public abstract class MapGenerator {
	public int[][][] map;
	public int startX, startY;
	
	public abstract int[][][] generate(int width, int length, int height);
	
	static int randInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
	
	static boolean randFlip() {
		return Math.random() < .5;
	}
}
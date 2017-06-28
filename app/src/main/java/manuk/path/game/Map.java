package manuk.path.game;

import android.graphics.Color;

class Map {
	private static final double SCROLL_WEIGHT = .2;
	private int[][][] map;
	private double scrollX, scrollY;
	
	Map(int width, int length, int height) {
		map = new int[width][length][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				for (int z = 0; z < height; z++)
					map[x][y][z] = (int) (Math.random() * 2);
	}
	
	void scroll(double toX, double toY) {
		scrollX = Util.minMax(scrollX + (toX - scrollX) * SCROLL_WEIGHT, 0, map.length);
		scrollY = Util.minMax(scrollY + (toY - scrollY) * SCROLL_WEIGHT, 0, map[0].length);
	}
	
	void draw(Painter painter) {
		int endX = (int) scrollX + Engine.VIEW_WIDTH + 1;
		int endY = (int) scrollY + Engine.VIEW_HEIGHT + 1;
		for (int x = (int) scrollX; x < endX; x++)
			for (int y = (int) scrollY; y < endY; y++)
				if (map[x][y][0] == 1)
					World.drawBlock(painter, x - scrollX + .5, y - scrollY + .5, Color.GRAY);
	}
}

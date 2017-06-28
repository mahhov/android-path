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
		for (int x = 0; x < Engine.VIEW_WIDTH; x++)
			for (int y = 0; y < Engine.VIEW_HEIGHT; y++)
				if (map[x + (int) scrollX][y + (int) scrollY][0] == 1)
					World.drawBlock(painter, x, y, Color.GRAY);
	}
}

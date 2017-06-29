package manuk.path.game;

import android.graphics.Color;

class Map {
	private static final double SCROLL_WEIGHT = .2;
	private final int width, length, height;
	private int[][][] map;
	double scrollX, scrollY;
	
	Map(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		map = new int[width][length][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				for (int z = 0; z < 1; z++)
					if (Math.random() > .95)
						map[x][y][z] = 1;
	}
	
	void scroll(double toX, double toY) {
		scrollX = Util.minMax(scrollX + (toX - Engine.VIEW_WIDTH / 2 - scrollX) * SCROLL_WEIGHT, 0, width - Engine.VIEW_WIDTH);
		scrollY = Util.minMax(scrollY + (toY - Engine.VIEW_HEIGHT / 2 - scrollY) * SCROLL_WEIGHT, 0, length - Engine.VIEW_WIDTH);
	}
	
	private boolean isEmpty(int x, int y, int z, int startX, int endX, int startY, int endY) {
		return x >= 0 && x < width && y >= 0 && y < length && z >= 0 && z < height && map[x][y][z] == 0;
	}
	
	void draw(Painter painter) {
		boolean top, front, right;
		
		int startX = (int) scrollX;
		int startY = (int) scrollY;
		int endX = startX + Engine.VIEW_WIDTH + 1;
		int endY = startY + Engine.VIEW_HEIGHT + 1;
		
		for (int z = 0; z < height; z++)
			for (int x = startX; x < endX; x++)
				for (int y = (int) scrollY; y < endY; y++)
					if (map[x][y][z] == 1) {
						right = isEmpty(x + 1, y, z, startX, endX, startY, endY);
						front = isEmpty(x, y + 1, z, startX, endX, startY, endY);
						top = isEmpty(x, y, z + 1, startX, endX, startY, endY);
						
						MapPainter.drawBlock(painter, x - scrollX, y - scrollY, z, right, front, top, Color.GRAY);
					}
	}
}

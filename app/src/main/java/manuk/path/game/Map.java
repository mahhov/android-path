package manuk.path.game;

import android.graphics.Color;

class Map {
	private static final double SCROLL_WEIGHT = .2;
	private final int width, length, height;
	private int[][][] map;
	private boolean[][] shadow;
	double scrollX, scrollY;
	
	Map(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		map = new int[width][length][height];
		this.shadow = new boolean[width][length];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++) {
				boolean shadow = false;
				for (int z = height - 1; z >= 0; z--)
					if (Math.random() > .98) {
						map[x][y][z] = 1;
						shadow = true;
					} else if (z == 0)
						this.shadow[x][y] = shadow;
			}
	}
	
	void scroll(double toX, double toY) {
		scrollX = Util.minMax(scrollX + (toX - Engine.VIEW_WIDTH / 2 - scrollX) * SCROLL_WEIGHT, 0, width - Engine.VIEW_WIDTH);
		scrollY = Util.minMax(scrollY + (toY - Engine.VIEW_HEIGHT / 2 - scrollY) * SCROLL_WEIGHT, 0, length - Engine.VIEW_WIDTH);
	}
	
	private boolean isEmpty(int x, int y, int z, int startX, int endX, int startY, int endY) {
		return !(x >= 0 && x < width && y >= 0 && y < length && z >= 0 && z < height) || map[x][y][z] == 0;
	}
	
	boolean isMoveable(int x, int y, int z) {
		return x >= 0 && x < width && y >= 0 && y < length && z >= 0 && z < height && map[x][y][z] == 0;
	}
	
	void draw() {
		boolean side[] = new boolean[6];
		int startX = (int) scrollX;
		int startY = (int) scrollY;
		int endX = startX + Engine.VIEW_WIDTH + 1;
		int endY = startY + Engine.VIEW_HEIGHT + 1;
		int midX = startX + Engine.VIEW_WIDTH / 2;
		int midY = startY + Engine.VIEW_HEIGHT / 2;
		
		
		for (int x = startX; x < endX; x++)
			for (int y = (int) scrollY; y < endY; y++)
				if (shadow[x][y])
					MapPainter.drawFlat(x - scrollX, y - scrollY, 0, Color.GRAY);
		
		for (int z = 0; z < height; z++)
			for (int x = startX; x < endX; x++)
				for (int y = (int) scrollY; y < endY; y++)
					if (map[x][y][z] == 1) {
						side[MapPainter.LEFT] = x > midX && isEmpty(x - 1, y, z, startX, endX, startY, endY);
						side[MapPainter.RIGHT] = x < midX && isEmpty(x + 1, y, z, startX, endX, startY, endY);
						side[MapPainter.BACK] = y > midY && isEmpty(x, y - 1, z, startX, endX, startY, endY);
						side[MapPainter.FRONT] = y < midY && isEmpty(x, y + 1, z, startX, endX, startY, endY);
						side[MapPainter.TOP] = isEmpty(x, y, z + 1, startX, endX, startY, endY);
						
						MapPainter.drawBlock(x - scrollX, y - scrollY, z, side, Color.LTGRAY);
					}
	}
}

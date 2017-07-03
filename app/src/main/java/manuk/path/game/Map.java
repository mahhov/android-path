package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.mapgenerator.MapGenerator;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Map {
	private static final double SCROLL_WEIGHT = .2;
	public final int width, length, height;
	private int[][][] map;
	private boolean[][] shadow;
	public double scrollX, scrollY;
	public IntersectionFinder intersectionFinder;
	
	Map(int width, int length, int height, MapGenerator mapGenerator) {
		this.width = width;
		this.length = length;
		this.height = height;
		map = mapGenerator.map;
		
		this.shadow = new boolean[width][length];
		boolean shadow;
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++) {
				shadow = false;
				for (int z = height - 1; z > 0; z--)
					if (map[x][y][z] == 1) {
						shadow = true;
						break;
					}
				this.shadow[x][y] = shadow;
			}
		
		intersectionFinder = new IntersectionFinder(this);
	}
	
	void scroll(double toX, double toY) {
		scrollX = Math3D.minMax(scrollX + (toX - Measurements.SCALED_VIEW_WIDTH / 2 - scrollX) * SCROLL_WEIGHT, 0, width - Measurements.SCALED_VIEW_WIDTH);
		scrollY = Math3D.minMax(scrollY + (toY - Measurements.SCALED_VIEW_HEIGHT / 2 - scrollY) * SCROLL_WEIGHT, 0, length - Measurements.SCALED_VIEW_WIDTH);
	}
	
	public boolean isInBounds(int x, int y, int z) {
		return x >= 0 && x < width && y >= 0 && y < length && z >= 0 && z < height;
	}
	
	public boolean isEmpty(int x, int y, int z) {
		return !isInBounds(x, y, z) || map[x][y][z] == 0;
	}
	
	public boolean isMoveable(int x, int y, int z) {
		return isInBounds(x, y, z) && map[x][y][z] == 0;
	}
	
	void draw() {
		boolean side[] = new boolean[6];
		int startX = (int) scrollX;
		int startY = (int) scrollY;
		int endX = Math3D.min(startX + Measurements.SCALED_VIEW_WIDTH + 1, width);
		int endY = Math3D.min(startY + Measurements.SCALED_VIEW_HEIGHT + 1, length);
		int midX = startX + Measurements.SCALED_VIEW_WIDTH / 2;
		int midY = startY + Measurements.SCALED_VIEW_HEIGHT / 2;
		
		
		for (int x = startX; x < endX; x++)
			for (int y = (int) scrollY; y < endY; y++)
				if (shadow[x][y])
					MapPainter.drawFlat(x - scrollX, y - scrollY, 0, Color.GRAY);
		
		for (int z = 0; z < height; z++)
			for (int x = startX; x < endX; x++)
				for (int y = (int) scrollY; y < endY; y++)
					if (map[x][y][z] == 1) {
						side[MapPainter.LEFT] = x > midX && isEmpty(x - 1, y, z);
						side[MapPainter.RIGHT] = x < midX && isEmpty(x + 1, y, z);
						side[MapPainter.BACK] = y > midY && isEmpty(x, y - 1, z);
						side[MapPainter.FRONT] = y < midY && isEmpty(x, y + 1, z);
						side[MapPainter.TOP] = isEmpty(x, y, z + 1);
						
						MapPainter.drawBlock(x - scrollX, y - scrollY, z, side, MapPainter.MAP_COLOR);
					}
	}
}

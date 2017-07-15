package manuk.path.game.map;

import android.graphics.Color;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Map {
	private static final int[] COLOR = MapPainter.createColorShade(0, 100, 0);
	private static final double SCROLL_WEIGHT = .1;
	public final int width, length, height;
	private int[][][] map;
	private boolean[][] shadow;
	public LList<MapEntity>[][][] entity; // todo: see if we can refactor intersectionFinder to make this private
	public double scrollX, scrollY;
	private Scroll scroll;
	private IntersectionFinder intersectionFinder;
	
	public Map(int width, int length, int height, MapGenerator mapGenerator) {
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
		
		clearEntities();
		scroll = new Scroll(width, length);
		intersectionFinder = new IntersectionFinder(this);
	}
	
	private void clearEntities() {
		entity = new LList[width][length][MapEntity.ENTITY_LAYERS_COUNT];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				for (int layer = 0; layer < MapEntity.ENTITY_LAYERS_COUNT; layer++)
					entity[x][y][layer] = new LList<>();
	}
	
	public void moveEntity(double[] pos, MapEntity entity) {
		removeEntity(entity);
		entity.mapX = pos[0];
		entity.mapY = pos[1];
		entity.node = this.entity[(int) entity.mapX][(int) entity.mapY][entity.layer].addHead(entity);
	}
	
	public IntersectionFinder.Intersection moveEntity(double[] orig, double[] dir, double maxMove, boolean allowSlide, MapEntity entity) {
		removeEntity(entity);
		IntersectionFinder.Intersection intersection = intersectionFinder.find(entity.id, orig, dir, maxMove, allowSlide, entity.layer, entity.size);
		entity.mapX = intersection.x;
		entity.mapY = intersection.y;
		entity.node = this.entity[(int) entity.mapX][(int) entity.mapY][entity.layer].addHead(entity);
		return intersection;
	}
	
	public IntersectionFinder.Intersection moveEntity(double[] orig, double[] dir, double maxMove, boolean allowSlide, int entityLayer, MapEntity entity) {
		removeEntity(entity);
		IntersectionFinder.Intersection intersection = intersectionFinder.find(entity.id, orig, dir, maxMove, allowSlide, entityLayer, entity.size);
		entity.mapX = intersection.x;
		entity.mapY = intersection.y;
		entity.node = this.entity[(int) entity.mapX][(int) entity.mapY][entity.layer].addHead(entity);
		return intersection;
	}
	
	public void removeEntity(MapEntity entity) {
		if (entity.node != null)
			this.entity[(int) entity.mapX][(int) entity.mapY][entity.layer].remove(entity.node);
	}
	
	public void scroll(double toX, double toY) {
		scrollX = Math3D.minMax(scrollX + (toX - Measurements.SCALED_VIEW_WIDTH / 2 - scrollX) * SCROLL_WEIGHT, 0, width - Measurements.SCALED_VIEW_WIDTH);
		scrollY = Math3D.minMax(scrollY + (toY - Measurements.SCALED_VIEW_HEIGHT / 2 - scrollY) * SCROLL_WEIGHT, 0, length - Measurements.SCALED_VIEW_WIDTH);
		scroll.setScroll(scrollX, scrollY);
	}
	
	private boolean isInBounds(int x, int y, int z) {
		return x >= 0 && x < width && y >= 0 && y < length && z >= 0 && z < height;
	}
	
	// true for out of bounds
	private boolean isEmpty(int x, int y, int z) {
		return !isInBounds(x, y, z) || map[x][y][z] == 0;
	}
	
	// false for out of bounds
	public boolean isMoveable(int x, int y, int z) {
		return isInBounds(x, y, z) && map[x][y][z] == 0;
	}
	
	// false for out of bounds
	public boolean isMoveable(int x, int y, int z, int layer) {
		if (!isMoveable(x, y, z))
			return false;
		for (int i = 0; i < MapEntity.ENTITY_LAYERS_COUNT; i++)
			if (MapEntity.ENTITY_COLLISION_BLOCK[layer][i] && !entity[x][y][i].isEmpty())
				return false;
		return true;
	}
	
	public void draw() {
		for (int x = scroll.startX; x < scroll.endX; x++)
			for (int y = scroll.startY; y < scroll.endY; y++)
				if (shadow[x][y])
					MapPainter.drawFlat(x - scrollX, y - scrollY, 0, Color.GRAY);
		
		for (int z = 0; z < height; z++) {
			for (int y = scroll.startY; y < scroll.midY; y++)
				drawYZ(y, z);
			for (int y = scroll.endY - 1; y >= scroll.midY; y--)
				drawYZ(y, z);
		}
	}
	
	private void drawYZ(int y, int z) {
		for (int x = scroll.startX; x < scroll.midX; x++)
			drawXYZ(x, y, z);
		for (int x = scroll.endX - 1; x >= scroll.midX; x--)
			drawXYZ(x, y, z);
	}
	
	private void drawXYZ(int x, int y, int z) {
		for (int layer = 0; layer < MapEntity.ENTITY_LAYERS_COUNT; layer++)
			for (MapEntity e : entity[x][y][layer])
				e.draw(scrollX, scrollY);
		if (map[x][y][z] == 1) {
			boolean side[] = new boolean[6];
			side[MapPainter.LEFT] = x > scroll.midX && isEmpty(x - 1, y, z);
			side[MapPainter.RIGHT] = x <= scroll.midX && isEmpty(x + 1, y, z);
			side[MapPainter.BACK] = y > scroll.midY && isEmpty(x, y - 1, z);
			side[MapPainter.FRONT] = y <= scroll.midY && isEmpty(x, y + 1, z);
			side[MapPainter.TOP] = isEmpty(x, y, z + 1);
			MapPainter.drawBlock(x - scrollX, y - scrollY, z, 1, 1, 5, side, COLOR);
		}
	}
}

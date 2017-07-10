package manuk.path.game.map;

import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

public abstract class MapEntity {
	private static long count = 0;
	public static final int ENTITY_LAYERS_COUNT = 4;
	protected static final int ENTITY_LAYER_FRIENDLY_PROJECTILE = 0, ENTITY_LAYER_HOSTILE_PROJECTILE = 1, ENTITY_LAYER_FRIENDLY_CHARACTER = 2, ENTITY_LAYER_HOSTILE_CHARACTER = 3;
	public static final boolean[][] ENTITY_COLLISION;
	public static double MAX_ENTITY_SIZE = 0;
	
	static {
		ENTITY_COLLISION = new boolean[ENTITY_LAYERS_COUNT][ENTITY_LAYERS_COUNT];
		
		ENTITY_COLLISION[ENTITY_LAYER_FRIENDLY_PROJECTILE][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // friendly projectile & hostile char
		ENTITY_COLLISION[ENTITY_LAYER_FRIENDLY_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // friendly char & hostile char
		ENTITY_COLLISION[ENTITY_LAYER_HOSTILE_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // hostile char & hostile char
		
		for (int i = 0; i < ENTITY_LAYERS_COUNT; i++)
			for (int j = 0; j < ENTITY_LAYERS_COUNT; j++)
				ENTITY_COLLISION[i][j] = ENTITY_COLLISION[i][j] || ENTITY_COLLISION[j][i];
	}
	
	public final long id;
	public double mapX, mapY, size;
	LList<MapEntity>.Node node;
	public int layer;
	
	public MapEntity(int layer, double size) {
		id = count++;
		this.layer = layer;
		this.size = size;
		MAX_ENTITY_SIZE = Math3D.max(size, MAX_ENTITY_SIZE);
	}
	
	public void handleIntersection(double damageAmount) {
	}
	
	public abstract void draw(double scrollX, double scrollY);
}

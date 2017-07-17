package manuk.path.game.map;

import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

public abstract class MapEntity {
	private static long count = 0;
	public static final int ENTITY_LAYERS_COUNT = 7; // don't forget to increment this when creating new entity layer
	protected static final int ENTITY_LAYER_FRIENDLY_PROJECTILE = 0, ENTITY_LAYER_HOSTILE_PROJECTILE = 1, ENTITY_LAYER_FRIENDLY_CHARACTER = 2, ENTITY_LAYER_HOSTILE_CHARACTER = 3, ENTITY_LAYER_DROPPED_ITEM = 4, ENTITY_LAYER_TRANSPARENT_FRIENDLY_CHARACTER = 5, ENTITY_LAYER_PARTICLE = 6;
	public static final boolean[][] ENTITY_COLLISION_BLOCK, ENTITY_COLLISION_TRACK;
	public static double MAX_ENTITY_SIZE = 0;
	
	static {
		ENTITY_COLLISION_BLOCK = new boolean[ENTITY_LAYERS_COUNT][ENTITY_LAYERS_COUNT];
		ENTITY_COLLISION_TRACK = new boolean[ENTITY_LAYERS_COUNT][ENTITY_LAYERS_COUNT];
		
		ENTITY_COLLISION_BLOCK[ENTITY_LAYER_FRIENDLY_PROJECTILE][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // friendly projectile & hostile char
		ENTITY_COLLISION_BLOCK[ENTITY_LAYER_HOSTILE_PROJECTILE][ENTITY_LAYER_FRIENDLY_CHARACTER] = true; // hostile projectile & friendly char
		ENTITY_COLLISION_BLOCK[ENTITY_LAYER_FRIENDLY_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // friendly char & hostile char
		ENTITY_COLLISION_BLOCK[ENTITY_LAYER_HOSTILE_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // hostile char & hostile char
		
		ENTITY_COLLISION_TRACK[ENTITY_LAYER_TRANSPARENT_FRIENDLY_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // transparent friendly char and hostile char
		ENTITY_COLLISION_TRACK[ENTITY_LAYER_FRIENDLY_CHARACTER][ENTITY_LAYER_DROPPED_ITEM] = true; // friendly char and item
		//		ENTITY_COLLISION_TRACK[ENTITY_LAYER_TRANSPARENT_FRIENDLY_CHARACTER][ENTITY_LAYER_DROPPED_ITEM] = true; // transparent friendly char and item
		
		for (int i = 0; i < ENTITY_LAYERS_COUNT; i++)
			for (int j = 0; j < ENTITY_LAYERS_COUNT; j++) {
				ENTITY_COLLISION_BLOCK[i][j] = ENTITY_COLLISION_BLOCK[i][j] || ENTITY_COLLISION_BLOCK[j][i];
				ENTITY_COLLISION_TRACK[i][j] = ENTITY_COLLISION_TRACK[i][j] || ENTITY_COLLISION_TRACK[j][i];
			}
	}
	
	public final long id;
	private static long INTERSECTION_COUNT;
	private long intersectionId;
	public double mapX, mapY, size;
	public LList<MapEntity>.Node node;
	public int layer;
	
	public MapEntity(int layer, double size) {
		id = count++;
		this.layer = layer;
		this.size = size;
		MAX_ENTITY_SIZE = Math3D.max(size, MAX_ENTITY_SIZE);
	}
	
	public void handleIntersection(long intersectionId, double damageAmount) {
		if (intersectionId != this.intersectionId) {
			this.intersectionId = intersectionId;
			onIntersection(damageAmount);
		}
	}
	
	public void onIntersection(double damageAmount) {
	}
	
	public long getIntersectorId() {
		return ++INTERSECTION_COUNT;
	}
	
	public abstract void draw(double scrollX, double scrollY);
}

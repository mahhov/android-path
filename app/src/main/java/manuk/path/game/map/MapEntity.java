package manuk.path.game.map;

import manuk.path.game.util.LList;

public abstract class MapEntity {
	static final int ENTITY_LAYERS_COUNT = 4;
	protected static final int ENTITY_LAYER_FRIENDLY_PROJECTILE = 0, ENTITY_LAYER_HOSTILE_PROJECTILE = 1, ENTITY_LAYER_FRIENDLY_CHARACTER = 2, ENTITY_LAYER_HOSTILE_CHARACTER = 3;
	public static final boolean[][] ENTITY_COLLISION;
	
	static {
		ENTITY_COLLISION = new boolean[ENTITY_LAYERS_COUNT][ENTITY_LAYERS_COUNT];
		
		ENTITY_COLLISION[ENTITY_LAYER_FRIENDLY_PROJECTILE][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // friendly projectile & hostile char
		ENTITY_COLLISION[ENTITY_LAYER_FRIENDLY_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // friendly char & hostile char
		ENTITY_COLLISION[ENTITY_LAYER_HOSTILE_CHARACTER][ENTITY_LAYER_HOSTILE_CHARACTER] = true; // hostile char & hostile char
		
		for (int i = 0; i < ENTITY_LAYERS_COUNT; i++)
			for (int j = 0; j < ENTITY_LAYERS_COUNT; j++)
				ENTITY_COLLISION[i][j] = ENTITY_COLLISION[i][j] || ENTITY_COLLISION[j][i];
	}
	
	int mapX, mapY;
	LList<MapEntity>.Node node;
	int layer;
	
	public MapEntity(int layer) {
		this.layer = layer;
	}
	
	public abstract void draw(double scrollX, double scrollY);
}

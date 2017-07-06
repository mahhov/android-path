package manuk.path.game.map;

import manuk.path.game.util.LList;

public abstract class MapEntity {
	static final int ENTITY_LAYERS_COUNT = 4;
	public static final int ENTITY_LAYER_FRIENDLY_PROJECTILE = 0, ENTITY_LAYER_HOSTILE_PROJECTILE = 1, ENTITY_LAYER_FRIENDLY_CHARACTER = 2, ENTITY_LAYER_HOSTILE_CHARACTER = 3;
	
	int mapX, mapY;
	LList<MapEntity>.Node node;
	int layer;
	
	public MapEntity(int layer) {
		this.layer = layer;
	}
	
	public abstract void draw(double scrollX, double scrollY);
}

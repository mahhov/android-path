package manuk.path.game.item;

import android.graphics.Color;
import manuk.path.game.character.Player;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;

public class Item extends MapEntity {
	private double x, y;
	private int[] color;
	private boolean remove;
	
	public Item(double x, double y, Map map) {
		super(ENTITY_LAYER_DROPPED_ITEM, 1);
		this.x = x;
		this.y = y;
		color = MapPainter.createColorShade(Color.YELLOW);
		map.moveEntity(new double[] {x, y}, this);
	}
	
	// return true if need to be removed
	public boolean update(Map map, Player player) {
		if (!remove)
			return false;
		player.giveLife(5);
		map.removeEntity(this);
		return true;
	}
	
	public void onIntersection(double damageAmount) {
		remove = true;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .25, y - scrollY - .25, 0, .5, .5, .5, side, color);
	}
}

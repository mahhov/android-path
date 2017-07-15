package manuk.path.game.item;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;

public class Item extends MapEntity {
	private double x, y;
	private int[] color;
	
	public Item(double x, double y) {
		super(ENTITY_LAYER_DROPPED_ITEM, 0);
		this.x = x;
		this.y = y;
		color = MapPainter.createColorShade(Color.YELLOW);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		if (node == null)
			map.moveEntity(new double[] {x, y}, this);
		return false;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .25, y - scrollY - .25, 0, .5, .5, .5, side, color);
	}
}

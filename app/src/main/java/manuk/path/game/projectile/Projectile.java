package manuk.path.game.projectile;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;

public class Projectile extends MapEntity {
	double x, y;
	double size;
	double damage;
	int[] color;
	
	public Projectile(int layer, double x, double y, double size, double damage, int color) {
		super(layer, size);
		this.x = x;
		this.y = y;
		this.size = size;
		this.damage = damage;
		this.color = MapPainter.createColorShade(color);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		return false;
	}
	
	void expire(Map map, MapEntity entity) {
		map.removeEntity(this);
		if (entity != null)
			entity.handleIntersection(getIntersectorId(), damage);
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - size, y - scrollY - size, 0, size * 2, size * 2, .5, side, color);
	}
}

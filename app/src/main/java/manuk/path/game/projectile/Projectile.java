package manuk.path.game.projectile;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;

public class Projectile extends MapEntity {
	private double x, y, dir[], speed;
	private int[] color;
	
	public Projectile(int layer, double x, double y, double dirX, double dirY, double speed, int color) {
		super(layer, .5);
		this.x = x;
		this.y = y;
		dir = new double[] {dirX, dirY};
		this.speed = speed;
		this.color = MapPainter.createColorShade(color);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, dir, speed, false, this);
		x = intersection.x;
		y = intersection.y;
		if (intersection.state != IntersectionFinder.Intersection.COLLISION_NONE) {
			map.removeEntity(this);
			if (intersection.state == IntersectionFinder.Intersection.COLLISION_ENTITY)
				intersection.entityCollide.handleIntersection(5);
			return true;
		}
		return false;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .25, y - scrollY - .25, 0, .5, .5, .5, side, color);
	}
}

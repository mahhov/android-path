package manuk.path.game.projectile;

import manuk.path.game.map.Map;
import manuk.path.game.util.IntersectionFinder;

public class MovingProjectile extends Projectile {
	private double dir[], speed;
	
	public MovingProjectile(int layer, double x, double y, double dirX, double dirY, double speed, double damage, int color) {
		super(layer, x, y, .5, damage, color);
		dir = new double[] {dirX, dirY};
		this.speed = speed;
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, dir, speed, false, this);
		x = intersection.x;
		y = intersection.y;
		if (intersection.state != IntersectionFinder.Intersection.COLLISION_NONE) {
			if (intersection.state == IntersectionFinder.Intersection.COLLISION_ENTITY)
				expire(map, intersection.entityCollide);
			else
				expire(map, null);
			return true;
		}
		return false;
	}
}
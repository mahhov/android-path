package manuk.path.game.projectile;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.util.IntersectionFinder;

public class DurationProjectile extends Projectile {
	private double duration;
	
	public DurationProjectile(int layer, double x, double y, double size, double damage, int color, double duration, Map map) {
		super(layer, x, y, size, damage, color);
		this.duration = duration;
		map.moveEntity(new double[] {x, y}, this);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {1, 0}, 0, false, MapEntity.ENTITY_LAYER_HOSTILE_PROJECTILE, this);
		if (intersection.state == IntersectionFinder.Intersection.COLLISION_ENTITY)
			intersection.entityCollide.handleIntersection(getIntersectorId(), damage);
		if (--duration == 0) {
			expire(map, null);
			return true;
		}
		return false;
	}
}
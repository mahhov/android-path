package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.item.Item;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

import static manuk.path.game.util.Math3D.setMagnitude;

public class ProjectileEnemy extends Enemy {
	private static final double WANDER_THRESHOLD = .98, WANDER_DISTANCE = 5, ACTIVE_DISTANCE = 10, DAMAGE_RANGE = 2;
	private static final double PATH_FIND_FRICTION = .8;
	private static final double ITEM_DROP_RATE = .5;
	private double[] awayFromIntersection;
	
	public ProjectileEnemy(double spawnX, double spawnY) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, Color.rgb(150, 0, 150), .05, 10, 10, 0, 0, 0);
		awayFromIntersection = new double[2];
	}
	
	// return true if need to be removed
	public boolean update(Player player, Map map, LList<Projectile> projectile, LList<Item> item) {
		if (updateAttack()) {
			double[] toPlayer = Math3D.setMagnitude(player.x - x, player.y - y, 1);
			projectile.addHead(new Projectile(MapEntity.ENTITY_LAYER_HOSTILE_PROJECTILE, x, y, toPlayer[0], toPlayer[1], .1, Color.RED));
		}
		
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE)
			beginAttack(0);
		else if (distance < ACTIVE_DISTANCE) {
			double[] toPlayer = Math3D.setMagnitude(player.x - x, player.y - y, 1);
			moveDeltaX = toPlayer[0] + awayFromIntersection[0];
			moveDeltaY = toPlayer[1] + awayFromIntersection[1];
			awayFromIntersection[0] *= PATH_FIND_FRICTION;
			awayFromIntersection[1] *= PATH_FIND_FRICTION;
			handleEnemyIntersection(moveByDir(map));
		} else if (Math3D.random() > WANDER_THRESHOLD) {
			goalX = x + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			goalY = y + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			handleEnemyIntersection(moveToGoal(map));
		}
		if (life <= 0) {
			map.removeEntity(this);
			if (Math3D.random() < ITEM_DROP_RATE)
				item.addHead(new Item(x, y));
			player.gainExp(5);
			return true;
		}
		return false;
	}
	
	private void handleEnemyIntersection(IntersectionFinder.Intersection intersection) {
		if (intersection != null && intersection.state == IntersectionFinder.Intersection.COLLISION_ENTITY && intersection.entityCollisionLayer == ENTITY_LAYER_HOSTILE_CHARACTER)
			awayFromIntersection = setMagnitude(x - intersection.entityCollisionX, y - intersection.entityCollisionY, 1);
	}
}

package manuk.path.game.character;

import manuk.path.game.item.Item;
import manuk.path.game.map.Map;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

import static manuk.path.game.util.Math3D.setMagnitude;

public abstract class Enemy extends Character {
	public static final int ENEMY_TYPE_COUNT = 2;
	public static final int ENEMY_TYPE_MELEE = 0, ENEMY_TYPE_PROJECTILE = 1;
	
	private final double WANDER_THRESHOLD;
	private final double WANDER_DISTANCE;
	private final double ACTIVE_DISTANCE;
	private final double DAMAGE_RANGE;
	private final double PATH_FIND_FRICTION;
	private final double ITEM_DROP_RATE;
	
	private double[] awayFromIntersection;
	
	Enemy(int layer, double spawnX, double spawnY, int color, double moveSpeed, int attackTime, double maxLife, double wanderThreshold, double wanderDistance, double activeDistance, double damageRange, double pathFindFriction, double itemDropRate) {
		super(layer, spawnX, spawnY, color, moveSpeed, attackTime, maxLife, 0, 0, 0);
		WANDER_THRESHOLD = wanderThreshold;
		WANDER_DISTANCE = wanderDistance;
		ACTIVE_DISTANCE = activeDistance;
		DAMAGE_RANGE = damageRange;
		PATH_FIND_FRICTION = pathFindFriction;
		ITEM_DROP_RATE = itemDropRate;
		awayFromIntersection = new double[2];
	}
	
	public static Enemy create(int x, int y, int type) {
		switch (type) {
			case ENEMY_TYPE_MELEE:
				return new MeleeEnemy(x, y);
			case ENEMY_TYPE_PROJECTILE:
				return new ProjectileEnemy(x, y);
			default:
				System.out.println("UNRECOGNIZED ENEMY TYPE");
				return new MeleeEnemy(x, y);
		}
	}
	
	// return true if need to be removed
	public boolean update(Player player, Map map, LList<Projectile> projectile, LList<Item> item) {
		if (updateAttack())
			doAttack(player, projectile);
		
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE)
			beginAttack(0);
		else if (distance < ACTIVE_DISTANCE)
			chasePlayer(player, map);
		else if (Math3D.random() > WANDER_THRESHOLD) {
			wander(map);
		}
		if (life <= 0) {
			die(player, map, item);
			return true;
		}
		return false;
	}
	
	void doAttack(Player player, LList<Projectile> projectile) {
	}
	
	private void chasePlayer(Player player, Map map) {
		double[] toPlayer = Math3D.setMagnitude(player.x - x, player.y - y, 1);
		moveDeltaX = toPlayer[0] + awayFromIntersection[0];
		moveDeltaY = toPlayer[1] + awayFromIntersection[1];
		awayFromIntersection[0] *= PATH_FIND_FRICTION;
		awayFromIntersection[1] *= PATH_FIND_FRICTION;
		handleEnemyIntersection(moveByDir(map));
	}
	
	private void wander(Map map) {
		goalX = x + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
		goalY = y + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
		handleEnemyIntersection(moveToGoal(map));
	}
	
	private void die(Player player, Map map, LList<Item> item) {
		map.removeEntity(this);
		if (Math3D.random() < ITEM_DROP_RATE)
			item.addHead(new Item(x, y));
		player.gainExp(5);
	}
	
	private void handleEnemyIntersection(IntersectionFinder.Intersection intersection) {
		if (intersection != null && intersection.state == IntersectionFinder.Intersection.COLLISION_ENTITY && intersection.entityCollisionLayer == ENTITY_LAYER_HOSTILE_CHARACTER)
			awayFromIntersection = setMagnitude(x - intersection.entityCollisionX, y - intersection.entityCollisionY, 1);
	}
}

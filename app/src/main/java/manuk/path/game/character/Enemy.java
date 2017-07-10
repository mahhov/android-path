package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.util.Math3D;

import static manuk.path.game.util.Math3D.setMagnitude;

public class Enemy extends Character {
	private static final double WANDER_THRESHOLD = .98, WANDER_DISTANCE = 5, ACTIVE_DISTANCE = 10, DAMAGE_RANGE = 2;
	private static final double PATH_FIND_FRICTION = .8;
	private double[] awayFromIntersection;
	
	public Enemy(double spawnX, double spawnY) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, Color.RED, .1, 10, 10);
		awayFromIntersection = new double[2];
	}
	
	public void update(Player player, Map map) {
		if (updateAttack())
			player.takeDamage(1);
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE)
			attack();
		else if (distance < ACTIVE_DISTANCE) {
			double[] toPlayer = Math3D.setMagnitude(player.x - x, player.y - y, 1);
			moveDeltaX = toPlayer[0] + awayFromIntersection[0];
			moveDeltaY = toPlayer[1] + awayFromIntersection[1];
			awayFromIntersection[0] *= PATH_FIND_FRICTION;
			awayFromIntersection[1] *= PATH_FIND_FRICTION;
			handleIntersection(moveByDir(map));
		} else if (Math3D.random() > WANDER_THRESHOLD) {
			goalX = x + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			goalY = y + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			handleIntersection(moveToGoal(map));
		}
	}
	
	private void handleIntersection(double[] intersection) {
		if (intersection != null && intersection[2] == 2 && intersection[5] == ENTITY_LAYER_HOSTILE_CHARACTER)
			awayFromIntersection = setMagnitude(x - intersection[3], y - intersection[4], 1);
	}
}

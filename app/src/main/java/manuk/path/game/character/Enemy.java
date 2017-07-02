package manuk.path.game.character;

import manuk.path.game.Map;
import manuk.path.game.util.Math3D;

public class Enemy extends Character {
	private static final double WANDER_THRESHOLD = .98, WANDER_DISTANCE = 5, ACTIVE_DISTANCE = 10, DAMAGE_RANGE = 2;
	
	public Enemy(double startX, double startY) {
		super(startX, startY, .1, 10, 10);
	}
	
	public void update(Player player, Map map) {
		if (updateAttack())
			player.takeDamage(1);
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE) {
			attack();
		} else if (distance < ACTIVE_DISTANCE) {
			goalX = player.x;
			goalY = player.y;
			move(map.intersectionFinder);
		} else if (Math3D.random() > WANDER_THRESHOLD) {
			goalX = x + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			goalY = y + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			move(map.intersectionFinder);
		}
	}
}

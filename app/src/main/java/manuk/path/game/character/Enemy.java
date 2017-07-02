package manuk.path.game.character;

import manuk.path.game.Map;
import manuk.path.game.util.Math3D;

public class Enemy extends Character {
	private double WANDER_THRESHOLD = .98, WANDER_DISTANCE = 5, ACTIVE_DISTANCE = 10;
	
	private boolean isActive;
	
	public Enemy(double startX, double startY) {
		super(startX, startY);
		speed = .1;
	}
	
	public void update(Player player, Map map) {
		double deltaX = player.x - x;
		double deltaY = player.y - y;
		isActive = Math3D.magnitude(deltaX, deltaY) < ACTIVE_DISTANCE;
		if (isActive) {
			double[] offXY = Math3D.setMagnitude(deltaX, deltaY, 1);
			goalX = x + deltaX - offXY[0];
			goalY = y + deltaY - offXY[1];
		} else if (Math3D.random() > WANDER_THRESHOLD) {
			goalX = x + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
			goalY = y + Math3D.random(-WANDER_DISTANCE, WANDER_DISTANCE);
		}
		move(map.intersectionFinder);
	}
}

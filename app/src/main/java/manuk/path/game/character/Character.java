package manuk.path.game.character;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.Math3D;

abstract class Character extends MapEntity {
	public double x, y;
	double goalX, goalY;
	double moveDeltaX, moveDeltaY;
	
	private int[] color;
	private double speed;
	private int attackSpeed, attackWait;
	private boolean attacking;
	private double maxLife;
	double life;
	
	Character(int layer, double spawnX, double spawnY, int color, double speed, int attackSpeed, double maxLife) {
		super(layer, .5);
		goalX = x = spawnX;
		goalY = y = spawnY;
		this.color = MapPainter.createColorShade(color);
		this.speed = speed;
		this.attackSpeed = attackSpeed;
		this.maxLife = life = maxLife;
	}
	
	double getLifePercent() {
		return life / maxLife;
	}
	
	void takeDamage(double amount) {
		life = Math3D.max(life - amount, 0);
	}
	
	boolean attack() {
		if (!attacking) {
			attacking = true;
			attackWait = attackSpeed;
			return true;
		}
		return false;
	}
	
	boolean updateAttack() {
		if (attacking && attackWait-- == 0) {
			attacking = false;
			return true;
		}
		return false;
	}
	
	IntersectionFinder.Intersection moveToGoal(Map map) {
		moveDeltaX = goalX - x;
		moveDeltaY = goalY - y;
		return moveByDir(map);
	}
	
	IntersectionFinder.Intersection moveByDir(Map map) {
		if (attacking)
			return null;
		double dist = Math3D.min(speed, Math3D.magnitude(moveDeltaX, moveDeltaY));
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {moveDeltaX, moveDeltaY}, dist, true, this);
		x = intersection.x;
		y = intersection.y;
		return intersection;
	}
	
	public void handleIntersection(double damageAmount) {
		takeDamage(damageAmount);
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, 1, 1, .5, side, color);
	}
}

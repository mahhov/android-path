package manuk.path.game.character;

import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.Math3D;

abstract class Character {
	public double x, y;
	double goalX, goalY;
	
	private double speed;
	private int attackSpeed, attackWait;
	private boolean attacking;
	private double maxLife, life;
	
	public Character(double spawnX, double spawnY, double speed, int attackSpeed, double maxLife) {
		goalX = x = spawnX;
		goalY = y = spawnY;
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
	
	void move(IntersectionFinder intersectionFinder) {
		if (attacking)
			return;
		double deltaX = goalX - x, deltaY = goalY - y;
		double dist = Math3D.min(speed, Math3D.magnitude(deltaX, deltaY));
		double[] intersection = intersectionFinder.find(new double[] {x, y}, new double[] {deltaX, deltaY}, dist, true);
		x = intersection[0];
		y = intersection[1];
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, side);
	}
}

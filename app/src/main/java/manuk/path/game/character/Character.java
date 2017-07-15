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
	double moveSpeed;
	private int attackTime, attackTimeCur;
	private boolean attacking;
	private double maxLife, maxStamina;
	double life, stamina;
	private int staminaRegenDelay, staminaRegenDelayCur, staminaRegenRate;
	
	Character(int layer, double spawnX, double spawnY, int color, double moveSpeed, int attackTime, double maxLife, double maxStamina, int staminaRegenRate, int staminaRegenDelay) {
		super(layer, .5);
		goalX = x = spawnX;
		goalY = y = spawnY;
		this.color = MapPainter.createColorShade(color);
		this.moveSpeed = moveSpeed;
		this.attackTime = attackTime;
		this.maxLife = life = maxLife;
		this.maxStamina = stamina = maxStamina;
		this.staminaRegenRate = staminaRegenRate;
		this.staminaRegenDelay = staminaRegenDelay;
	}
	
	double getLifePercent() {
		return life / maxLife;
	}
	
	void takeDamage(double amount) {
		life = Math3D.max(life - amount, 0);
	}
	
	double getStaminaPercent() {
		return stamina / maxStamina;
	}
	
	boolean useStamina(double amount) {
		if (stamina > amount) {
			stamina -= amount;
			staminaRegenDelayCur = staminaRegenDelay;
			return true;
		}
		return false;
	}
	
	void staminaRegen() {
		if (staminaRegenDelayCur == 0)
			stamina = Math3D.min(stamina + staminaRegenRate, maxStamina);
		else
			staminaRegenDelayCur--;
	}
	
	boolean beginAttack(int staminaCost) {
		if (attacking || !useStamina(staminaCost))
			return false;
		attacking = true;
		attackTimeCur = attackTime;
		return true;
	}
	
	boolean updateAttack() {
		if (attacking && attackTimeCur-- == 0) {
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
		double dist = Math3D.min(moveSpeed, Math3D.magnitude(moveDeltaX, moveDeltaY));
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {moveDeltaX, moveDeltaY}, dist, true, this);
		x = intersection.x;
		y = intersection.y;
		return intersection;
	}
	
	IntersectionFinder.Intersection moveByDir(Map map, double moveSpeed, int entityLayer) {
		if (attacking)
			return null;
		double dist = Math3D.min(moveSpeed, Math3D.magnitude(moveDeltaX, moveDeltaY));
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {moveDeltaX, moveDeltaY}, dist, true, entityLayer, this);
		x = intersection.x;
		y = intersection.y;
		return intersection;
	}
	
	public void handleProjectileIntersection(double damageAmount) {
		takeDamage(damageAmount);
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, 1, 1, .5, side, color);
	}
}

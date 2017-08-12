package manuk.path.game.character;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.Math3D;

public abstract class Character extends MapEntity {
	public double x, y;
	protected double goalX, goalY;
	protected double moveDeltaX, moveDeltaY;
	
	private int[] color;
	double moveSpeed;
	protected Counter attackTime;
	protected double maxLife, maxStamina;
	protected double life, stamina;
	private int staminaRegenDelay, staminaRegenDelayCur, staminaRegenRate;
	
	protected Character(int layer, double spawnX, double spawnY, int color, double moveSpeed, int attackTime, double maxLife, double maxStamina, int staminaRegenRate, int staminaRegenDelay, Map map) {
		super(layer, .5);
		goalX = x = spawnX;
		goalY = y = spawnY;
		this.color = MapPainter.createColorShade(color);
		this.moveSpeed = moveSpeed;
		this.attackTime = new Counter(attackTime);
		this.maxLife = life = maxLife;
		this.maxStamina = stamina = maxStamina;
		this.staminaRegenRate = staminaRegenRate;
		this.staminaRegenDelay = staminaRegenDelay;
		map.moveEntity(new double[] {x, y}, this);
	}
	
	void takeDamage(double amount) {
		life = Math3D.max(life - amount, 0);
	}
	
	void takeHeal(double amount) {
		life = Math3D.min(life + amount, maxLife);
	}
	
	boolean useStamina(double amount) {
		if (stamina >= amount) {
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
	
	protected boolean beginAttack(int staminaCost) {
		return !attackTime.active() && useStamina(staminaCost) && attackTime.begin();
	}
	
	protected boolean updateAttack() {
		return attackTime.update();
	}
	
	protected IntersectionFinder.Intersection moveToGoal(Map map) {
		moveDeltaX = goalX - x;
		moveDeltaY = goalY - y;
		return moveByDir(map);
	}
	
	IntersectionFinder.Intersection moveToGoal(Map map, double moveSpeed, int entityLayer) {
		moveDeltaX = goalX - x;
		moveDeltaY = goalY - y;
		return moveByDir(map, moveSpeed, entityLayer);
	}
	
	protected IntersectionFinder.Intersection moveByDir(Map map) {
		if (attackTime.active())
			return null;
		double dist = Math3D.min(moveSpeed, Math3D.magnitude(moveDeltaX, moveDeltaY));
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {moveDeltaX, moveDeltaY}, dist, true, this);
		x = intersection.x;
		y = intersection.y;
		return intersection;
	}
	
	IntersectionFinder.Intersection moveByDir(Map map, double moveSpeed, int entityLayer) {
		if (attackTime.active())
			return null;
		double dist = Math3D.min(moveSpeed, Math3D.magnitude(moveDeltaX, moveDeltaY));
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {moveDeltaX, moveDeltaY}, dist, true, entityLayer, this);
		x = intersection.x;
		y = intersection.y;
		return intersection;
	}
	
	public void onIntersection(double damageAmount) {
		takeDamage(damageAmount);
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, 1, 1, .5, side, color);
	}
	
	public static class Counter {
		public int maxValue, value;
		
		public Counter(int maxValue) {
			this.maxValue = maxValue;
			value = -1;
		}
		
		public boolean active() {
			return value > -1;
		}
		
		boolean begin() {
			if (active())
				return false;
			value = maxValue;
			return true;
		}
		
		public boolean begin(int value) {
			if (active() && value < this.value)
				return false;
			this.value = value;
			return true;
		}
		
		public boolean update() {
			return active() && value-- == 0;
		}
		
		void stop() {
			value = -1;
		}
	}
}	
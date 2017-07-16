package manuk.path.game.character;

import manuk.path.game.item.Item;
import manuk.path.game.map.Map;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;

public abstract class Enemy extends Character {
	public static final int ENEMY_TYPE_COUNT = 2;
	public static final int ENEMY_TYPE_MELEE = 0, ENEMY_TYPE_PROJECTILE = 1;
	
	Enemy(int layer, double spawnX, double spawnY, int color, double moveSpeed, int attackTime, double maxLife, double maxStamina, int staminaRegenRate, int staminaRegenDelay) {
		super(layer, spawnX, spawnY, color, moveSpeed, attackTime, maxLife, maxStamina, staminaRegenRate, staminaRegenDelay);
	}
	
	public abstract boolean update(Player player, Map map, LList<Projectile> projectile, LList<Item> item);
	
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
}

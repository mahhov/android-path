package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

class MeleeEnemy extends Enemy {
	private static final double WANDER_THRESHOLD = .98;
	private static final double WANDER_DISTANCE = 5;
	private static final double ACTIVE_DISTANCE = 10;
	private static final double DAMAGE_RANGE = 2;
	private static final double KEEP_AWAY_DISTANCE = 0;
	private static final double PATH_FIND_FRICTION = .8;
	private static final double ITEM_DROP_RATE = .5;
	
	private static final int COLOR = Color.RED;
	private static final double MOVE_SPEED = .05, MAX_LIFE = 10, ATTACK_DAMAGE = 10;
	private static final int ATTACK_TIME = 15;
	
	MeleeEnemy(double spawnX, double spawnY, Map map) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, COLOR, MOVE_SPEED, ATTACK_TIME, MAX_LIFE, WANDER_THRESHOLD, WANDER_DISTANCE, ACTIVE_DISTANCE, DAMAGE_RANGE, KEEP_AWAY_DISTANCE, PATH_FIND_FRICTION, ITEM_DROP_RATE, map);
	}
	
	void doAttack(Map map, Player player, LList<Projectile> projectile, LList<Particle> particle) {
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE * 2)
			player.takeDamage(ATTACK_DAMAGE);
	}
}

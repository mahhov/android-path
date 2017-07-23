package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.DelayedProjectile;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

class RaiseEnemy extends Enemy {
	private static final double WANDER_THRESHOLD = .98;
	private static final double WANDER_DISTANCE = 5;
	private static final double ACTIVE_DISTANCE = 10;
	private static final double DAMAGE_RANGE = 8;
	private static final double KEEP_AWAY_DISTANCE = 4;
	private static final double PATH_FIND_FRICTION = .8;
	private static final double ITEM_DROP_RATE = .9;
	
	private static final int COLOR = Color.rgb(0, 120, 150);
	private static final double MOVE_SPEED = .1, MAX_LIFE = 5, ATTACK_DAMAGE = 15;
	private static final int ATTACK_TIME = 100, STUN_DURATION = 30;
	private static final double RAISE_RANGE = 2, RAISE_DELAY = 100;
	
	RaiseEnemy(double spawnX, double spawnY, Map map) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, COLOR, MOVE_SPEED, ATTACK_TIME, MAX_LIFE, WANDER_THRESHOLD, WANDER_DISTANCE, ACTIVE_DISTANCE, DAMAGE_RANGE, KEEP_AWAY_DISTANCE, PATH_FIND_FRICTION, ITEM_DROP_RATE, map);
	}
	
	void doAttack(Map map, Player player, LList<Projectile> projectile, LList<Particle> particle) {
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE * 2)
			projectile.addHead(new DelayedProjectile(MapEntity.ENTITY_LAYER_PARTICLE, player.x, player.y, RAISE_RANGE, ATTACK_DAMAGE, Color.YELLOW, RAISE_DELAY, map));
	}
}
package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

class StunEnemy extends Enemy {
	private static final double WANDER_THRESHOLD = .98;
	private static final double WANDER_DISTANCE = 5;
	private static final double ACTIVE_DISTANCE = 10;
	private static final double DAMAGE_RANGE = 2;
	private static final double KEEP_AWAY_DISTANCE = 0;
	private static final double PATH_FIND_FRICTION = .8;
	private static final double ITEM_DROP_RATE = .9;
	
	private static final int COLOR = Color.rgb(100, 100, 0);
	private static final double MOVE_SPEED = .05, MAX_LIFE = 10, ATTACK_DAMAGE = 15;
	private static final int ATTACK_TIME = 100, STUN_DURATION = 30;
	
	StunEnemy(double spawnX, double spawnY, Map map) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, COLOR, MOVE_SPEED, ATTACK_TIME, MAX_LIFE, WANDER_THRESHOLD, WANDER_DISTANCE, ACTIVE_DISTANCE, DAMAGE_RANGE, KEEP_AWAY_DISTANCE, PATH_FIND_FRICTION, ITEM_DROP_RATE, map);
	}
	
	void doAttack(Player player, LList<Projectile> projectile, LList<Particle> particle, Map map) {
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE * 2) {
			player.takeDamage(ATTACK_DAMAGE);
			player.setStun(STUN_DURATION);
		}
		particle.addHead(new Particle(x, y, DAMAGE_RANGE * 2, 0, 0, Color.GREEN, STUN_DURATION, map));
	}
}

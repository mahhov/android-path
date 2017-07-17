package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

class ProjectileEnemy extends Enemy {
	private static final double WANDER_THRESHOLD = .98;
	private static final double WANDER_DISTANCE = 5;
	private static final double ACTIVE_DISTANCE = 10;
	private static final double DAMAGE_RANGE = 8;
	private static final double KEEP_AWAY_DISTANCE = 4;
	private static final double PATH_FIND_FRICTION = .8;
	private static final double ITEM_DROP_RATE = .75;
	
	private static final int COLOR = Color.rgb(150, 0, 150);
	private static final double MOVE_SPEED = .1, MAX_LIFE = 5, ATTACK_DAMAGE = 5;
	private static final int ATTACK_TIME = 50;
	
	ProjectileEnemy(double spawnX, double spawnY, Map map) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, COLOR, MOVE_SPEED, ATTACK_TIME, MAX_LIFE, WANDER_THRESHOLD, WANDER_DISTANCE, ACTIVE_DISTANCE, DAMAGE_RANGE, KEEP_AWAY_DISTANCE, PATH_FIND_FRICTION, ITEM_DROP_RATE, map);
	}
	
	void doAttack(Player player, LList<Projectile> projectile) {
		double[] toPlayer = Math3D.setMagnitude(player.x - x, player.y - y, 1);
		projectile.addHead(new Projectile(MapEntity.ENTITY_LAYER_HOSTILE_PROJECTILE, x, y, toPlayer[0], toPlayer[1], .1, ATTACK_DAMAGE, Color.RED));
	}
}

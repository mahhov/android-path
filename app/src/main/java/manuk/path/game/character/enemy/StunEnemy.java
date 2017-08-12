package manuk.path.game.character.enemy;

import android.graphics.Color;
import manuk.path.game.character.Player;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
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
	private static final double DEATH_EXP = 10;
	
	private static final int COLOR = Color.rgb(50, 150, 50);
	private static final double MOVE_SPEED = .05, MAX_LIFE = 10, ATTACK_DAMAGE = 15;
	private static final int ATTACK_TIME = 100, STUN_DURATION = 30;
	
	StunEnemy(double spawnX, double spawnY, Map map) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, COLOR, MOVE_SPEED, ATTACK_TIME, MAX_LIFE, WANDER_THRESHOLD, WANDER_DISTANCE, ACTIVE_DISTANCE, DAMAGE_RANGE, KEEP_AWAY_DISTANCE, PATH_FIND_FRICTION, ITEM_DROP_RATE, DEATH_EXP, map);
	}
	
	void doAttack(Map map, Player player, LList<Projectile> projectile, LList<Particle> particle) {
		double distance = Math3D.magnitude(player.x - x, player.y - y);
		if (distance < DAMAGE_RANGE * 2) {
			player.takeDamage(ATTACK_DAMAGE);
			player.setStun(STUN_DURATION);
		}
		particle.addHead(new Particle(x, y, DAMAGE_RANGE * 2, 0, 0, Color.GREEN, STUN_DURATION, map));
	}
	
	public void draw(double scrollX, double scrollY) {
		super.draw(scrollX, scrollY);
		if (attackTime.active()) {
			boolean[] side = new boolean[] {true, true, true, true, true, true};
			int[] color = MapPainter.createColorShade(Color.rgb(0, (int) (255. * attackTime.value / attackTime.maxValue), 0));
			double size = DAMAGE_RANGE * 2;
			MapPainter.drawBlock(x - scrollX - size, y - scrollY - size, 0, size * 2, size * 2, .5, side, color, true);
		}
	}
}

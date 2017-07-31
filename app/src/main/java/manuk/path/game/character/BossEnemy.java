package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.DelayedProjectile;
import manuk.path.game.projectile.DurationProjectile;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

class BossEnemy extends Enemy {
	private static final double WANDER_THRESHOLD = .98;
	private static final double WANDER_DISTANCE = 5;
	private static final double ACTIVE_DISTANCE = 10;
	private static final double DAMAGE_RANGE = 8;
	private static final double KEEP_AWAY_DISTANCE = 0;
	private static final double PATH_FIND_FRICTION = .8;
	private static final double ITEM_DROP_RATE = 1;
	private static final double DEATH_EXP = 100;
	
	private static final int COLOR = Color.rgb(0, 0, 0);
	private static final double MOVE_SPEED = .05, MAX_LIFE = 30, ATTACK_DAMAGE = 5;
	private static final int ATTACK_TIME = 0;
	
	private static final double WEIGHT_RAY = .2, WEIGHT_QUADRANT = .2, WEIGHT_ROCKFALL = .2;
	private static final int STATE_NONE = 0, STATE_RAY = 1, STATE_QUADRANT = 2, STATE_ROCKFALL = 3;
	private static final int DURATION[] = new int[] {100, 100, 100, 100};
	private static final int INTERVAL[] = new int[] {100, 20, 25, 10};
	
	private int state;
	private Counter stateDuration;
	private Counter stateInterval;
	
	//	private final static double QUADRANT_SIZE = 1, 
	private int quadrantCurrent;
	
	BossEnemy(double spawnX, double spawnY, Map map) {
		super(MapEntity.ENTITY_LAYER_HOSTILE_CHARACTER, spawnX, spawnY, COLOR, MOVE_SPEED, ATTACK_TIME, MAX_LIFE, WANDER_THRESHOLD, WANDER_DISTANCE, ACTIVE_DISTANCE, DAMAGE_RANGE, KEEP_AWAY_DISTANCE, PATH_FIND_FRICTION, ITEM_DROP_RATE, DEATH_EXP, map);
		stateDuration = new Counter(0);
		stateDuration.begin(0);
		stateInterval = new Counter(0);
	}
	
	void doAttack(Map map, Player player, LList<Projectile> projectile, LList<Particle> particle) {
		if (stateDuration.update())
			findNewState();
		
		if (!stateInterval.update())
			return;
		
		switch (state) {
			case STATE_RAY:
				//				rayAttack();
				//				break;
			case STATE_QUADRANT:
				quadrantAttack(map, projectile);
				break;
			case STATE_ROCKFALL:
				rockfallAttack(map, projectile);
				break;
		}
		
		stateInterval.begin(INTERVAL[state]);
	}
	
	private void findNewState() {
		double newStateRand = Math3D.random();
		if ((newStateRand -= WEIGHT_RAY) < 0)
			state = STATE_RAY;
		else if ((newStateRand -= WEIGHT_QUADRANT) < 0)
			state = STATE_QUADRANT;
		else if ((newStateRand -= WEIGHT_ROCKFALL) < 0)
			state = STATE_ROCKFALL;
		
		stateDuration.begin(DURATION[state]);
		stateInterval.begin(0);
	}
	
	private void rayAttack() {
		
	}
	
	private void quadrantAttack(Map map, LList<Projectile> projectile) {
		int[][] QUADRANT_LOCATION = new int[][] {{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
		double QUADRANT_SIZE = 5;
		int[] quadrantLocation = QUADRANT_LOCATION[quadrantCurrent];
		projectile.addHead(new DurationProjectile(MapEntity.ENTITY_LAYER_PARTICLE, x + quadrantLocation[0] * QUADRANT_SIZE, y + quadrantLocation[1] * QUADRANT_SIZE, QUADRANT_SIZE, .5, Color.YELLOW, INTERVAL[STATE_QUADRANT], map));
		
		quadrantCurrent++;
		if (quadrantCurrent == 4)
			quadrantCurrent = 0;
	}
	
	private void rockfallAttack(Map map, LList<Projectile> projectile) {
		double dx = Math3D.random(-6, 6); // make constant
		double dy = Math3D.random(-6, 6);
		projectile.addHead(new DelayedProjectile(MapEntity.ENTITY_LAYER_PARTICLE, x + dx, y + dy, .6, ATTACK_DAMAGE, Color.YELLOW, 30, map)); // make constants for .6 and 30
	}
}
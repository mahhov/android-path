package manuk.path.game.particle;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.util.Math3D;

public class ShrineParticle extends Particle {
	private static final double SIZE = .1, VELOCITY = .04;
	private static final int COLOR = Color.YELLOW, DURATION = 25;
	
	public ShrineParticle(double x, double y, Map map) {
		super(x, y, SIZE, Math3D.random(-VELOCITY, VELOCITY), Math3D.random(-VELOCITY, VELOCITY), COLOR, DURATION, map);
	}
}
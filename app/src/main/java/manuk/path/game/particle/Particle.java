package manuk.path.game.particle;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;

public class Particle extends MapEntity {
	private static final double FRICTION = 1;
	private double x, y;
	private double vx, vy;
	private double size;
	private int[] color;
	private int duration;
	
	public Particle(double x, double y, double size, double vx, double vy, int color, int duration, Map map) {
		super(MapEntity.ENTITY_LAYER_PARTICLE, 0);
		this.x = x;
		this.y = y;
		this.size = size;
		this.vx = vx;
		this.vy = vy;
		this.color = MapPainter.createColorShade(color);
		this.duration = duration;
		map.moveEntity(new double[] {x, y}, this);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		if (duration-- == 0) {
			map.removeEntity(this);
			return true;
		}
		
		vx *= FRICTION;
		vy *= FRICTION;
		x += vx;
		y += vy;
		//		double[] xy = Math3D.bound(x, y, z, world.width, world.length, world.height);
		//		x = xyz[0];
		//		y = xyz[1];
		
		map.moveEntity(new double[] {x, y}, this);
		
		return false;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - size, y - scrollY - size, 0, size * 2, size * 2, .5, side, color);
	}
}
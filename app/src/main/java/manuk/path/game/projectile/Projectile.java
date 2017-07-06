package manuk.path.game.projectile;

import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;

public class Projectile extends MapEntity {
	private double x, y, dir[], speed;
	private int[] color;
	
	public Projectile(int layer, double x, double y, double dirX, double dirY, double speed, int color) {
		super(layer);
		this.x = x;
		this.y = y;
		dir = new double[] {dirX, dirY};
		this.speed = speed;
		this.color = MapPainter.createColorShade(color);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		double[] intersection = map.intersectionFinder.find(new double[] {x, y}, dir, speed, false);
		x = intersection[0];
		y = intersection[1];
		map.addEntity((int) x, (int) y, this);
		return intersection[2] == 1;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .25, y - scrollY - .25, 0, .5, .5, .5, side, color);
	}
}

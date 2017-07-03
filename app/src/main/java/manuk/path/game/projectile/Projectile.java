package manuk.path.game.projectile;

import android.graphics.Color;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;

public class Projectile {
	private double x, y, dir[], speed;
	private int color;
	
	public Projectile(double x, double y, double dirX, double dirY, double speed, int color) {
		this.x = x;
		this.y = y;
		dir = new double[] {dirX, dirY};
		this.speed = speed;
		this.color = color;
	}
	
	// return true if need to be removed
	public boolean update(IntersectionFinder intersectionFinder) {
		double[] intersection = intersectionFinder.find(new double[] {x, y}, dir, speed, false);
		x = intersection[0];
		y = intersection[1];
		return intersection[2] == 1;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, side, Color.RED);
	}
}

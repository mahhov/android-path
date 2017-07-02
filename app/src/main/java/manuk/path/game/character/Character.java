package manuk.path.game.character;

import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.Math3D;

abstract class Character {
	double speed;
	public double x, y;
	double goalX, goalY;
	
	public Character(double startX, double startY) {
		goalX = x = startX;
		goalY = y = startY;
	}
	
	void move(IntersectionFinder intersectionFinder) {
		double deltaX = goalX - x, deltaY = goalY - y;
		double dist = Math3D.min(speed, Math3D.magnitude(deltaX, deltaY));
		double[] intersection = intersectionFinder.find(new double[] {x, y}, new double[] {deltaX, deltaY}, dist, true);
		x = intersection[0];
		y = intersection[1];
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, side);
	}
}

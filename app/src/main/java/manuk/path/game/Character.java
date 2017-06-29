package manuk.path.game;

import android.graphics.Color;

class Character {
	private double speed = .5;
	double x, y;
	private double goalX, goalY;
	
	Character() {
		goalX = x = 5;
		goalY = y = 5;
	}
	
	void setMoveGoal(double toX, double toY) {
		goalX = toX;
		goalY = toY;
	}
	
	void move() {
		double[] movement = Util.setMagnitudeMax(goalX - x, goalY - y, speed);
		x += movement[0];
		y += movement[1];
	}
	
	void draw(Painter painter, double scrollX, double scrollY) {
		MapPainter.drawBlock(painter, x - scrollX, y - scrollY, 0, true, true, true, Color.BLUE);
	}
}

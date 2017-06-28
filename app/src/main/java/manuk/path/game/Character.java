package manuk.path.game;

import android.graphics.Color;

class Character {
	private double speed = .1;
	double x, y;
	
	Character() {
		x = 5;
		y = 5;
	}
	
	void move(double toX, double toY) {
		double[] movement = Util.setMagnitudeMax(toX - x, toY - y, speed);
		x += movement[0];
		y += movement[1];
	}
	
	void draw(Painter painter) {
		World.drawBlock(painter, x, y, Color.BLUE);
	}
}

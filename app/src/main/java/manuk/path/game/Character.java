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
	
	void move(Controller controller, Map map) {
		if (controller.isDown()) {
			goalX = controller.touchX * Engine.VIEW_WIDTH + map.scrollX;
			goalY = controller.touchY * Engine.VIEW_HEIGHT + map.scrollY;
		}
		double[] movement = Util.setMagnitudeMax(goalX - x, goalY - y, speed);
		x += movement[0];
		y += movement[1];
	}
	
	void draw(Painter painter, double scrollX, double scrollY) {
		MapPainter.drawBlock(painter, x - scrollX - .5, y - scrollY - .5, 0, true, true, true, Color.BLUE);
	}
}

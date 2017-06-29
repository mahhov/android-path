package manuk.path.game;

import android.graphics.Color;

class Character {
	private double speed = .5;
	double x, y;
	private double goalX, goalY;
	
	Character() {
		goalX = x = MapGenerator.startX;
		goalY = y = MapGenerator.startY;
	}
	
	void move(Controller controller, Map map) {
		if (controller.isDown()) {
			goalX = controller.touchX * Engine.VIEW_WIDTH + map.scrollX;
			goalY = controller.touchY * Engine.VIEW_HEIGHT + map.scrollY;
		}
		double[] movement = Util.setMagnitudeMax(goalX - x, goalY - y, speed);
		if (map.isMoveable((int) (x + movement[0]), (int) (y + movement[1]), 0)) {
			x += movement[0];
			y += movement[1];
		}
	}
	
	void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .5, y - scrollY - .5, 0, side, Color.BLUE);
	}
}

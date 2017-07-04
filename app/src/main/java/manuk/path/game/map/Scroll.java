package manuk.path.game.map;

import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

class Scroll {
	private int width, length;
	int startX, startY, endX, endY, midX, midY;
	
	Scroll(int width, int length) {
		this.width = width;
		this.length = length;
	}
	
	void setScroll(double scrollX, double scrollY) {
		startX = (int) scrollX;
		startY = (int) scrollY;
		endX = Math3D.min(startX + Measurements.SCALED_VIEW_WIDTH + 1, width);
		endY = Math3D.min(startY + Measurements.SCALED_VIEW_HEIGHT + 1, length);
		midX = startX + Measurements.SCALED_VIEW_WIDTH / 2;
		midY = startY + Measurements.SCALED_VIEW_HEIGHT / 2;
	}
	
	boolean inView(int x, int y) {
		return x >= startX && x < endX && y >= startY && y < endY;
	}
}
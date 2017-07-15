package manuk.path.game.painter.element;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.Painter;

public class PaintElement {
	static final int FRAME_COLOR = Color.GRAY, FRAME_SIZE = 5;
	
	double left, top, width, height;
	int color;
	
	public PaintElement(double left, double top, double width, double height, int color) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public Controller.Touch handleInput(Controller controller) {
		return null;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, color, FRAME_COLOR);
	}
}

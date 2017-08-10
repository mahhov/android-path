package manuk.path.game.userinterface.element;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.Painter;

public class PaintElement {
	static final int FRAME_COLOR = Color.GRAY, FRAME_SIZE = 5;
	
	double left, top, width, height;
	int color;
	boolean hidden;
	
	public PaintElement(double left, double top, double width, double height, int color) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public Controller.Touch handleInput(Controller controller) {
		return null;
	}
	
	public void cleanInput(Controller controller) {
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, color, FRAME_COLOR);
	}
}
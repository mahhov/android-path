package manuk.path.game.userinterface.element;

import manuk.path.game.controller.Controller;
import manuk.path.game.painter.Painter;

public class Joystick extends ClickablePaintElement {
	public double touchX, touchY;
	
	public Joystick(double left, double top, double width, double height, int color, int pressedColor) {
		super(left, top, width, height, color, pressedColor);
	}
	
	public Controller.Touch handleInput(Controller controller) {
		Controller.Touch touch = super.handleInput(controller);
		if (isDown()) {
			touchX = (touch.x - left) / width;
			touchY = (touch.y - top) / height;
		}
		return touch;
	}
	
	public void draw(Painter painter) {
		painter.drawRectFrame(left, top, width, height, color);
		if (isDown())
			painter.drawRect(left + touchX * width - width / 10, top + touchY * height - height / 10, width / 5, height / 5, pressedColor);
	}
}

package manuk.path.game.userinterface.element;

import manuk.path.game.controller.Controller;
import manuk.path.game.controller.Controller.Touch;
import manuk.path.game.painter.Painter;

public class ClickablePaintElement extends PaintElement {
	private int touchId;
	int pressedColor;
	public boolean isPressed;
	
	public ClickablePaintElement(double left, double top, double width, double height, int color, int pressedColor) {
		super(left, top, width, height, color);
		this.pressedColor = pressedColor;
		touchId = -1;
	}
	
	public Touch handleInput(Controller controller) {
		Touch touch = controller.getTouch(touchId, left, top, left + width, top + height);
		if (touch == null) {
			isPressed = false;
			touchId = -1;
		} else {
			isPressed = true;
			touchId = touch.consume();
		}
		return touch;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, isPressed ? pressedColor : color, FRAME_COLOR);
	}
}

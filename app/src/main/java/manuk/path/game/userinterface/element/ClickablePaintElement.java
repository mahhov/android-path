package manuk.path.game.userinterface.element;

import manuk.path.game.controller.Controller;
import manuk.path.game.controller.Controller.Touch;
import manuk.path.game.painter.Painter;

public class ClickablePaintElement extends PaintElement {
	private int touchId;
	int pressedColor;
	public boolean isPressed;
	private String text;
	
	public ClickablePaintElement(double left, double top, double width, double height, int color, int pressedColor) {
		this(left, top, width, height, color, pressedColor, null);
	}
	
	public ClickablePaintElement(double left, double top, double width, double height, int color, int pressedColor, String text) {
		super(left, top, width, height, color);
		this.pressedColor = pressedColor;
		this.text = text;
		touchId = -1;
	}
	
	public void setText(String text) {
		this.text = text;
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
	
	public void cleanInput(Controller controller) {
		Touch touch = controller.getTouch(touchId, left, top, left + width, top + height);
		if (touch != null)
			touch.reset();
		touchId = -1;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, isPressed ? pressedColor : color, FRAME_COLOR);
		if (text != null)
			painter.drawTextCentered(text, left + width / 2, top + height / 2, .05f);
	}
}

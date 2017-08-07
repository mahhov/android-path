package manuk.path.game.userinterface.element;

import manuk.path.game.controller.Controller;
import manuk.path.game.controller.Controller.Touch;
import manuk.path.game.painter.Painter;

public class ClickablePaintElement extends PaintElement {
	private int touchId;
	int pressedColor;
	private boolean down, firstDown;
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
			down = false;
			touchId = -1;
		} else {
			down = true;
			int touchId = touch.consume();
			if (this.touchId != touchId) {
				this.touchId = touchId;
				firstDown = true;
			}
		}
		return touch;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public boolean isFirstDown() {
		boolean r = firstDown;
		firstDown = false;
		return r;
	}
	
	public void cleanInput(Controller controller) {
		Touch touch = controller.getTouch(touchId, left, top, left + width, top + height);
		if (touch != null)
			touch.reset();
		touchId = -1;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, down ? pressedColor : color, FRAME_COLOR);
		if (text != null)
			painter.drawTextCentered(text, left + width / 2, top + height / 2, .05f);
	}
}

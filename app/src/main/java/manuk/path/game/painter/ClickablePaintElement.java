package manuk.path.game.painter;


import manuk.path.game.controller.Controller;

public class ClickablePaintElement extends PaintElement {
	int pressedColor;
	public boolean isPressed;
	
	public ClickablePaintElement(double left, double top, double width, double height, int color, int pressedColor) {
		super(left, top, width, height, color);
		this.pressedColor = pressedColor;
	}
	
	public void handleInput(Controller controller) {
		isPressed = controller.isDown() && controller.touchX > left && controller.touchX < left + width && controller.touchY > top && controller.touchY < top + height;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, isPressed ? pressedColor : color);
		painter.drawRect(left, top, width, height, FRAME_COLOR, FRAME_SIZE);
	}
}

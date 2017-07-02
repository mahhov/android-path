package manuk.path.game.painter;


import manuk.path.game.controller.Controller;

public class PaintElement {
	private double left, top, width, height;
	private int color, pressedColor;
	public boolean isPressed;
	
	public PaintElement(double left, double top, double width, double height, int color, int pressedColor) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.color = color;
		this.pressedColor = pressedColor;
	}
	
	public void handleInput(Controller controller) {
		isPressed = controller.isDown() && controller.touchX > left && controller.touchX < left + width && controller.touchY > top && controller.touchY < top + height;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, isPressed ? pressedColor : color, false);
	}
}

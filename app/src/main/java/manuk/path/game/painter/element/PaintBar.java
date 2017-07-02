package manuk.path.game.painter.element;

import manuk.path.game.painter.Painter;

public class PaintBar extends PaintElement {
	private double value;
	
	public PaintBar(double left, double top, double width, double height, int color) {
		super(left, top, width, height, color);
		value = 1;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, FRAME_COLOR);
		painter.drawRect(left, top, width * value, height, color);
		painter.drawRect(left, top, width, height, FRAME_COLOR, FRAME_SIZE);
	}
}

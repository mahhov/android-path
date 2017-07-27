package manuk.path.game.userinterface.element;

import manuk.path.game.painter.Painter;

public class PaintBar extends PaintElement {
	double value, maxValue;
	
	public PaintBar(double left, double top, double width, double height, int color) {
		super(left, top, width, height, color);
		maxValue = value = 1;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	
	public void draw(Painter painter) {
		painter.drawRect(left, top, width, height, FRAME_COLOR, FRAME_COLOR);
		painter.drawRect(left, top, width * value / maxValue, height, color);
	}
}

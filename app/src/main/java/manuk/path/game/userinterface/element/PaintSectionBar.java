package manuk.path.game.userinterface.element;

import manuk.path.game.painter.Painter;

public class PaintSectionBar extends PaintBar {
	private static final double SECTION = 25;
	
	public PaintSectionBar(double left, double top, double width, double height, int color) {
		super(left, top, width, height, color);
	}
	
	public void draw(Painter painter) {
		super.draw(painter);
		for (double i = 0; i < value / maxValue; i += SECTION / maxValue)
			painter.drawRectFrame(left, top, width * i, height, FRAME_COLOR);
	}
}

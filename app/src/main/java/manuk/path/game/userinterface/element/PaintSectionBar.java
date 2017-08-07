package manuk.path.game.userinterface.element;

import manuk.path.game.painter.Painter;
import manuk.path.game.util.Math3D;

public class PaintSectionBar extends PaintBar {
	private static final double SECTION = 25;
	
	public PaintSectionBar(double left, double top, double width, double height, int color) {
		super(left, top, width, height, color);
	}
	
	public void draw(Painter painter) {
		super.draw(painter);
		int numSections = (int) ((value - Math3D.EPSILON) / SECTION);
		double sectionWidth = width / (int) (maxValue / SECTION);
		for (int i = 0; i <= numSections; i++)
			painter.drawRectFrame(left, top, i * sectionWidth, height, FRAME_COLOR);
	}
}

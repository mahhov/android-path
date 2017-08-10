package manuk.path.game.userinterface.element;

import manuk.path.game.painter.Painter;
import manuk.path.game.util.Math3D;

public class PaintSectionBar extends PaintBar {
	private static final double SECTION = 25;
	private final double section;
	
	public PaintSectionBar(double left, double top, double width, double height, int color) {
		this(left, top, width, height, color, SECTION);
	}
	
	public PaintSectionBar(double left, double top, double width, double height, int color, double section) {
		super(left, top, width, height, color);
		this.section = section;
	}
	
	public void draw(Painter painter) {
		super.draw(painter);
		int numSections = (int) ((value - Math3D.EPSILON) / section);
		double sectionWidth = width / (int) (maxValue / section);
		for (int i = 0; i <= numSections; i++)
			painter.drawRectFrame(left, top, i * sectionWidth, height, FRAME_COLOR);
	}
}

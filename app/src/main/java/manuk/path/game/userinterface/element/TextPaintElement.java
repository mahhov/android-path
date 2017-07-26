package manuk.path.game.userinterface.element;

import manuk.path.game.painter.Painter;

public class TextPaintElement extends PaintElement {
	private String text;
	private float size;
	
	public TextPaintElement(double x, double y, String text, float size) {
		super(x, y, 0, 0, 0);
		this.text = text;
		this.size = size;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void draw(Painter painter) {
		painter.drawTextCentered(text, left, top, size);
	}
}

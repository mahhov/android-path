package manuk.path.game.userinterface.element;

import manuk.path.game.painter.Painter;

public class Text extends PaintElement {
	private String text;
	private float size;
	
	public Text(double x, double y, String text, float size) {
		super(x, y, 0, 0, 0);
		this.text = text;
		this.size = size;
	}
	
	public void draw(Painter painter) {
		painter.drawTextCentered(text, left, top, size);
	}
}

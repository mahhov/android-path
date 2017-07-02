package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.PaintElement;
import manuk.path.game.painter.Painter;
import manuk.path.game.util.LList;

class UserInterface {
	private LList<PaintElement> elements;
	
	UserInterface() {
		elements = new LList<>();
		elements.addHead(new PaintElement(.1, .1, .3, .1, Color.GREEN, Color.BLUE));
	}
	
	void handleInput(Controller controller) {
		for (PaintElement elem : elements)
			elem.handleInput(controller);
	}
	
	void draw(Painter painter) {
		for (PaintElement elem : elements)
			elem.draw(painter);
	}
}

package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.ClickablePaintElement;
import manuk.path.game.painter.PaintElement;
import manuk.path.game.painter.Painter;
import manuk.path.game.util.LList;

class UserInterface {
	private LList<PaintElement> elements;
	
	UserInterface() {
		elements = new LList<>();
		elements.addHead(new PaintElement(.05, .02, .43, .02, Color.RED));
		elements.addHead(new PaintElement(.52, .02, .43, .02, Color.BLUE));
		elements.addHead(new ClickablePaintElement(.47, .85, .06, .1, Color.WHITE, Color.GRAY));
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

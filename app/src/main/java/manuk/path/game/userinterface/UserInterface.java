package manuk.path.game.userinterface;

import manuk.path.game.controller.Controller;
import manuk.path.game.painter.Painter;
import manuk.path.game.userinterface.element.PaintElement;
import manuk.path.game.util.LList;

abstract class UserInterface {
	LList<PaintElement> elements;
	
	UserInterface() {
		elements = new LList<>();
	}
	
	public void handleInput(Controller controller) {
		for (PaintElement elem : elements)
			elem.handleInput(controller);
	}
	
	public void cleanInput(Controller controller) {
		for (PaintElement elem : elements)
			elem.cleanInput(controller);
	}
	
	public void draw(Painter painter) {
		for (PaintElement elem : elements)
			if (!elem.isHidden())
				elem.draw(painter);
	}
}

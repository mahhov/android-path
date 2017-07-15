package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.Painter;
import manuk.path.game.painter.element.ClickablePaintElement;
import manuk.path.game.painter.element.Joystick;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.painter.element.PaintElement;
import manuk.path.game.util.LList;

class UserInterface {
	private LList<PaintElement> elements;
	Joystick joystick;
	PaintBar lifeBar, manaBar, expBar;
	ClickablePaintElement actionButton;
	
	UserInterface() {
		elements = new LList<>();
		elements.addHead(joystick = new Joystick(1. / 12, 9. / 14, 1. / 6, 2. / 7, Color.GREEN, Color.RED));
		elements.addHead(lifeBar = new PaintBar(.05, .01, .43, .02, Color.RED));
		elements.addHead(manaBar = new PaintBar(.52, .01, .43, .02, Color.BLUE));
		elements.addHead(expBar = new PaintBar(.05, .04, .90, .01, Color.GREEN));
		elements.addHead(actionButton = new ClickablePaintElement(.47, .85, .06, .1, Color.WHITE, Color.GRAY));
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

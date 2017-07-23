package manuk.path.game.userinterface;

import android.graphics.Color;
import manuk.path.game.userinterface.element.ClickablePaintElement;
import manuk.path.game.userinterface.element.Text;

public class PauseUserInterface extends UserInterface {
	public ClickablePaintElement resumeButton;
	
	public PauseUserInterface() {
		elements.addHead(new Text(.5, .5, "Paused", .2f));
		elements.addHead(resumeButton = new ClickablePaintElement(.44, .8, .12, .2, Color.WHITE, Color.GRAY));
	}
}

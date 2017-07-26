package manuk.path.game.userinterface;

import android.graphics.Color;
import manuk.path.game.userinterface.element.ClickablePaintElement;
import manuk.path.game.userinterface.element.TextPaintElement;

public class CharacterUserInterface extends UserInterface {
	public TextPaintElement skillPointsText;
	public ClickablePaintElement resumeButton, lifeSkillButton, staminaSkillButton;
	
	public CharacterUserInterface() {
		double w = .12;
		elements.addHead(skillPointsText = new TextPaintElement(.5, .25, "", .05f));
		elements.addHead(resumeButton = new ClickablePaintElement(.5 - w / 2, .8, w, w * 5 / 3, Color.WHITE, Color.GRAY));
		elements.addHead(lifeSkillButton = new ClickablePaintElement(.25 - w / 2, .5, w, w * 5 / 3, Color.WHITE, Color.GRAY));
		elements.addHead(staminaSkillButton = new ClickablePaintElement(.75 - w / 2, .5, w, w * 5 / 3, Color.WHITE, Color.GRAY));
	}
}

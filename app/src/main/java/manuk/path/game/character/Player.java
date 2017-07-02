package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.Map;
import manuk.path.game.controller.Controller;
import manuk.path.game.mapgenerator.MapGenerator;
import manuk.path.game.painter.element.ClickablePaintElement;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private PaintBar lifeBar;
	private ClickablePaintElement actionButton;
	
	public Player(MapGenerator mapGenerator, PaintBar lifeBar, ClickablePaintElement actionButton) {
		super(mapGenerator.startX, mapGenerator.startY, .5, 10, 100);
		this.lifeBar = lifeBar;
		this.actionButton = actionButton;
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		if (updateAttack()) {
			projectile.addHead(new Projectile(x, y, 1, 0, .1, Color.RED));
			goalX = x;
			goalY = y;
		}
		lifeBar.setValue(getLifePercent());
		if (actionButton.isPressed)
			attack();
		for (Controller.Touch touch : controller.touch)
			if (touch.isFresh()) {
				goalX = touch.x * Measurements.SCALED_VIEW_WIDTH + map.scrollX;
				goalY = touch.y * Measurements.SCALED_VIEW_HEIGHT + map.scrollY;
				break;
			}
		move(map.intersectionFinder);
	}
}
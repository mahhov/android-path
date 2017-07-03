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
	private double attackX, attackY;
	private PaintBar lifeBar;
	private ClickablePaintElement actionButton;
	
	public Player(MapGenerator mapGenerator, PaintBar lifeBar, ClickablePaintElement actionButton) {
		super(mapGenerator.spawnX, mapGenerator.spawnY, .5, 10, 100);
		this.lifeBar = lifeBar;
		this.actionButton = actionButton;
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		double[] touchXY = getTouchXY(controller, map);
		lifeBar.setValue(getLifePercent());
		
		if (updateAttack()) {
			projectile.addHead(new Projectile(x, y, attackX - x, attackY - y, .1, Color.RED));
			goalX = x;
			goalY = y;
		} else if (actionButton.isPressed && touchXY != null) {
			if (attack()) {
				attackX = touchXY[0];
				attackY = touchXY[1];
			}
		} else if (touchXY != null) {
			goalX = touchXY[0];
			goalY = touchXY[1];
		}
		move(map.intersectionFinder);
	}
	
	private double[] getTouchXY(Controller controller, Map map) {
		for (Controller.Touch touch : controller.touch)
			if (touch.isFresh()) {
				double x = touch.x * Measurements.SCALED_VIEW_WIDTH + map.scrollX;
				double y = touch.y * Measurements.SCALED_VIEW_HEIGHT + map.scrollY;
				return new double[] {x, y};
			}
		return null;
	}
}
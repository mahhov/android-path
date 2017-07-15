package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.painter.element.ClickablePaintElement;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private double attackX, attackY;
	private PaintBar lifeBar, expBar;
	private ClickablePaintElement actionButton;
	private double exp;
	
	public Player(MapGenerator mapGenerator, PaintBar lifeBar, PaintBar expBar, ClickablePaintElement actionButton) {
		super(MapEntity.ENTITY_LAYER_FRIENDLY_CHARACTER, mapGenerator.spawn.x, mapGenerator.spawn.y, Color.BLUE, .2, 10, 100);
		this.lifeBar = lifeBar;
		this.expBar = expBar;
		this.actionButton = actionButton;
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		double[] touchXY = getTouchXY(controller, map);
		lifeBar.setValue(getLifePercent());
		expBar.setValue(exp / 100);
		
		if (updateAttack()) {
			projectile.addHead(new Projectile(MapEntity.ENTITY_LAYER_FRIENDLY_PROJECTILE, x, y, attackX - x, attackY - y, .1, Color.RED));
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
		moveToGoal(map);
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
	
	void gainExp(double amount) {
		exp = Math3D.min(exp + amount, 100);
	}
}
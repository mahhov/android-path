package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.painter.element.ClickablePaintElement;
import manuk.path.game.painter.element.Joystick;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private int touchId;
	private double[] attackDir;
	private Joystick joystick;
	private PaintBar lifeBar, staminaBar, expBar;
	private ClickablePaintElement actionButton;
	private double exp;
	private double dashSpeed = 10;
	
	public Player(MapGenerator mapGenerator, Joystick joystick, PaintBar lifeBar, PaintBar staminaBar, PaintBar expBar, ClickablePaintElement actionButton) {
		super(MapEntity.ENTITY_LAYER_FRIENDLY_CHARACTER, mapGenerator.spawn.x, mapGenerator.spawn.y, Color.BLUE, .2, 10, 100, 100, 1, 30);
		this.joystick = joystick;
		this.lifeBar = lifeBar;
		this.staminaBar = staminaBar;
		this.expBar = expBar;
		this.actionButton = actionButton;
		touchId = -1;
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		double[] touchXY = getTouchXY(controller, map);
		
		lifeBar.setValue(getLifePercent());
		staminaBar.setValue(getStaminaPercent());
		expBar.setValue(exp / 100);
		
		if (updateAttack()) {
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			moveByDir(map, dashSpeed,MapEntity.ENTITY_LAYER_TRANSPARENT);
		} else if (actionButton.isPressed && (touchXY != null || joystick.isPressed)) {
			if (beginAttack(30)) {
				if (touchXY != null)
					attackDir = Math3D.setMagnitude(touchXY[0] - x, touchXY[1] - y, dashSpeed);
				else
					attackDir = Math3D.setMagnitude(joystick.touchX - .5, joystick.touchY - .5, dashSpeed);
			}
		} else if (touchXY != null) {
			goalX = touchXY[0];
			goalY = touchXY[1];
			moveToGoal(map);
		} else if (joystick.isPressed) {
			moveDeltaX = (joystick.touchX - .5) * moveSpeed;
			moveDeltaY = (joystick.touchY - .5) * moveSpeed;
			moveByDir(map);
		}
		staminaRegen();
	}
	
	private double[] getTouchXY(Controller controller, Map map) {
		Controller.Touch touch = controller.getTouch(touchId);
		if (touch == null) {
			touchId = -1;
			return null;
		}
		touchId = touch.consume();
		double x = touch.x * Measurements.SCALED_VIEW_WIDTH + map.scrollX;
		double y = touch.y * Measurements.SCALED_VIEW_HEIGHT + map.scrollY;
		return new double[] {x, y};
	}
	
	void gainExp(double amount) {
		exp = Math3D.min(exp + amount, 100);
	}
}
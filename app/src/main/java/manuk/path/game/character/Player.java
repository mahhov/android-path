package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.UserInterface;
import manuk.path.game.controller.Controller;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.painter.element.ClickablePaintElement;
import manuk.path.game.painter.element.Joystick;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private int touchId;
	private double[] attackDir;
	private Joystick joystick;
	private PaintBar lifeBar, staminaBar, expBar;
	private ClickablePaintElement dashButton, sprintButton;
	private double exp;
	private double dashSpeed = 1;
	private Counter dashTime;
	private long dashId;
	
	public Player(MapGenerator mapGenerator, UserInterface userInterface) {
		super(MapEntity.ENTITY_LAYER_FRIENDLY_CHARACTER, mapGenerator.spawn.x, mapGenerator.spawn.y, Color.BLUE, .2, 10, 100, 100, 1, 30);
		joystick = userInterface.joystick;
		lifeBar = userInterface.lifeBar;
		staminaBar = userInterface.staminaBar;
		expBar = userInterface.expBar;
		dashButton = userInterface.dashButton;
		sprintButton = userInterface.sprintButton;
		touchId = -1;
		dashTime = new Counter(10);
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		double[] touchXY = getTouchXY(controller, map);
		
		lifeBar.setValue(getLifePercent());
		staminaBar.setValue(getStaminaPercent());
		expBar.setValue(exp / 100);
		
		if (dashTime.active()) {
			dashTime.update();
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			IntersectionFinder.Intersection intersection = moveByDir(map, dashSpeed, MapEntity.ENTITY_LAYER_TRANSPARENT_FRIENDLY_CHARACTER);
			for (MapEntity trackedEntity : intersection.trackedCollisions)
				trackedEntity.handleIntersection(dashId, 5);
		} else if (updateAttack()) {
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			dashTime.begin();
			dashId++;
		} else if (dashButton.isPressed && (touchXY != null || joystick.isPressed)) {
			if (beginAttack(30)) {
				if (touchXY != null)
					attackDir = Math3D.setMagnitude(touchXY[0] - x, touchXY[1] - y, dashSpeed);
				else
					attackDir = Math3D.setMagnitude(joystick.touchX - .5, joystick.touchY - .5, dashSpeed);
			}
		} else if (joystick.isPressed) {
			moveDeltaX = (joystick.touchX - .5) * moveSpeed;
			moveDeltaY = (joystick.touchY - .5) * moveSpeed;
			double moveSpeed = this.moveSpeed;
			if (sprintButton.isPressed && useStamina(.3)) {
				moveSpeed *= 2;
				double[] moveDeltaXY = Math3D.setMagnitude(moveDeltaX, moveDeltaY, 1);
				moveDeltaX = moveDeltaXY[0];
				moveDeltaY = moveDeltaXY[1];
			}
			moveByDir(map, moveSpeed, layer);
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
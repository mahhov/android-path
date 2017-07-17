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
	private Counter dashTime, stunTime;
	private long dashId;
	
	public Player(MapGenerator mapGenerator, UserInterface userInterface, Map map) {
		super(MapEntity.ENTITY_LAYER_FRIENDLY_CHARACTER, mapGenerator.spawn.x, mapGenerator.spawn.y, Color.BLUE, .2, 10, 100, 100, 1, 30, map);
		joystick = userInterface.joystick;
		lifeBar = userInterface.lifeBar;
		staminaBar = userInterface.staminaBar;
		expBar = userInterface.expBar;
		dashButton = userInterface.dashButton;
		sprintButton = userInterface.sprintButton;
		touchId = -1;
		dashTime = new Counter(10);
		stunTime = new Counter(0);
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		double[] touchXYDouble = getTouchXY(controller, map);
		
		lifeBar.setValue(getLifePercent());
		staminaRegen();
		staminaBar.setValue(getStaminaPercent());
		expBar.setValue(exp / 100);
		
		if (stunTime.active()) {
			stunTime.update();
			return;
		}
		
		if (dashTime.active()) {
			dashTime.update();
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			doIntersections(moveByDir(map, dashSpeed, MapEntity.ENTITY_LAYER_TRANSPARENT_FRIENDLY_CHARACTER), dashId, 5);
		} else if (updateAttack()) {
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			dashTime.begin();
			dashId = getIntersectorId();
		} else if ((touchXYDouble != null && touchXYDouble[2] == 1) || (dashButton.isPressed && (touchXYDouble != null || joystick.isPressed))) {
			if (beginAttack(30)) {
				if (touchXYDouble != null)
					attackDir = Math3D.setMagnitude(touchXYDouble[0] - x, touchXYDouble[1] - y, dashSpeed);
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
			doIntersections(moveByDir(map, moveSpeed, layer), getIntersectorId(), 0);
		} else if (touchXYDouble != null) {
			goalX = touchXYDouble[0];
			goalY = touchXYDouble[1];
			double moveSpeed = this.moveSpeed;
			if (sprintButton.isPressed && useStamina(.3))
				moveSpeed *= 2;
			doIntersections(moveToGoal(map, moveSpeed, layer), getIntersectorId(), 0);
		}
	}
	
	private double[] getTouchXY(Controller controller, Map map) {
		Controller.Touch touch = controller.getTouch(touchId);
		if (touch == null) {
			touchId = -1;
			return null;
		}
		int isDouble = touch.isFreshDouble() ? 1 : 0;
		double x = touch.x * Measurements.SCALED_VIEW_WIDTH + map.scrollX;
		double y = touch.y * Measurements.SCALED_VIEW_HEIGHT + map.scrollY;
		touchId = touch.consume();
		return new double[] {x, y, isDouble};
	}
	
	private void doIntersections(IntersectionFinder.Intersection intersection, long intersectionId, int damage) {
		if (intersection != null)
			for (MapEntity trackedEntity : intersection.trackedCollisions)
				trackedEntity.handleIntersection(intersectionId, damage);
	}
	
	void gainExp(double amount) {
		exp = Math3D.min(exp + amount, 100);
	}
	
	public void giveLife(int amount) {
		takeHeal(amount);
	}
	
	void takeDamage(double amount) {
		if (!dashTime.active())
			super.takeDamage(amount);
	}
	
	void setStun(int duration) {
		stunTime.begin(duration);
		dashTime.stop();
	}
}
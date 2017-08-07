package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.userinterface.CharacterUserInterface;
import manuk.path.game.userinterface.PlayUserInterface;
import manuk.path.game.userinterface.element.ClickablePaintElement;
import manuk.path.game.userinterface.element.Joystick;
import manuk.path.game.userinterface.element.PaintBar;
import manuk.path.game.userinterface.element.TextPaintElement;
import manuk.path.game.util.IntersectionFinder;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private static final int LEVEL_EXP = 100;
	private static final double DASH_SPEED = 1;
	private int touchId;
	private double[] attackDir;
	
	private Joystick joystick;
	private PaintBar lifeBar, staminaBar, expBar;
	private ClickablePaintElement dashButton, sprintButton, characterButton;
	public boolean sprintButtonPressed;
	
	private int level, unspentSkill, skilledLife, skilledStamina;
	private TextPaintElement skillPointsText;
	private ClickablePaintElement lifeSkillButton, staminaSkillButton;
	
	private double exp;
	private Counter dashTime, stunTime;
	private long dashId;
	
	public Player(MapGenerator mapGenerator, PlayUserInterface playUserInterface, CharacterUserInterface characterUserInterface, Map map) {
		super(MapEntity.ENTITY_LAYER_FRIENDLY_CHARACTER, mapGenerator.spawn.x, mapGenerator.spawn.y, Color.BLUE, .2, 10, 100, 100, 1, 30, map);
		setupInterface(playUserInterface, characterUserInterface);
		touchId = -1;
		dashTime = new Counter(10);
		stunTime = new Counter(0);
	}
	
	private void setupInterface(PlayUserInterface playUserInterface, CharacterUserInterface characterUserInterface) {
		joystick = playUserInterface.joystick;
		lifeBar = playUserInterface.lifeBar;
		lifeBar.setValue(life);
		lifeBar.setMaxValue(maxLife);
		staminaBar = playUserInterface.staminaBar;
		staminaBar.setValue(stamina);
		staminaBar.setMaxValue(maxStamina);
		expBar = playUserInterface.expBar;
		expBar.setValue(exp);
		expBar.setMaxValue(LEVEL_EXP);
		dashButton = playUserInterface.dashButton;
		sprintButton = playUserInterface.sprintButton;
		characterButton = playUserInterface.characterButton;
		characterButton.setText(level + 1 + "");
		
		skillPointsText = characterUserInterface.skillPointsText;
		lifeSkillButton = characterUserInterface.lifeSkillButton;
		staminaSkillButton = characterUserInterface.staminaSkillButton;
		updateCharacterSkillInterface();
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		sprintButtonPressed = sprintButton.isDown();
		double[] touchXYDouble = getTouchXY(controller, map);
		
		staminaRegen();
		lifeBar.setValue(life);
		staminaBar.setValue(stamina);
		expBar.setValue(exp);
		
		if (stunTime.active()) {
			stunTime.update();
			return;
		}
		
		if (dashTime.active()) {
			dashTime.update();
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			doIntersections(moveByDir(map, DASH_SPEED, MapEntity.ENTITY_LAYER_TRANSPARENT_FRIENDLY_CHARACTER), dashId, 5);
		} else if (updateAttack()) {
			moveDeltaX = attackDir[0];
			moveDeltaY = attackDir[1];
			dashTime.begin();
			dashId = getIntersectorId();
		} else if ((touchXYDouble != null && touchXYDouble[2] == 1) || (dashButton.isDown() && (touchXYDouble != null || joystick.isDown()))) {
			if (beginAttack(30)) {
				if (touchXYDouble != null)
					attackDir = Math3D.setMagnitude(touchXYDouble[0] - x, touchXYDouble[1] - y, DASH_SPEED);
				else
					attackDir = Math3D.setMagnitude(joystick.touchX - .5, joystick.touchY - .5, DASH_SPEED);
			}
		} else if (joystick.isDown()) {
			moveDeltaX = (joystick.touchX - .5) * moveSpeed;
			moveDeltaY = (joystick.touchY - .5) * moveSpeed;
			double moveSpeed = this.moveSpeed;
			if (sprintButtonPressed && useStamina(.3)) {
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
			if (sprintButtonPressed && useStamina(.3))
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
		level += (exp + amount) / LEVEL_EXP;
		exp = (exp + amount) % LEVEL_EXP;
		updateCharacterSkillInterface();
	}
	
	public void giveLife(double amount) {
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
	
	public void updateCharacterMode() {
		if (unspentSkill == 0)
			return;
		if (lifeSkillButton.isFirstDown()) {
			skilledLife++;
			maxLife += 25;
			lifeBar.setMaxValue(maxLife);
			updateCharacterSkillInterface();
		}
		if (staminaSkillButton.isFirstDown()) {
			skilledStamina++;
			maxStamina += 25;
			staminaBar.setMaxValue(maxStamina);
			updateCharacterSkillInterface();
		}
	}
	
	private void updateCharacterSkillInterface() {
		characterButton.setText(level + 1 + "");
		unspentSkill = level - skilledLife - skilledStamina;
		skillPointsText.setText("Skill Points Remaining: " + unspentSkill);
		lifeSkillButton.setText("life " + skilledLife);
		staminaSkillButton.setText("stamina " + skilledStamina);
	}
}
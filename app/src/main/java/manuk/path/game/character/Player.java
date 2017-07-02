package manuk.path.game.character;

import android.graphics.Color;
import manuk.path.game.Map;
import manuk.path.game.controller.Controller;
import manuk.path.game.mapgenerator.MapGenerator;
import manuk.path.game.painter.element.ClickablePaintElement;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private static final double MAX_LIFE = 100;
	private double life;
	private PaintBar lifeBar;
	private ClickablePaintElement actionButton;
	
	public Player(MapGenerator mapGenerator, PaintBar lifeBar, ClickablePaintElement actionButton) {
		super(mapGenerator.startX, mapGenerator.startY);
		speed = .5;
		life = MAX_LIFE;
		this.lifeBar = lifeBar;
		this.actionButton = actionButton;
	}
	
	public void update(Controller controller, Map map, LList<Projectile> projectile) {
		lifeBar.setValue(life / MAX_LIFE);
		if (actionButton.isPressed)
			projectile.addHead(new Projectile(x, y, 1, 0, .1, Color.RED));
		for (Controller.Touch touch : controller.touch)
			if (touch.isFresh()) {
				goalX = touch.x * Measurements.SCALED_VIEW_WIDTH + map.scrollX;
				goalY = touch.y * Measurements.SCALED_VIEW_HEIGHT + map.scrollY;
				break;
			}
		move(map.intersectionFinder);
	}
	
	void takeDamage(double amount) {
		life = Math3D.max(life - amount / 5, 0);
	}
}

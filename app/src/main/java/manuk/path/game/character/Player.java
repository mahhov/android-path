package manuk.path.game.character;

import manuk.path.game.Map;
import manuk.path.game.controller.Controller;
import manuk.path.game.mapgenerator.MapGenerator;
import manuk.path.game.painter.element.PaintBar;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	private static final double MAX_LIFE = 100;
	private PaintBar lifeBar;
	private double life;
	
	public Player(MapGenerator mapGenerator, PaintBar lifeBar) {
		super(mapGenerator.startX, mapGenerator.startY);
		speed = .5;
		life = MAX_LIFE;
		this.lifeBar = lifeBar;
	}
	
	public void update(Controller controller, Map map) {
		lifeBar.setValue(life / MAX_LIFE);
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

package manuk.path.game.character;

import manuk.path.game.Map;
import manuk.path.game.controller.Controller;
import manuk.path.game.mapgenerator.MapGenerator;
import manuk.path.game.util.Measurements;

public class Player extends Character {
	public Player(MapGenerator mapGenerator) {
		super(mapGenerator.startX, mapGenerator.startY);
		speed = .5;
	}
	
	public void update(Controller controller, Map map) {
		for (Controller.Touch touch : controller.touch)
			if (touch.isFresh()) {
				goalX = touch.x * Measurements.SCALED_VIEW_WIDTH + map.scrollX;
				goalY = touch.y * Measurements.SCALED_VIEW_HEIGHT + map.scrollY;
				break;
			}
		move(map.intersectionFinder);
	}
}

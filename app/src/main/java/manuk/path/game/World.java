package manuk.path.game;

import android.graphics.Color;

class World {
	boolean gameOver;
	private Map map;
	private Character character;
	
	World(int width, int length, int height) {
		character = new Character();
		map = new Map(width, length, height);
	}
	
	void update(Controller controller) {
		if (controller.isDown())
			character.setMoveGoal(controller.touchX * Engine.VIEW_WIDTH + map.scrollX, controller.touchY * Engine.VIEW_HEIGHT + map.scrollY);
		character.move();
		map.scroll(character.x, character.y);
	}
	
	void draw(Painter painter) {
		painter.drawRect(0, 0, 1, 1, Color.WHITE);
		map.draw(painter);
		character.draw(painter, map.scrollX, map.scrollY);
		
		if (gameOver) {
			painter.drawText("GAME OVER :(", .5, .5, Color.GREEN);
			painter.drawText("Tap to Restart", .5, .7, Color.GREEN);
		}
	}
}	
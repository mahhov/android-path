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
			character.move(controller.touchX * Engine.VIEW_WIDTH, controller.touchY * Engine.VIEW_HEIGHT);
		map.scroll(character.x, character.y);
	}
	
	void draw(Painter painter) {
		painter.drawRect(0, 0, 1, 1, Color.WHITE);
		map.draw(painter);
		character.draw(painter);
		
		if (gameOver) {
			painter.drawText("GAME OVER :(", .5, .5, Color.GREEN);
			painter.drawText("Tap to Restart", .5, .7, Color.GREEN);
		}
	}
	
	static void drawBlock(Painter painter, double x, double y, int color) {
		painter.drawRect(x / Engine.VIEW_WIDTH - Engine.BLOCK_HEIGHT / 2, y / Engine.VIEW_HEIGHT - Engine.BLOCK_WIDTH / 2, Engine.BLOCK_WIDTH, Engine.BLOCK_HEIGHT, color);
	}
}	
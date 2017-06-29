package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.mapgenerator.CavernMapGenerator;
import manuk.path.game.mapgenerator.MapGenerator;

class World {
	boolean gameOver;
	private Map map;
	private Character character;
	
	World(int width, int length, int height) {
		MapGenerator mapGenerator = new CavernMapGenerator();
		mapGenerator.generate(width, length, height);
		map = new Map(width, length, height, mapGenerator);
		character = new Character(mapGenerator);
	}
	
	void update(Controller controller) {
		character.move(controller, map);
		map.scroll(character.x, character.y);
	}
	
	void draw(Painter painter) {
		painter.drawRect(0, 0, 1, 1, Color.WHITE);
		map.draw();
		character.draw(map.scrollX, map.scrollY);
		
		if (gameOver) {
			painter.drawText("GAME OVER :(", .5, .5, Color.GREEN);
			painter.drawText("Tap to Restart", .5, .7, Color.GREEN);
		}
	}
}	
package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.controller.Controller;
import manuk.path.game.character.Player;
import manuk.path.game.mapgenerator.CavernMapGenerator;
import manuk.path.game.mapgenerator.MapGenerator;
import manuk.path.game.painter.Painter;

class World {
	boolean gameOver;
	private Map map;
	private Player player;
	
	World(int width, int length, int height) {
		MapGenerator mapGenerator = new CavernMapGenerator();
		mapGenerator.generate(width, length, height);
		map = new Map(width, length, height, mapGenerator);
		player = new Player(mapGenerator);
	}
	
	void update(Controller controller) {
		player.move(controller, map);
		map.scroll(player.x, player.y);
	}
	
	void draw(Painter painter) {
		painter.drawRect(0, 0, 1, 1, Color.WHITE);
		map.draw();
		player.draw(map.scrollX, map.scrollY);
		
		if (gameOver) {
			painter.drawText("GAME OVER :(", .5, .5, Color.GREEN);
			painter.drawText("Tap to Restart", .5, .7, Color.GREEN);
		}
	}
}	
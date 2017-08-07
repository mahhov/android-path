package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.character.Enemy;
import manuk.path.game.character.Player;
import manuk.path.game.controller.Controller;
import manuk.path.game.item.Item;
import manuk.path.game.item.ShrineItem;
import manuk.path.game.map.Map;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.map.mapgenerator.MapGenerator.Pos;
import manuk.path.game.map.mapgenerator.MapGenerator.Pos3;
import manuk.path.game.map.mapgenerator.RoomMapGenerator;
import manuk.path.game.painter.Painter;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.userinterface.UserInterfaceHandler;
import manuk.path.game.util.LList;

import java.util.Iterator;

class World {
	private final static int STATE_PLAY = 0, STATE_PAUSED = 1;
	private int state;
	private UserInterfaceHandler userInterfaceHandler;
	private Map map;
	private Player player;
	private LList<Enemy> enemy;
	private LList<Projectile> projectile;
	private LList<Item> item;
	private LList<Particle> particle;
	
	World(int width, int length, int height) {
		userInterfaceHandler = new UserInterfaceHandler();
		MapGenerator mapGenerator = new RoomMapGenerator();
		mapGenerator.generate(width, length, height);
		map = new Map(width, length, height, mapGenerator);
		player = new Player(mapGenerator, userInterfaceHandler.playUserInterface, userInterfaceHandler.characterUserInterface, map);
		enemy = new LList<>();
		projectile = new LList<>();
		item = new LList<>();
		particle = new LList<>();
		for (Pos3 enemyPos : mapGenerator.enemySpawn)
			enemy.addHead(Enemy.create(enemyPos.x, enemyPos.y, enemyPos.z, map));
		for (Pos shrinePos : mapGenerator.shrineSpawn)
			item.addHead(new ShrineItem(shrinePos.x, shrinePos.y, map));
	}
	
	void update(Controller controller) {
		switch (state) {
			case STATE_PLAY:
				updatePlay(controller);
				if (userInterfaceHandler.playUserInterface.characterButton.isFirstDown()) {
					userInterfaceHandler.playUserInterface.cleanInput(controller);
					state = STATE_PAUSED;
				}
				break;
			case STATE_PAUSED:
				updatePause();
				userInterfaceHandler.characterUserInterface.handleInput(controller);
				if (userInterfaceHandler.characterUserInterface.resumeButton.isFirstDown()) {
					userInterfaceHandler.characterUserInterface.cleanInput(controller);
					state = STATE_PLAY;
				}
				break;
		}
	}
	
	private void updatePlay(Controller controller) {
		userInterfaceHandler.playUserInterface.handleInput(controller);
		player.update(controller, map, projectile);
		map.scroll(player.x, player.y);
		
		Iterator<LList<Enemy>.Node> enemyIterator = enemy.nodeIterator();
		LList<Enemy>.Node enemyNode;
		while (enemyIterator.hasNext()) {
			enemyNode = enemyIterator.next();
			if (enemyNode.elem.update(map, player, projectile, item, particle))
				enemy.remove(enemyNode);
		}
		
		Iterator<LList<Projectile>.Node> projectileIterator = projectile.nodeIterator();
		LList<Projectile>.Node projectileNode;
		while (projectileIterator.hasNext()) {
			projectileNode = projectileIterator.next();
			if (projectileNode.elem.update(map))
				projectile.remove(projectileNode);
		}
		
		Iterator<LList<Item>.Node> itemIterator = item.nodeIterator();
		LList<Item>.Node itemNode;
		while (itemIterator.hasNext()) {
			itemNode = itemIterator.next();
			if (itemNode.elem.update(map, player, projectile, item, particle))
				item.remove(itemNode);
		}
		
		Iterator<LList<Particle>.Node> particleIterator = particle.nodeIterator();
		LList<Particle>.Node particleNode;
		while (particleIterator.hasNext()) {
			particleNode = particleIterator.next();
			if (particleNode.elem.update(map))
				particle.remove(particleNode);
		}
	}
	
	private void updatePause() {
		player.updateCharacterMode();
	}
	
	void draw(Painter painter) {
		switch (state) {
			case STATE_PLAY:
				painter.drawRect(0, 0, 1, 1, Color.WHITE);
				map.draw();
				userInterfaceHandler.playUserInterface.draw(painter);
				break;
			case STATE_PAUSED:
				userInterfaceHandler.characterUserInterface.draw(painter);
				break;
		}
	}
}	
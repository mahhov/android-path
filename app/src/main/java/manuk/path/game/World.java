package manuk.path.game;

import android.graphics.Color;
import manuk.path.game.character.Enemy;
import manuk.path.game.character.Player;
import manuk.path.game.controller.Controller;
import manuk.path.game.item.Item;
import manuk.path.game.map.Map;
import manuk.path.game.map.mapgenerator.MapGenerator;
import manuk.path.game.map.mapgenerator.MapGenerator.Pos3;
import manuk.path.game.map.mapgenerator.RoomMapGenerator;
import manuk.path.game.painter.Painter;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;

import java.util.Iterator;

class World {
	boolean gameOver;
	private UserInterface userInterface;
	private Map map;
	private Player player;
	private LList<Enemy> enemy;
	private LList<Projectile> projectile;
	private LList<Item> item;
	private LList<Particle> particle;
	
	World(int width, int length, int height) {
		userInterface = new UserInterface();
		MapGenerator mapGenerator = new RoomMapGenerator();
		mapGenerator.generate(width, length, height);
		map = new Map(width, length, height, mapGenerator);
		player = new Player(mapGenerator, userInterface, map);
		enemy = new LList<>();
		for (Pos3 enemyPos : mapGenerator.enemySpawn)
			enemy.addHead(Enemy.create(enemyPos.x, enemyPos.y, enemyPos.z, map));
		projectile = new LList<>();
		item = new LList<>();
		particle = new LList<>();
	}
	
	void update(Controller controller) {
		userInterface.handleInput(controller);
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
			if (itemNode.elem.update(map, player))
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
	
	void draw(Painter painter) {
		painter.drawRect(0, 0, 1, 1, Color.WHITE);
		map.draw();
		userInterface.draw(painter);
		//		if (gameOver)
		//			painter.drawText("GAME OVER :(", .5, .5, size);
	}
}	
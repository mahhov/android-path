package manuk.path.game.map.mapgenerator;

import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

public class CavernMapGenerator extends MapGenerator {
	private static final int PASSES = 5, MAX_SPAWN_TRY = 50, MIN_SPACE_FACTOR = 5, ENEMY_ATTEMPTS = 100;
	int[][][] mapTemp;
	int width, length, height;
	
	public int[][][] generate(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		
		initRand();
		smooth();
		spawn = findSpawn();
		if (spawn == null || !fillExternal())
			return generate(width, length, height);
		transferFinal();
		addEnemies();
		
		return map;
	}
	
	private void initRand() {
		mapTemp = new int[width][length][PASSES + 3];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				if (randFlip())
					mapTemp[x][y][0] = 1;
	}
	
	private void smooth() {
		for (int z = 0; z < PASSES; z++)
			for (int x = 0; x < width; x++)
				for (int y = 0; y < length; y++) {
					int count = 0;
					for (int xx = Math3D.max(x - 1, 0); xx <= Math3D.min(x + 1, width - 1); xx++)
						for (int yy = Math3D.max(y - 1, 0); yy <= Math3D.min(y + 1, length - 1); yy++)
							if (mapTemp[xx][yy][z] == 1)
								count++;
					if (count >= 5)
						mapTemp[x][y][z + 1] = 1;
				}
	}
	
	private Pos findSpawn() {
		int x, y;
		int maxSpawnTry = MAX_SPAWN_TRY;
		do {
			x = randInt(0, width);
			y = randInt(0, length);
		} while (mapTemp[x][y][PASSES] == 1 && maxSpawnTry-- > 0);
		if (mapTemp[x][y][PASSES] != 1)
			return new Pos(x, y);
		return null;
	}
	
	private boolean fillExternal() {
		int minSpace = width * length / MIN_SPACE_FACTOR;
		LList<Pos> search = new LList<>();
		search.addHead(new Pos(spawn.x, spawn.y));
		mapTemp[spawn.x][spawn.y][PASSES] = 2;
		while (!search.isEmpty()) {
			Pos xy = search.removeTail();
			if (xy.x > 0 && mapTemp[xy.x - 1][xy.y][PASSES] == 0) {
				mapTemp[xy.x - 1][xy.y][PASSES] = 2;
				search.addHead(new Pos(xy.x - 1, xy.y));
			}
			if (xy.x < width - 1 && mapTemp[xy.x + 1][xy.y][PASSES] == 0) {
				mapTemp[xy.x + 1][xy.y][PASSES] = 2;
				search.addHead(new Pos(xy.x + 1, xy.y));
			}
			if (xy.y > 0 && mapTemp[xy.x][xy.y - 1][PASSES] == 0) {
				mapTemp[xy.x][xy.y - 1][PASSES] = 2;
				search.addHead(new Pos(xy.x, xy.y - 1));
			}
			if (xy.y < length - 1 && mapTemp[xy.x][xy.y + 1][PASSES] == 0) {
				mapTemp[xy.x][xy.y + 1][PASSES] = 2;
				search.addHead(new Pos(xy.x, xy.y + 1));
			}
			minSpace--;
		}
		return minSpace <= 0;
	}
	
	private void transferFinal() {
		map = new int[width][length][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				map[x][y][0] = mapTemp[x][y][PASSES] == 2 ? 0 : 1;
	}
	
	private void addEnemies() {
		for (int i = 0; i < ENEMY_ATTEMPTS; i++) {
			Pos xy = findSpawn();
			if (xy != null)
				enemySpawn.addHead(new Pos3(xy.x, xy.y, 1));
		}
	}
}
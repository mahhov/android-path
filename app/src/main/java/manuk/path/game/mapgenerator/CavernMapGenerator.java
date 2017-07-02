package manuk.path.game.mapgenerator;

import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

public class CavernMapGenerator extends MapGenerator {
	private static final int PASSES = 5, MAX_START_TRY = 50, MIN_SPACE_FACTOR = 5;
	int[][][] mapTemp;
	int width, length, height;
	
	public int[][][] generate(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		
		initRand();
		smooth();
		findStart();
		if (!findStart() || !fillExternal())
			return generate(width, length, height);
		transferFinal();
		
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
	
	private boolean findStart() {
		int maxStartTry = MAX_START_TRY;
		do {
			startX = randInt(0, width);
			startY = randInt(0, length);
		} while (mapTemp[startX][startY][PASSES] == 1 && maxStartTry-- > 0);
		return mapTemp[startX][startY][PASSES] == 0;
	}
	
	private boolean fillExternal() {
		int minSpace = width * length / MIN_SPACE_FACTOR;
		LList<Pos> search = new LList<>();
		search.addHead(new Pos(startX, startY));
		mapTemp[startY][startY][PASSES] = 2;
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
	
	private static class Pos {
		private int x, y;
		
		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

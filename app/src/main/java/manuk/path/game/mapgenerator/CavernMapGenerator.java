package manuk.path.game.mapgenerator;

import manuk.path.game.LList;
import manuk.path.game.Util;

public class CavernMapGenerator extends MapGenerator {
	
	public int[][][] generate(int width, int length, int height) {
		int n = 5;
		int[][][] mapTemp = new int[width][length][n + 3];
		
		// init rand fill
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				if (randFlip())
					mapTemp[x][y][0] = 1;
		
		// iterate on map
		for (int z = 0; z < n; z++)
			for (int x = 0; x < width; x++)
				for (int y = 0; y < length; y++) {
					int count = 0;
					for (int xx = Util.max(x - 1, 0); xx <= Util.min(x + 1, width - 1); xx++)
						for (int yy = Util.max(y - 1, 0); yy <= Util.min(y + 1, length - 1); yy++)
							if (mapTemp[xx][yy][z] == 1)
								count++;
					if (count >= 5)
						mapTemp[x][y][z + 1] = 1;
				}
		
		// find free spawn spot
		int maxStartTry = 50;
		do {
			startX = randInt(0, width);
			startY = randInt(0, length);
		} while (mapTemp[startX][startY][n] == 1 && maxStartTry-- > 0);
		if (mapTemp[startX][startY][n] == 1)
			return generate(width, length, height);
		
		// check if spawn has minimum flood space
		int minSpace = width * length / 10;
		LList<Pos> search = new LList<>();
		search.addHead(new Pos(startX, startY));
		mapTemp[startY][startY][n] = 2;
		while (!search.isEmpty()) {
			Pos xy = search.removeTail();
			if (xy.x > 0 && mapTemp[xy.x - 1][xy.y][n] == 0) {
				mapTemp[xy.x - 1][xy.y][n] = 2;
				search.addHead(new Pos(xy.x - 1, xy.y));
			}
			if (xy.x < width - 1 && mapTemp[xy.x + 1][xy.y][n] == 0) {
				mapTemp[xy.x + 1][xy.y][n] = 2;
				search.addHead(new Pos(xy.x + 1, xy.y));
			}
			if (xy.y > 0 && mapTemp[xy.x][xy.y - 1][n] == 0) {
				mapTemp[xy.x][xy.y - 1][n] = 2;
				search.addHead(new Pos(xy.x, xy.y - 1));
			}
			if (xy.y < length - 1 && mapTemp[xy.x][xy.y + 1][n] == 0) {
				mapTemp[xy.x][xy.y + 1][n] = 2;
				search.addHead(new Pos(xy.x, xy.y + 1));
			}
			minSpace--;
		}
		
		// transfer to final map
		map = new int[width][length][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				map[x][y][0] = mapTemp[x][y][n] == 2 ? 0 : 1;
		
		return map;
	}
	
	private static class Pos {
		private int x, y;
		
		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

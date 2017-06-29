package manuk.path.game;

class MapGenerator {
	
	static int[][][] map;
	static int startX, startY;
	
	static int[][][] GenerateRandomMap(int width, int length, int height) {
		startX = width / 2;
		startY = length / 2;
		map = new int[width][length][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				for (int z = 0; z < height; z++)
					if (Math.random() > .98)
						map[x][y][z] = 1;
		return map;
	}
	
	static int[][][] GenerateWalledMap(int width, int length, int height) {
		startX = width / 2;
		startY = length / 2;
		map = new int[width][length][height];
		int dist = 5;
		int z = 1;
		
		for (int i = 0; i < 200; i++) {
			int x1 = randInt(0, width);
			int x2 = randInt(0, width);
			int y1 = randInt(0, length);
			int y2 = randInt(0, length);
			
			boolean direction = randFlip();
			if (direction) {
				// horizontal
				x2 = (x2 + x1) / 2;
				if (x2 < x1) {
					int t = x1;
					x1 = x2;
					x2 = t;
				}
				boolean good = true;
				int ys = y1 - dist;
				int ye = y1 + dist;
				if (ys < 0)
					ys = 0;
				if (ye > length - 1)
					ye = length - 1;
				for (int x = x1; x < x2; x++) {
					for (int y = ys; y < ye; y++)
						if (map[x][y][z] == 1) {
							good = false;
							break;
						}
					if (!good)
						break;
				}
				if (good)
					for (int x = x1; x < x2; x++)
						map[x][y1][z] = 1;
				
			} else {
				// vertical
				y2 = (y2 + y1) / 2;
				if (y2 < y1) {
					int t = y1;
					y1 = y2;
					y2 = t;
				}
				boolean good = true;
				int xs = x1 - dist;
				int xe = x1 + dist;
				if (xs < 0)
					xs = 0;
				if (xe > width - 1)
					xe = width - 1;
				for (int y = y1; y < y2; y++) {
					for (int x = xs; x < xe; x++)
						if (map[x][y][z] == 1) {
							good = false;
							break;
						}
					if (!good)
						break;
				}
				if (good)
					for (int y = y1; y < y2; y++)
						map[x1][y][z] = 1;
			}
		}
		
		return map;
	}
	
	// todo: optimize a lot
	static int[][][] GenerateCavernMap(int width, int length, int height) {
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
			return GenerateCavernMap(width, length, height);
		
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
	
	private static int randInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
	
	private static boolean randFlip() {
		return Math.random() < .5;
	}
	
	private static class Pos {
		private int x, y;
		
		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
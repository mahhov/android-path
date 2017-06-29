package manuk.path.game;

class MapGenerator {
	
	static int[][][] GenerateRandomMap(int width, int length, int height) {
		int[][][] map = new int[width][length][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < length; y++)
				for (int z = 0; z < height; z++)
					if (Math.random() > .98)
						map[x][y][z] = 1;
		return map;
	}
}
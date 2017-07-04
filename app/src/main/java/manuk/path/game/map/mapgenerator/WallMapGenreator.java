package manuk.path.game.map.mapgenerator;

public class WallMapGenreator extends MapGenerator {
	public int[][][] generate(int width, int length, int height) {
		spawn = new Pos(width / 2, length / 2);
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
}

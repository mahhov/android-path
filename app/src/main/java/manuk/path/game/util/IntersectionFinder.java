package manuk.path.game.util;

import manuk.path.game.map.Map;

public class IntersectionFinder {
	private Map map;
	private boolean allowSlide, limitDistance;
	private double x, y;
	private double nextx, nexty;
	private int intx, inty;
	private double movex, movey, move, moved, maxMove;
	private int moveWhich;
	private double[] dir;
	private int collideNum, isDirZeroNum;
	private boolean[] collide, isDirZero;
	
	public IntersectionFinder(Map map) {
		this.map = map;
	}
	
	// return r[3]: 1 if collision, 0 if maxMove
	public double[] find(double[] orig, double[] dir, double maxMove, boolean allowSlide) {
		reset(orig, dir, maxMove, allowSlide);
		if (isDirZeroNum == 2)
			return new double[] {orig[0], orig[1], 1};
		while (true) {
			computeNextMove();
			if (moved + move > maxMove && limitDistance) {
				moveBy(maxMove - moved);
				return new double[] {nextx, nexty, 0};
			}
			moveBy(move + Math3D.EPSILON);
			if (!map.isMoveable(intx, inty, 0)) {
				moveBy(move - Math3D.EPSILON);
				if (collideCheck())
					return new double[] {x, y, 1};
			}
			nextIter();
		}
	}
	
	private void reset(double[] orig, double[] dir, double maxMove, boolean allowSlide) {
		x = nextx = orig[0];
		y = nexty = orig[1];
		intx = (int) x;
		inty = (int) y;
		moved = 0;
		this.maxMove = maxMove;
		this.limitDistance = maxMove > 0;
		this.dir = Math3D.setMagnitude(dir[0], dir[1], 1);
		this.allowSlide = allowSlide;
		collideNum = 0;
		isDirZeroNum = 0;
		collide = new boolean[] {false, false};
		isDirZero = new boolean[] {Math3D.isZero(dir[0]), Math3D.isZero(dir[1])};
		if (isDirZero[0]) {
			dir[0] = 0;
			isDirZeroNum++;
		}
		if (isDirZero[1]) {
			dir[1] = 0;
			isDirZeroNum++;
		}
	}
	
	private void computeNextMove() {
		if (isDirZero[0])
			movex = Math3D.sqrt3;
		else {
			if (dir[0] > 0)
				movex = (1 + intx - x) / dir[0];
			else {
				movex = (intx - x) / dir[0];
			}
		}
		
		if (isDirZero[1])
			movey = Math3D.sqrt3;
		else {
			if (dir[1] > 0)
				movey = (1 + inty - y) / dir[1];
			else
				movey = (inty - y) / dir[1];
		}
		
		if (movex < movey) {
			moveWhich = 0;
			move = movex;
		} else {
			moveWhich = 1;
			move = movey;
		}
	}
	
	private void moveBy(double move) {
		nextx = x + dir[0] * move;
		nexty = y + dir[1] * move;
		
		intx = (int) nextx;
		inty = (int) nexty;
		if (nextx < 0)
			intx--;
		if (nexty < 0)
			inty--;
	}
	
	private boolean collideCheck() {
		if (!allowSlide)
			return true;
		if (!collide[moveWhich]) {
			collideNum++;
			collide[moveWhich] = true;
			if (!isDirZero[moveWhich]) {
				isDirZeroNum++;
				isDirZero[moveWhich] = true;
			}
			dir[moveWhich] = 0;
			return collideNum == 2 || isDirZeroNum == 2;
		}
		return false;
	}
	
	private void nextIter() {
		moved += move;
		x = nextx;
		y = nexty;
	}
}

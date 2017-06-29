package manuk.path.game;

import android.graphics.Color;

class MapPainter {
	private static final double Z_SHIFT_X = 1. / 3, Z_SHIFT_Y = 1. / 6;
	private static Painter painter;
	
	static void setPainter(Painter painter) {
		MapPainter.painter = painter;
	}
	
	static void drawFlat(double cx, double cy, double z, int color) {
		double x = (cx - Z_SHIFT_X * z) / Engine.VIEW_WIDTH - Engine.BLOCK_HEIGHT / 2;
		double y = (cy - Z_SHIFT_Y * z) / Engine.VIEW_HEIGHT - Engine.BLOCK_WIDTH / 2;
		painter.drawRect(cx, cy, Engine.BLOCK_WIDTH, Engine.BLOCK_HEIGHT, color);
	}
	
	static double topZ, leftBottomX, leftTopX, rightBottomX, rightTopX, backBottomY, backTopY, frontBottomY, frontTopY;
	static double[] rightX = null, rightY = null, frontX = null, frontY = null, topX = null, topY = null, bottomX = null, bottomY = null;
	
	static void drawBlock(double x, double y, double z, boolean right, boolean front, boolean top, int color) {
		topZ = z + 1;
		
		// x
		leftBottomX = (x - Z_SHIFT_X * z) * Engine.BLOCK_WIDTH;
		leftTopX = (x - Z_SHIFT_X * topZ) * Engine.BLOCK_WIDTH;
		rightBottomX = leftBottomX + Engine.BLOCK_WIDTH;
		rightTopX = leftTopX + Engine.BLOCK_WIDTH;
		
		// y
		backBottomY = (y - Z_SHIFT_Y * z) * Engine.BLOCK_WIDTH;
		backTopY = (y - Z_SHIFT_Y * topZ) * Engine.BLOCK_WIDTH;
		frontBottomY = backBottomY + Engine.BLOCK_WIDTH;
		frontTopY = backTopY + Engine.BLOCK_WIDTH;
		
		// fill
		
		// right face
		if (right) {
			rightX = new double[] {rightTopX, rightBottomX, rightBottomX, rightTopX};
			rightY = new double[] {backTopY, backBottomY, frontBottomY, frontTopY};
			painter.drawPolygon(rightX, rightY, color, false);
		}
		
		// front face
		if (front) {
			frontX = new double[] {leftTopX, rightTopX, rightBottomX, leftBottomX};
			frontY = new double[] {frontTopY, frontTopY, frontBottomY, frontBottomY};
			painter.drawPolygon(frontX, frontY, color, false);
		}
		
		// top face
		if (top) {
			topX = new double[] {leftTopX, rightTopX, rightTopX, leftTopX};
			topY = new double[] {backTopY, backTopY, frontTopY, frontTopY};
			painter.drawPolygon(topX, topY, color, false);
		}
		
		// outline
		
		// right face
		if (right)
			painter.drawPolygon(rightX, rightY, Color.WHITE, true);
		
		// front face
		if (front)
			painter.drawPolygon(frontX, frontY, Color.WHITE, true);
		
		// top face
		if (top)
			painter.drawPolygon(topX, topY, Color.WHITE, true);
	}
}

package manuk.path.game.painter;

import android.graphics.Color;
import manuk.path.game.util.Measurements;

public class MapPainter {
	private static Painter painter;
	public static final int LEFT = 0, RIGHT = 1, BACK = 2, FRONT = 3, BOTTOM = 4, TOP = 5;
	private static final double[] LIGHT = new double[6];
	
	// local variables, defined here to avoid allocation
	private static double[] bottomCoord, topCoord;
	private static double leftBottomX, leftTopX, rightBottomX, rightTopX, backBottomY, backTopY, frontBottomY, frontTopY;
	private static double[] leftX = null, rightX = null, sideX = null, backY = null, frontY = null, sideY = null, topX = null, topY = null; // todo: check if this is any benefit
	private static double[] coord = new double[4];
	
	static {
		LIGHT[FRONT] = .7;
		LIGHT[TOP] = .9;
		LIGHT[LEFT] = .6;
		LIGHT[RIGHT] = .4;
		LIGHT[BACK] = .3;
		LIGHT[BOTTOM] = .1;
	}
	
	public static void setPainter(Painter painter) {
		MapPainter.painter = painter;
	}
	
	public static void drawFlat(double x, double y, double z, int color) {
		bottomCoord = toPaintCoord(x, y, z, 1, 1);
		painter.drawRect(bottomCoord[0], bottomCoord[1], bottomCoord[2], bottomCoord[3], color);
	}
	
	public static void drawBlock(double x, double y, double z, double width, double length, double height, boolean[] side, int[] color) {
		bottomCoord = toPaintCoord(x, y, z, width, length);
		topCoord = toPaintCoord(x, y, z + height, width, length);
		
		// x
		leftBottomX = bottomCoord[0];
		leftTopX = topCoord[0];
		rightBottomX = leftBottomX + bottomCoord[2];
		rightTopX = leftTopX + topCoord[2];
		
		// y
		backBottomY = bottomCoord[1];
		backTopY = topCoord[1];
		frontBottomY = backBottomY + bottomCoord[3];
		frontTopY = backTopY + topCoord[3];
		
		// fill
		
		if (side[LEFT]) {
			leftX = new double[] {leftTopX, leftBottomX, leftBottomX, leftTopX};
			sideY = new double[] {backTopY, backBottomY, frontBottomY, frontTopY};
			painter.drawPolygon(leftX, sideY, color[LEFT]);
		}
		
		if (side[RIGHT]) {
			rightX = new double[] {rightTopX, rightBottomX, rightBottomX, rightTopX};
			if (!side[LEFT])
				sideY = new double[] {backTopY, backBottomY, frontBottomY, frontTopY};
			painter.drawPolygon(rightX, sideY, color[RIGHT]);
		}
		
		if (side[BACK]) {
			sideX = new double[] {leftTopX, rightTopX, rightBottomX, leftBottomX};
			backY = new double[] {backTopY, backTopY, backBottomY, backBottomY};
			painter.drawPolygon(sideX, backY, color[BACK]);
		}
		
		if (side[FRONT]) {
			if (!side[BACK])
				sideX = new double[] {leftTopX, rightTopX, rightBottomX, leftBottomX};
			frontY = new double[] {frontTopY, frontTopY, frontBottomY, frontBottomY};
			painter.drawPolygon(sideX, frontY, color[FRONT]);
		}
		
		if (side[TOP]) {
			topX = new double[] {leftTopX, rightTopX, rightTopX, leftTopX};
			topY = new double[] {backTopY, backTopY, frontTopY, frontTopY};
			painter.drawPolygon(topX, topY, color[TOP]);
		}
	}
	
	private static double[] toPaintCoord(double x, double y, double z, double width, double length) {
		coord = new double[4];
		double stretchX = Measurements.SCALED_BLOCK_WIDTH + Measurements.VIEW_STRETCH_Z * z;
		double stretchY = Measurements.SCALED_BLOCK_HEIGHT + Measurements.VIEW_STRETCH_Z * z;
		coord[0] = (x - Measurements.SCALED_VIEW_WIDTH / 2.) * stretchX + .5;
		coord[1] = (y - Measurements.SCALED_VIEW_HEIGHT / 2.) * stretchY + .5;
		coord[2] = width * stretchX;
		coord[3] = length * stretchY;
		return coord;
	}
	
	public static int[] createColorShade(int color) {
		return createColorShade((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
	}
	
	public static int[] createColorShade(int red, int green, int blue) {
		int[] shade = new int[LIGHT.length];
		for (int i = 0; i < shade.length; i++)
			shade[i] = Color.rgb((int) (red * LIGHT[i]), (int) (green * LIGHT[i]), (int) (blue * LIGHT[i]));
		return shade;
	}
}

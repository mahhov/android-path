package manuk.path.game.painter;

import android.graphics.Color;
import manuk.path.game.util.Measurements;

public class MapPainter {
	private static Painter painter;
	public static final int LEFT = 0, RIGHT = 1, BACK = 2, FRONT = 3, BOTTOM = 4, TOP = 5;
	public static final int[] MAP_COLOR = new int[6];
	
	// local variables, defined here to avoid allocation
	private static double[] bottomCoord, topCoord;
	private static double leftBottomX, leftTopX, rightBottomX, rightTopX, backBottomY, backTopY, frontBottomY, frontTopY;
	private static double[] leftX = null, rightX = null, sideX = null, backY = null, frontY = null, sideY = null, topX = null, topY = null; // todo: check if this is any benefit
	private static double[] coord = new double[4];
	
	public static void init(Painter painter) {
		MapPainter.painter = painter;
		int red = 6, green = 12, blue = 6;
		int i = 2;
		MAP_COLOR[FRONT] = Color.rgb(red * i, green * i, blue * i);
		i++;
		MAP_COLOR[TOP] = Color.rgb(red * i, green * i, blue * i);
		i++;
		MAP_COLOR[LEFT] = Color.rgb(red * i, green * i, blue * i);
		i++;
		MAP_COLOR[RIGHT] = Color.rgb(red * i, green * i, blue * i);
		i++;
		MAP_COLOR[BACK] = Color.rgb(red * i, green * i, blue * i);
		i++;
		MAP_COLOR[BOTTOM] = Color.rgb(red * i, green * i, blue * i);
	}
	
	public static void drawFlat(double x, double y, double z, int color) {
		bottomCoord = toPaintCoord(x, y, z, 1, 1);
		painter.drawRect(bottomCoord[0], bottomCoord[1], bottomCoord[2], bottomCoord[3], color);
	}
	
	public static void drawBlock(double x, double y, double z, boolean[] side, int color) {
		drawBlock(x, y, z, side, new int[] {color, color, color, color, color, color});
	}
	
	public static void drawBlock(double x, double y, double z, boolean[] side, int[] color) {
		bottomCoord = toPaintCoord(x, y, z, 1, 1);
		topCoord = toPaintCoord(x, y, z + 1, 1, 1);
		
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
			painter.drawPolygon(leftX, sideY, color[LEFT], false);
		}
		
		if (side[RIGHT]) {
			rightX = new double[] {rightTopX, rightBottomX, rightBottomX, rightTopX};
			if (!side[LEFT])
				sideY = new double[] {backTopY, backBottomY, frontBottomY, frontTopY};
			painter.drawPolygon(rightX, sideY, color[RIGHT], false);
		}
		
		if (side[BACK]) {
			sideX = new double[] {leftTopX, rightTopX, rightBottomX, leftBottomX};
			backY = new double[] {backTopY, backTopY, backBottomY, backBottomY};
			painter.drawPolygon(sideX, backY, color[BACK], false);
		}
		
		if (side[FRONT]) {
			if (!side[BACK])
				sideX = new double[] {leftTopX, rightTopX, rightBottomX, leftBottomX};
			frontY = new double[] {frontTopY, frontTopY, frontBottomY, frontBottomY};
			painter.drawPolygon(sideX, frontY, color[FRONT], false);
		}
		
		if (side[TOP]) {
			topX = new double[] {leftTopX, rightTopX, rightTopX, leftTopX};
			topY = new double[] {backTopY, backTopY, frontTopY, frontTopY};
			painter.drawPolygon(topX, topY, color[TOP], false);
		}
	}
	
	private static double[] toPaintCoord(double x, double y, double z, double width, double height) {
		coord = new double[4];
		double stretchX = Measurements.SCALED_BLOCK_WIDTH + Measurements.VIEW_STRETCH_Z * z;
		double stretchY = Measurements.SCALED_BLOCK_HEIGHT + Measurements.VIEW_STRETCH_Z * z;
		coord[0] = (x - Measurements.SCALED_VIEW_WIDTH / 2.) * stretchX + .5;
		coord[1] = (y - Measurements.SCALED_VIEW_HEIGHT / 2.) * stretchY + .5;
		coord[2] = width * stretchX;
		coord[3] = height * stretchY;
		return coord;
	}
}

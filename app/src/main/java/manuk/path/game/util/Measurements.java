package manuk.path.game.util;

public class Measurements {
	private static final boolean OVERVIEW_MODE = false;
	
	public static final int MAP_WIDTH = 100, MAP_LENGTH = MAP_WIDTH, MAP_HEIGHT = 3;
	private static final int VIEW_RATIO = 1;
	private static final int VIEW_WIDTH = 30 * (OVERVIEW_MODE ? 3 : 1), VIEW_HEIGHT = VIEW_WIDTH * VIEW_RATIO;
	
	public static int SCALED_VIEW_WIDTH = VIEW_WIDTH, SCALED_VIEW_HEIGHT = VIEW_HEIGHT;
	public static double SCALED_BLOCK_WIDTH = 1. / SCALED_VIEW_WIDTH, SCALED_BLOCK_HEIGHT = 1. / SCALED_VIEW_HEIGHT;
	
	public static int SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_SHIFT_X, SCREEN_SHIFT_Y;
	
	public static final double VIEW_STRETCH_Z = SCALED_BLOCK_WIDTH / 10 * (OVERVIEW_MODE ? 0 : 1);
	
	public static void init(int fullWidth, int fullHeight) {
		SCREEN_WIDTH = fullWidth;
		SCREEN_HEIGHT = fullWidth * VIEW_RATIO;
		SCREEN_SHIFT_X = 0;
		SCREEN_SHIFT_Y = (fullHeight - SCREEN_HEIGHT) / 2;
	}
	
	public static void setScale(double scale) {
		double viewWidth = VIEW_WIDTH * scale;
		double viewHeight = VIEW_HEIGHT * scale;
		SCALED_VIEW_WIDTH = (int) viewWidth;
		SCALED_VIEW_HEIGHT = (int) viewHeight;
		SCALED_BLOCK_WIDTH = 1. / viewWidth;
		SCALED_BLOCK_HEIGHT = 1. / viewHeight;
	}
}

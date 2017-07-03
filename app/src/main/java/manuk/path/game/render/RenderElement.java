package manuk.path.game.render;

public abstract class RenderElement {
	abstract void draw();
	
	static float[] floatColor(int color) {
		return new float[] {((color >> 16) & 0xff) / 255f, ((color >> 8) & 0xff) / 255f, (color & 0xff) / 255f, 1f};
	}
}

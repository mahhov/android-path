package manuk.path.game.render.texture;

import manuk.path.game.render.RenderElement;

public class CharGlyphTextureGroup {
	private static CharGlyphTexture[] texture;
	
	public static void init() {
		texture = new CharGlyphTexture[127];
		for (int i = 32; i < texture.length; i++)
			texture[i] = new CharGlyphTexture((char) i + "");
	}
	
	public static RenderElement[] drawString(String s, float x, float y, float w, float h) {
		RenderElement[] r = new RenderElement[s.length()];
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++)
			if (c[i] >= 0 && c[i] < texture.length)
				r[i] = texture[c[i]].draw(x + w * i, y, w, h);
		return r;
	}
}

package manuk.path.game.projectile;

import android.graphics.Color;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.util.IntersectionFinder;

public class DelayedProjectile extends Projectile {
	private int colorRed, colorGreen, colorBlue;
	private double delay, maxDelay;
	
	public DelayedProjectile(int layer, double x, double y, double size, double damage, int color, double delay, Map map) {
		super(layer, x, y, size, damage, color);
		colorRed = Color.red(color);
		colorGreen = Color.green(color);
		colorBlue = Color.blue(color);
		this.delay = maxDelay = delay;
		map.moveEntity(new double[] {x, y}, this);
	}
	
	// return true if need to be removed
	public boolean update(Map map) {
		if (--delay > 0)
			return false;
		IntersectionFinder.Intersection intersection = map.moveEntity(new double[] {x, y}, new double[] {1, 0}, 0, false, MapEntity.ENTITY_LAYER_HOSTILE_PROJECTILE, this);
		if (intersection.state == IntersectionFinder.Intersection.COLLISION_ENTITY)
			expire(map, intersection.entityCollide);
		else
			expire(map, null);
		return true;
	}
	
	public void draw(double scrollX, double scrollY) {
		if (delay > 5)
			MapPainter.drawFlatFrame(x - scrollX - size, y - scrollY - size, 0, size * 2, size * 2, getColor());
		else
			MapPainter.drawFlat(x - scrollX - size, y - scrollY - size, 0, size * 2, size * 2, getColor());
	}
	
	private int getColor() {
		double delayMult = delay / maxDelay;
		int r = (int) (colorRed * delayMult);
		int g = (int) (colorGreen * delayMult);
		int b = (int) (colorBlue * delayMult);
		return Color.rgb(r, g, b);
	}
}
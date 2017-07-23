package manuk.path.game.item;

import android.graphics.Color;
import manuk.path.game.character.Player;
import manuk.path.game.map.Map;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;

public class HealthItem extends Item {
	private boolean remove;
	
	public HealthItem(double x, double y, Map map) {
		super(ENTITY_LAYER_DROPPED_ITEM, 1, x, y, Color.YELLOW, map);
	}
	
	// return true if need to be removed
	public boolean update(Map map, Player player, LList<Projectile> projectile, LList<Item> item, LList<Particle> particle) {
		if (!remove)
			return false;
		player.giveLife(5);
		map.removeEntity(this);
		return true;
	}
	
	public void onIntersection(double damageAmount) {
		remove = true;
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		MapPainter.drawBlock(x - scrollX - .25, y - scrollY - .25, 0, .5, .5, .5, side, color);
	}
}

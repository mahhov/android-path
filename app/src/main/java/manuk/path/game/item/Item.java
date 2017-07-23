package manuk.path.game.item;

import manuk.path.game.character.Player;
import manuk.path.game.map.Map;
import manuk.path.game.map.MapEntity;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.particle.Particle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;

public abstract class Item extends MapEntity {
	double x, y;
	int[] color;
	private boolean remove;
	
	public Item(int layer, double size, double x, double y, int color, Map map) {
		super(layer, size);
		this.x = x;
		this.y = y;
		this.color = MapPainter.createColorShade(color);
		map.moveEntity(new double[] {x, y}, this);
	}
	
	// return true if need to be removed
	public abstract boolean update(Map map, Player player, LList<Projectile> projectile, LList<Item> item, LList<Particle> particle);
	
	public void draw(double scrollX, double scrollY) {
	}
}

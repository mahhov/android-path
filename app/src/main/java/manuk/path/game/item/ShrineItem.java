package manuk.path.game.item;

import android.graphics.Color;
import manuk.path.game.character.Player;
import manuk.path.game.map.Map;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.particle.Particle;
import manuk.path.game.particle.ShrineParticle;
import manuk.path.game.projectile.Projectile;
import manuk.path.game.util.LList;
import manuk.path.game.util.Math3D;

public class ShrineItem extends Item {
	private static final double SIZE = 1, RANGE = 4;
	private static final double DURATION = 250, HEAL = .2;
	private double duration;
	private boolean active, used;
	
	public ShrineItem(double x, double y, Map map) {
		super(ENTITY_LAYER_DROPPED_ITEM, 0, x, y, Color.YELLOW, map);
	}
	
	// return true if need to be removed
	public boolean update(Map map, Player player, LList<Projectile> projectile, LList<Item> item, LList<Particle> particle) {
		boolean inRange = Math3D.magnitude(player.x - x, player.y - y) < RANGE;
		
		if (active) {
			spawnShrineParticle(map, particle);
			if (inRange)
				player.giveLife(HEAL);
			if (--duration == 0)
				active = false;
		} else if (!used && inRange && player.sprintButtonPressed) {
			active = true;
			duration = DURATION;
			used = true;
		}
		return false;
	}
	
	private void spawnShrineParticle(Map map, LList<Particle> particle) {
		double edgeX = x;
		double edgeY = y;
		int edge = (int) (Math3D.random() * 4);
		switch (edge) {
			case 0:
				edgeX -= SIZE;
				edgeY += Math3D.random(-SIZE, SIZE);
				break;
			case 1:
				edgeX += SIZE;
				edgeY += Math3D.random(-SIZE, SIZE);
				break;
			case 2:
				edgeY -= SIZE;
				edgeX += Math3D.random(-SIZE, SIZE);
				break;
			case 3:
				edgeY += SIZE;
				edgeX += Math3D.random(-SIZE, SIZE);
				break;
		}
		particle.addHead(new ShrineParticle(edgeX, edgeY, map));
	}
	
	public void draw(double scrollX, double scrollY) {
		boolean[] side = new boolean[] {true, true, true, true, true, true};
		if (used && !active)
			MapPainter.drawBlock(x - scrollX - SIZE, y - scrollY - SIZE, 0, SIZE * 2, SIZE * 2, SIZE * 2, side, color, true);
		else
			MapPainter.drawBlock(x - scrollX - SIZE, y - scrollY - SIZE, 0, SIZE * 2, SIZE * 2, SIZE * 2, side, color);
	}
}

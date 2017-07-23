package manuk.path.game;

import manuk.path.MySurface;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.painter.Painter;
import manuk.path.game.render.MyRenderer;
import manuk.path.game.render.texture.CharGlyphTextureGroup;
import manuk.path.game.util.Frames;
import manuk.path.game.util.Measurements;

import static manuk.path.game.util.Measurements.*;

public class Engine implements Runnable {
	private Frames frames;
	private Painter painter;
	private Controller controller;
	private World world;
	
	public Engine() {
		world = new World(MAP_WIDTH, MAP_LENGTH, MAP_HEIGHT);
		controller = new Controller();
	}
	
	public void setupRenderer(MySurface mySurface, MyRenderer myRenderer, int screenWidth, int screenHeight) {
		Measurements.init(screenWidth, screenHeight);
		CharGlyphTextureGroup.init();
		painter = new Painter(mySurface, myRenderer);
		MapPainter.setPainter(painter);
		mySurface.setController(controller);
		frames = new Frames();
	}
	
	private void draw() {
		painter.prep();
		world.draw(painter);
		frames.draw(painter);
		painter.post();
	}
	
	private void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (frames == null || !frames.running)
			sleep(500);
		while (frames.running) {
			world.update(controller);
			draw();
			frames.waitCurrentFrame();
		}
	}
	
	public void stop() {
		frames.running = false;
	}
}

// todo: hold off
// todo: better combat
// todo: item types
// todo: item enchanting and crafting costs
// todo: damage types
// todo: skill system
// todo: level tree
// life (regen / total) / mana (regen / total) -> blood magic, mind over matter, 
// armor / evs / es -> all others converted to 1
// crit / attack speed -> crits increase attack speed, attacks increase crit chance, 


// todo: fix side darwing of character
// todo: lighting and shadow
// todo: textures
// todo: models
// todo: better camera perspective
// todo: better enemy path finding
// todo: level up - 10 life, 10 mana
// todo: calculate VIEW_STRETCH_Z correctly
// todo: shrines
// todo: refactor character counter with delay and step
// todo: intersectino finder redo layer system / map entity array flatten ll dimension
// todo: ui mode
// todo: map secret paths
// todo: traps
// todo: exp lvl up skills
// todo: fix controller lock bug
// constructor -> map last param. methods -> map first param
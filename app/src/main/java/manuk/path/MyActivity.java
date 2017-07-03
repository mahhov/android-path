package manuk.path;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import manuk.path.game.Engine;
import manuk.path.game.render.MyRenderer;

public class MyActivity extends AppCompatActivity {
	private MyRenderer myRenderer;
	private MySurface mySurface;
	private Engine engine;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myRenderer = new MyRenderer(this);
		mySurface = new MySurface(this, myRenderer);
		setContentView(mySurface);
		engine = new Engine();
	}
	
	public void newSurface(int width, int height) {
		engine.setupRenderer(mySurface, myRenderer, width, height);
	}
	
	protected void onResume() {
		super.onResume();
		mySurface.onResume();
		new Thread(engine).start();
	}
	
	protected void onPause() {
		super.onPause();
		mySurface.onPause();
		engine.stop();
	}
}

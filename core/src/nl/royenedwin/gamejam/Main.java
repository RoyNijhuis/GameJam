package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	public static final String BACKGROUND_PATH = "background.jpg";
	private SpriteBatch batch;
	private Sprite background;
	private Field field1;
	public static Vector2 SCALING_FACTOR;
	private Mario mario;
	private static ArrayList<Object> objects;
	
	@Override
	public void create () {
		SCALING_FACTOR = new Vector2(Gdx.graphics.getWidth()/1920f, Gdx.graphics.getHeight()/1088f);
		objects = new ArrayList<Object>();
		batch = new SpriteBatch();
		field1 = new Field();
		objects.add(field1);
		mario = new Mario(field1);
		objects.add(mario);
		background = new Sprite(new Texture(BACKGROUND_PATH));
		Gdx.input.setInputProcessor(new InputProcessor(mario));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(43f/255f, 255f/255f, 24f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, background.getWidth()*SCALING_FACTOR.x, background.getHeight()*SCALING_FACTOR.y);
		float delta = Gdx.graphics.getDeltaTime();
		for(Object o: objects) {
			if(o instanceof Updateable) {
				((Updateable) o).update(delta);
			}
			if(o instanceof Drawable) {
				((Drawable) o).render(batch);
			}
		}
		batch.end();
	}
	
	public static void createStone(Vector2 position, Vector2 velocity) {
		objects.add(new Stone(position, velocity));
	}
	
	public static ArrayList<Object> getObjects() {
		return objects;
	}
}

package nl.royenedwin.gamejam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private Field field1;
	public static Vector2 SCALING_FACTOR;
	private Mario mario;
	
	@Override
	public void create () {
		SCALING_FACTOR = new Vector2(Gdx.graphics.getWidth()/1920f, Gdx.graphics.getHeight()/1088f);
		batch = new SpriteBatch();
		field1 = new Field();
		mario = new Mario(field1);
		Gdx.input.setInputProcessor(new InputProcessor(mario));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		field1.render(batch);
		mario.render(batch);
		mario.update();
		batch.end();
	}
}

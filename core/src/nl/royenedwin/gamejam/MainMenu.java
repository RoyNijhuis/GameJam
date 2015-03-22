package nl.royenedwin.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainMenu implements Screen {

	SpriteBatch batch;
	Sprite bg;
	public static Vector2 SCALING_FACTOR;
	Main main;
	
	public MainMenu(Main main) {
		this.main = main;
		System.out.println("test");
	}
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		bg = new Sprite(new Texture("hoofdmenu.png"));
		SCALING_FACTOR = new Vector2(Gdx.graphics.getWidth()/1920f, Gdx.graphics.getHeight()/1088f);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(bg, 0, 0, 1920*SCALING_FACTOR.x, 1088*SCALING_FACTOR.y);
		batch.end();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if(Gdx.input.getX() > 140*SCALING_FACTOR.x && Gdx.input.getX() < 640*SCALING_FACTOR.x && Gdx.input.getY() > 490*SCALING_FACTOR.y && Gdx.input.getY() < 870*SCALING_FACTOR.y) {
				main.setScreen(new Game(main, 0));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}

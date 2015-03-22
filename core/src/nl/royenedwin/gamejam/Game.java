package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Game implements Screen{
	public static final String BACKGROUND_PATH0 = "background0.jpg";
	public static final String BACKGROUND_PATH = "background2.jpg";
	private SpriteBatch batch;
	private Sprite background;
	private static Field field1;
	public static Vector2 SCALING_FACTOR;
	private static Mario mario;
	private static boolean spawnGhost;
	private static Ghost ghost;
	private static ArrayList<Object> objects;
	private static ArrayList<Explosion> explosions;
	private static Sound sound;
	private static ArrayList<Object> remove;
	private static Main main;
	private static int level;
	
	public Game(Main main, int level, boolean spawnGhost) {
		this.main = main;
		this.level = level;
		this.spawnGhost = spawnGhost;
	}
	
	@Override
	public void show () {
		SCALING_FACTOR = new Vector2(Gdx.graphics.getWidth()/1920f, Gdx.graphics.getHeight()/1088f);
		objects = new ArrayList<Object>();
		explosions = new ArrayList<Explosion>();
		remove = new ArrayList<Object>();
		batch = new SpriteBatch();
		field1 = new Field(level);
		objects.add(field1);
		mario = new Mario(field1);
		if(spawnGhost) {
			objects.add(ghost = new Ghost(new Vector2(0,0), mario));
		}
		sound = Gdx.audio.newSound(Gdx.files.internal("essai01.wav"));
		objects.add(mario);
		if(level!=0){
			background = new Sprite(new Texture(BACKGROUND_PATH));
		} else {
			background = new Sprite(new Texture(BACKGROUND_PATH0));
		}
		Gdx.input.setInputProcessor(new InputProcessor(mario));
		sound.loop();
	}
	
	public static void addExplosion(Explosion e) {
		explosions.add(e);
	}
	
	public static void removeExplosion(Explosion e) {
		remove.add(e);
	}
	
	public static void kill() {
		field1 = null;
		//TODO draw screen
		//TODO sleep
		main.setScreen(new MainMenu(main));
		sound.stop();
		sound.dispose();
	}
	
	public static void nextLevel() {
		boolean g = false;
		if(level+1==2 || level+1 ==3) {
			g = true;
		}
		main.setScreen(new Game(main, level+1, g));
		sound.stop();
		sound.dispose();
	}
	
	public static Mario getMario() {
		return mario;
	}
	
	public static Ghost getGhost(){
		return ghost;
	}
	
	public static void createStone(Vector2 position, Vector2 velocity) {
		objects.add(new Stone(position, velocity));
	}
	
	public static void createGrenade(Vector2 position, Vector2 velocity) {
		objects.add(new Grenade(position, velocity));
	}
	
	public static ArrayList<Object> getObjects() {
		return objects;
	}
	
	public static void removeObject(Object o){
		if(o instanceof Grenade) {
			//laat explosie zien
			Game.addExplosion(new Explosion(((Grenade) o).getPosition()));
		}
		remove.add(o);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		sound.stop();
		sound.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(43f/255f, 255f/255f, 24f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, background.getWidth()*SCALING_FACTOR.x, background.getHeight()*SCALING_FACTOR.y);
		for(Object o: objects) {
			if(o instanceof Updateable) {
				((Updateable) o).update(delta);
			}
			if(o instanceof Drawable) {
				((Drawable) o).render(batch);
			}
		}
		for(Explosion e: explosions) {
			e.update(delta);
			e.render(batch);
		}
		for(Object o: remove){
			objects.remove(o);
			explosions.remove(o);
		}
		remove.clear();
		batch.end();
	}

	public static void restart() {
		//TODO draw screen
		//TODO sleep
		boolean g = false;
		if(level==2 || level ==3) {
			g = true;
		}
		main.setScreen(new Game(main, level, g));
		sound.stop();
		sound.dispose();
	}
}

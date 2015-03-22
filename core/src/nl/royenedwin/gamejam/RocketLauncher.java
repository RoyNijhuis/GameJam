package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RocketLauncher implements StaticObject, Updateable, Collidable {
	public static final String TEXTURE_PATH = "rocketlauncher.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Vector2 velocity;
	private float deltaTime;
	private ArrayList<Rocket> rockets;
	private ArrayList<Rocket> toRemove;
	
	public RocketLauncher() {
		deltaTime = 0;
		rockets = new ArrayList<Rocket>();
		toRemove = new ArrayList<Rocket>();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		for(Rocket r: rockets) {
			r.render(batch);
		}
		
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}
	
	public void remove(Rocket rocket) {
		toRemove.add(rocket);
	}

	@Override
	public void update(float delta) {
		deltaTime+=delta;
		
		if(deltaTime > 1) {
			//spawn rocket
			rockets.add(new Rocket(this, Game.getMario(), new Vector2(position.x+0.5f*sprite.getWidth(), position.y+0.5f*sprite.getHeight())));
			deltaTime = 0;
		}
		
		for(Rocket r: rockets) {
			r.update(delta);
		}
		
		for(Rocket r: toRemove) {
			rockets.remove(r);
		}
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPostition() {
		return position;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void isTouched(Object o) {
		// TODO Auto-generated method stub
		
	}
}

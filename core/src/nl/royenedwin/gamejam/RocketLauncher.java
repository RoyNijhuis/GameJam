package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RocketLauncher implements MoveableObject {
	public static final String TEXTURE_PATH = "rocketlauncher.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Vector2 velocity;
	
	public RocketLauncher(Vector2 position, Vector2 velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}

	@Override
	public void update(float delta) {
		
	}

	public Vector2 getPosition() {
		return position;
	}
}

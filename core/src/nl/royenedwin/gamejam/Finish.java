package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Finish implements StaticObject, Collidable {
	public static final String TEXTURE_PATH = "huis.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	
	public Finish() {
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}

	public void isTouched(Object o) {
		Game.nextLevel();
	}

	@Override
	public Vector2 getPostition() {
		// TODO Auto-generated method stub
		return position;
	}
	
}

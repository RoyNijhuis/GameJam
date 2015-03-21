package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Trampoline implements StaticObject, Collidable {

	private Vector2 position;
	public static final String TEXTURE_PATH = "trampoline.png";
	private Sprite sprite;
	
	public Trampoline() {
		sprite = new Sprite(new Texture(TEXTURE_PATH));
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}

	public Vector2 getPostition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public void isTouched(Object o) {
		// TODO Auto-generated method stub
		
	}
}

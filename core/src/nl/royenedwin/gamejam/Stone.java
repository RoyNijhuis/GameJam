package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Stone implements MoveableObject{

	public static final String TEXTURE_PATH = "cobblestone.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Vector2 velocity;
	
	public Stone(Vector2 position, Vector2 velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Main.SCALING_FACTOR.x, position.y*Main.SCALING_FACTOR.y, sprite.getWidth()*Main.SCALING_FACTOR.x, sprite.getHeight()*Main.SCALING_FACTOR.y);
	}

	@Override
	public void update(float delta) {
		velocity.y-=delta*15;
		position.x+=velocity.x;
		position.y+=velocity.y;
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
}

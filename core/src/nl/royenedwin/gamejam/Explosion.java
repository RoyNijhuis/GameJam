package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Explosion implements StaticObject, Updateable{

	public static final String TEXTURE_PATH1 = "explosion1.png";
	public static final String TEXTURE_PATH2 = "explosion2.png";
	public static final String TEXTURE_PATH3 = "explosion3.png";
	public static final String TEXTURE_PATH4 = "explosion4.png";
	public static final String TEXTURE_PATH5 = "explosion5.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH1));
	private Vector2 position;
	private long timeSinceLastImage;
	
	public Explosion(Vector2 position) {
		this.position = position;
		timeSinceLastImage = System.currentTimeMillis();
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}

	public void isTouched(Object o) {
		
	}

	public void update(float delta) {
		if(System.currentTimeMillis() - timeSinceLastImage > 500) {
			timeSinceLastImage = System.currentTimeMillis();
			if(sprite.getTexture().equals(TEXTURE_PATH1)) {
				sprite = new Sprite(new Texture(TEXTURE_PATH2));
			} else if(sprite.getTexture().equals(TEXTURE_PATH2)) {
				sprite = new Sprite(new Texture(TEXTURE_PATH3));
			} else if(sprite.getTexture().equals(TEXTURE_PATH3)) {
				sprite = new Sprite(new Texture(TEXTURE_PATH4));
			} else if(sprite.getTexture().equals(TEXTURE_PATH4)) {
				sprite = new Sprite(new Texture(TEXTURE_PATH5));
			} else if(sprite.getTexture().equals(TEXTURE_PATH5)) {
				sprite = new Sprite(new Texture(TEXTURE_PATH1));
			}
		}
	}

	public Vector2 getPostition() {
		return position;
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
}
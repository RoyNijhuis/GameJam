package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AutoTurret implements StaticObject, Collidable, Updateable{

	public static final String TEXTURE_PATH = "autoturret.png";
	public static final String LASER_PATH = "laser.png";
	private Sprite sprite;
	private Sprite laserSprite;
	private Vector2 position;
	
	public AutoTurret() {
		sprite = new Sprite(new Texture(TEXTURE_PATH));
		laserSprite = new Sprite(new Texture(LASER_PATH));
	}

	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
		int x = (int)(position.x/32);
		int y = (int)(position.y/32);
		
		for(int currentX=x+1; currentX<60; currentX++) {
			if(!Field.fieldIsFull2(currentX, y)) {
				//niet vol, draw laser
				batch.draw(laserSprite, currentX*Game.SCALING_FACTOR.x, (position.y+37)*Game.SCALING_FACTOR.y, laserSprite.getWidth()*Game.SCALING_FACTOR.x, laserSprite.getHeight()*Game.SCALING_FACTOR.y);
			} else {
				//vol, stop laser
				break;
			}
		}
	}

	public Vector2 getPostition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public void isTouched(Object o) {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}
}

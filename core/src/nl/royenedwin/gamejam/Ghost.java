package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ghost implements MoveableObject{
	public static final String TEXTURE_PATH = "mario_ghost.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Vector2 velocity;
	private Mario mario;
	private float rotation;
	
	public Ghost(Vector2 position, Mario mario) {
		this.position = position;
		this.mario = mario;
		rotation = 0;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Main.SCALING_FACTOR.x, position.y*Main.SCALING_FACTOR.y, sprite.getWidth()*Main.SCALING_FACTOR.x, sprite.getHeight()*Main.SCALING_FACTOR.y);
	}

	@Override
	public void update(float delta) {
		Vector2 marioPos = mario.getPositionPixels();
		if (rotation > Math.PI)
			rotation -= 2*Math.PI;
		else if (rotation < -Math.PI)
			rotation += 2*Math.PI;
		 
		float angleToTarget = (float)Math.atan2(marioPos.y - position.y, marioPos.x - position.x); 
		float relativeAngleToTarget = angleToTarget - rotation;
		 
		if (relativeAngleToTarget > Math.PI)
		    relativeAngleToTarget -= 2*Math.PI;
		else if (relativeAngleToTarget < -Math.PI)
		    relativeAngleToTarget += 2*Math.PI;
		                 
		rotation += relativeAngleToTarget;
		position.x += Math.cos(rotation) * 100*delta;
		position.y += Math.sin(rotation) * 100*delta;
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
}

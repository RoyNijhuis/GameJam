package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Rocket implements MoveableObject {
	public static final String TEXTURE_PATH = "rocket.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Vector2 velocity;
	private Mario mario;
	private float rotation;
	private float rocketRotation;
	private RocketLauncher launcher;
	private Sound sound;
	
	public Rocket(RocketLauncher launcher, Mario mario, Vector2 position) {
		this.position = position;
		this.launcher = launcher;
		this.mario = mario;
		sound = Gdx.audio.newSound(Gdx.files.internal("bang.wav"));
		rocketRotation = 0;
		sprite.setOrigin(20, 50);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, 0, 0, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y, 1, 1, (float)Math.toDegrees(rocketRotation)+90);
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
		                 
		rotation += relativeAngleToTarget*delta;
		position.x += Math.cos(rotation) * 100*delta;
		position.y += Math.sin(rotation) * 100*delta;
		rocketRotation = rotation;
		if((int)(position.x/32) == (int)(marioPos.x/32) && (int)(position.y/32) == (int)(marioPos.y/32)){
			mario.takeLive();
			launcher.remove(this);
			sound.play();
		}
		if(Field.fieldIsFull2((int)(position.x/32),(int)(position.y/32))){
			launcher.remove(this);
			sound.play();
		}
		ArrayList<Object>  obj= Game.getObjects();
		for(Object t: obj){
			if(t instanceof Grenade || t instanceof Stone){
				if((int)(position.x/32) == (int)(((MoveableObject)t).getPosition().x/32) && (int)(position.y/32) == (int)((((MoveableObject)t).getPosition().y)/32)){
					launcher.remove(this);
					Game.removeObject(t);
					sound.play();
				} else if((int)((position.x+31)/32) == (int)(((MoveableObject)t).getPosition().x/32) && (int)(position.y/32) == (int)((((MoveableObject)t).getPosition().y)/32)){
					launcher.remove(this);
					Game.removeObject(t);
					sound.play();
				} else if((int)((position.x+31)/32) == (int)(((MoveableObject)t).getPosition().x/32) && (int)((position.y+63)/32) == (int)((((MoveableObject)t).getPosition().y)/32)){
					launcher.remove(this);
					Game.removeObject(t);
					sound.play();
				} else if((int)(position.x/32) == (int)(((MoveableObject)t).getPosition().x/32) && (int)(position.y+63/32) == (int)((((MoveableObject)t).getPosition().y)/32)){
					launcher.remove(this);
					Game.removeObject(t);
					sound.play();
				}
			}
		}
		if(Field.fieldIsFull2((int)(position.x/32),(int)(position.y/32))){
			launcher.remove(this);
			sound.play();
		}
		
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
}

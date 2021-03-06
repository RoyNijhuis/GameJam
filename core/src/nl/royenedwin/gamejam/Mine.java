package nl.royenedwin.gamejam;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Mine implements StaticObject, Collidable, Updateable {
	public static final String TEXTURE_PATH = "mine.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Sound sound;
	
	public Mine() {
		sound = Gdx.audio.newSound(Gdx.files.internal("bang.wav"));
		
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}

	public void isTouched(Object o) {
		//TODO BOOOOOOOOOOOOOOM
		Game.getMario().takeLive();
		Game.addExplosion(new Explosion(position));
		sound.play();
		Field.removeObjectFromField(this);
		
	}

	@Override
	public Vector2 getPostition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void hit2(){
		Game.addExplosion(new Explosion(position));
		sound.play();
		Field.removeObjectFromField(this);
	}
}

package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Portal implements StaticObject, Collidable {
	public static final String PORTAL1_PATH = "portal1.png";
	public static final String PORTAL2_PATH = "portal2.png";
	private int id;
	private static long time;
	private Sprite sprite;
	private Vector2 position;
	
	public Portal(int id) {
		time = 0;
		this.id = id;
		switch(id) {
		case 1:
			sprite = new Sprite(new Texture(PORTAL1_PATH));
			break;
		case 2:
			sprite = new Sprite(new Texture(PORTAL2_PATH));
			break;
		default: 
			System.out.println("ERROR ERROR ERROR");
			break;
		}
	}
	
	public int getID() {
		return id;
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
		if(o instanceof Mario) {
			if(time == 0 || System.currentTimeMillis() - time > 1000) {
				//teleport
				time = System.currentTimeMillis();
				((Mario) o).teleport(position, this);
			}
		}
	}
}

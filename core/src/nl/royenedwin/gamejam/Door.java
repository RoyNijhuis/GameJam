package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Door implements StaticObject, Collidable{

	public static final String TEXTURE_PATH_CLOSED = "door1_closed.png";
	public static final String TEXTURE_PATH_OPEN = "door_open.png";
	private static Sprite closedSprite = new Sprite(new Texture(TEXTURE_PATH_CLOSED));
	private static Sprite openSprite = new Sprite(new Texture(TEXTURE_PATH_OPEN));
	private Vector2 position;
	private boolean isOpen;
	
	public Door() {
		isOpen = false;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(isOpen) {
			batch.draw(openSprite, position.x*Main.SCALING_FACTOR.x, position.y*Main.SCALING_FACTOR.y, closedSprite.getWidth()*Main.SCALING_FACTOR.x, closedSprite.getHeight()*Main.SCALING_FACTOR.y);
		} else {
			batch.draw(closedSprite, position.x*Main.SCALING_FACTOR.x, position.y*Main.SCALING_FACTOR.y, closedSprite.getWidth()*Main.SCALING_FACTOR.x, closedSprite.getHeight()*Main.SCALING_FACTOR.y);
		}
	}

	public void isTouched(Object o) {
		if(o instanceof Mario) {
			if(!isOpen) {
				isOpen = true;
				StaticObject[][] fields = Field.getFields();
				for(int x=0;x<60;x++) {
					for(int y=0;y<34;y++) {
						if(fields[x][y] != null && fields[x][y].equals(this)) {
							fields[x][y-1] = new FullWalk();
						}
					}
				}
			}
		}
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public Vector2 getPostition() {
		// TODO Auto-generated method stub
		return position;
	}
}

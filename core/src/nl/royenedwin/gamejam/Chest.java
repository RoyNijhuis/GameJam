package nl.royenedwin.gamejam;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Chest implements StaticObject, Collidable {
	public static final String TEXTURE_PATH_CLOSED = "chest.png";
	public static final String TEXTURE_PATH_OPEN = "chest_open.png";
	private static Sprite closedSprite = new Sprite(new Texture(TEXTURE_PATH_CLOSED));
	private static Sprite openSprite = new Sprite(new Texture(TEXTURE_PATH_OPEN));
	private Vector2 position;
	private boolean isOpen;
	private ChestItem item;
	
	public Chest(ChestItem item) {
		isOpen = false;
		this.item = item;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(isOpen) {
			batch.draw(openSprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, closedSprite.getWidth()*Game.SCALING_FACTOR.x, closedSprite.getHeight()*Game.SCALING_FACTOR.y);
		} else {
			batch.draw(closedSprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, closedSprite.getWidth()*Game.SCALING_FACTOR.x, closedSprite.getHeight()*Game.SCALING_FACTOR.y);
		}
	}

	public void isTouched(Object o) {
		if(o instanceof Mario && !isOpen) {
			isOpen = true;
			if(item instanceof RandomItem){
				Random random = new Random();
				int i = random.nextInt(100);
				if(i <= 49){
					Game.getMario().addLive();
				} else {
					//TODO grenade
				}
			} else{
				Mario.addChestItem(item);
			}
		}
	}

	@Override
	public Vector2 getPostition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
}

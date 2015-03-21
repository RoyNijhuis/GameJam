package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Door implements StaticObject, Collidable{

	public static final String TEXTURE_PATH_CLOSED1 = "door1_closed.png";
	public static final String TEXTURE_PATH_CLOSED2 = "door2_closed.png";
	public static final String TEXTURE_PATH_CLOSED3 = "door3_closed.png";
	public static final String TEXTURE_PATH_CLOSED4 = "door4_closed.png";
	public static final String TEXTURE_PATH_OPEN = "door_open.png";
	private Sprite closedSprite;
	private static Sprite openSprite = new Sprite(new Texture(TEXTURE_PATH_OPEN));
	private Vector2 position;
	private boolean isOpen;
	private DoorKey key;
	
	public Door(DoorKey key) {
		isOpen = false;
		this.key = key;
		switch(key.getID()) {
		case 1:
			closedSprite = new Sprite(new Texture(TEXTURE_PATH_CLOSED1));
			break;
		case 2:
			closedSprite = new Sprite(new Texture(TEXTURE_PATH_CLOSED2));
			break;
		case 3:
			closedSprite = new Sprite(new Texture(TEXTURE_PATH_CLOSED3));
			break;
		case 4:
			closedSprite = new Sprite(new Texture(TEXTURE_PATH_CLOSED4));
			break;
		default: 
			System.out.println("ERROR ERROR ERROR");
			break;
		}
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
				boolean canOpen = false;
				
				for(ChestItem item: Mario.getChestItems()) {
					if(item instanceof DoorKey && ((DoorKey) item).getID() == ((DoorKey)key).getID()) {
						canOpen = true;
					}
				}
				
				if(canOpen) {
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

package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DoorKey implements ChestItem {
	public static final String KEY1_PATH = "door1_key.png";
	public static final String KEY2_PATH = "door2_key.png";
	public static final String KEY3_PATH = "door3_key.png";
	public static final String KEY4_PATH = "door4_key.png";
	private int id;
	private Sprite sprite;
	
	public DoorKey(int id) {
		this.id = id;
		switch(id) {
		case 1:
			sprite = new Sprite(new Texture(KEY1_PATH));
			break;
		case 2:
			sprite = new Sprite(new Texture(KEY2_PATH));
			break;
		case 3:
			sprite = new Sprite(new Texture(KEY3_PATH));
			break;
		case 4:
			sprite = new Sprite(new Texture(KEY4_PATH));
			break;
		default: 
			break;
		}
	}
	
	public int getID() {
		return id;
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}
}

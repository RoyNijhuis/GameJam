package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Portal {
	public static final String PORTAL1_PATH = "portal1.png";
	public static final String PORTAL2_PATH = "portal2.png";
	private int id;
	private Sprite sprite;
	
	public Portal(int id) {
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
}

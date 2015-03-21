package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Mario implements MovableObject{
	private int sizeX;
	private int sizeY;
	private Vector2 location;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean jump;
	private boolean falling;
	private Field field;
	public static final String TEXTURE_PATH = "block.png";//TODO change
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	
	public Mario(Field field){
		this.field = field;
		sizeX = 1;
		sizeY = 3;
		location = new Vector2(0,0);
	}
	
	private void physics(){
		if(jump){
			if(true){//TODO check field up
				location.y++;
			} else {
				jump = false;
				falling = true;
			}
			
		}
		if(falling){
			if(true){//TODO check field down
				location.y--;
			} else {
				falling = false;
			}
		}
		
		if(movingLeft && !movingRight){
			if(true){//TODO check field left
				location.x--;
			} else{
				movingLeft = false;
			}
		}
		
		if(movingRight && !movingLeft){
			if(true){//TODO check field right
				location.x++;
			} else{
				movingRight = false;
			}
		}
		
		if(!jump && !falling){
			if(true){//TODO check field under
				falling = true;
			}
		}
		
	}
	
	@Override
	public void update(){
		physics();
	}

	@Override
	public void render() {
		batch.draw(sprite, x, y);// TODO Auto-generated method stub
		
	}
	
}

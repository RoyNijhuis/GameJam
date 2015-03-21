package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Mario implements MoveableObject{
	private int sizeX;
	private int sizeY;
	private Vector2 location;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean jump;
	private Vector2 jumpStartLocation;
	private boolean falling;
	private Field field;
	public static final String TEXTURE_PATH = "mario.png";//TODO change
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	
	public Mario(Field field){
		this.field = field;
		jump = false;
		movingLeft = false;
		movingRight = false;
		falling = true;
		sizeX = 1;
		sizeY = 2;
		location = new Vector2(32,500);
	}
	
	public void inputUp(){
		if(!falling && !jump){
			jumpStartLocation = location;
			jump = true;
			
		}
	}
	
	public void inputLeft(){
		movingLeft = true;
	}
	
	public void inputRight(){
		movingLeft = true;
	}
	
	public void inputNoLeft(){
		movingLeft = false;
	}
	
	public void inputNoRight(){
		movingRight = false;
	}
	
	private void physics(){
		if(jump){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) + sizeY+1)
					&& jumpStartLocation.y < location.y+96){//TODO check field up
				location.y++;
			} else {
				jump = false;
				falling = true;
			}
			
		}
		if(falling){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) -1)
					|| location.y%32 != 0){
				location.y--;
			} else {
				falling = false;
			}
		}
		
		if(movingLeft && !movingRight){
			if(!field.fieldIsFull((int)(location.x/32) - 1, (int)(location.y/32))
					&& !field.fieldIsFull((int)(location.x/32)-1, (int)(location.y/32) +1)){//TODO check field up
				location.x--;
			} /*else{
				movingLeft = false;
			}*/
		}
		
		if(movingRight && !movingLeft){
			if(!field.fieldIsFull((int)(location.x/32) + sizeX+ 1, (int)(location.y/32))
					&& !field.fieldIsFull((int)(location.x/32) + sizeX +1, (int)(location.y/32) +1)){//TODO check field up
				location.x++;
			} /*else{
				movingRight = false;
			}*/
		}
		
		if(!jump && !falling){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) -1)
					&& !field.fieldIsFull((int)(location.x/32)+1, (int)(location.y/32) -1)){//TODO check field up
				falling = true;
			}
		}
		
	}
	
	public void update(){
		physics();
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, location.x*Main.SCALING_FACTOR.x, location.y*Main.SCALING_FACTOR.y, 2*sprite.getWidth()*Main.SCALING_FACTOR.x, 2*sprite.getHeight()*Main.SCALING_FACTOR.y);
	}
	
}

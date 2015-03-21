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
	private float speedHor;
	private float speedVer;
	
	public Mario(Field field){
		this.field = field;
		jump = false;
		movingLeft = false;
		movingRight = false;
		falling = true;
		sizeX = 1;
		sizeY = 2;
		speedHor = 1;
		speedVer = 1;
		location = new Vector2(100,500);
	}
	
	public void inputUp(){
		if(!falling && !jump){
			jumpStartLocation = location;
			jump = true;
			speedVer = 7;
		}
	}
	
	public void inputLeft(){
		if(!movingLeft){
			speedHor = 1;
			movingLeft = true;
		}	
	}
	
	public void inputRight(){
		if(!movingRight){
			speedHor = 1;
			movingRight = true;
		}		
	}
	
	public void inputNoLeft(){
		speedHor = 1;
		movingLeft = false;
	}
	
	public void inputNoRight(){
		speedHor = 1;
		movingRight = false;
	}
	
	private void physics(){
		if(jump){
			if((!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) + sizeY+1)
					&& jumpStartLocation.y < location.y+96)){//TODO check field up
				location.y += speedVer;
				speedVer -=0.2;
				if(speedVer < 0){
					jump = false;
					falling = true;
				}
			}  else {
				if((int)(location.y%32) > 7){
					location.y+=speedVer;
					if(speedVer<=7){
						speedVer+=0.2;
					}
				} else if((int)(location.y%32) == 2 && speedVer<location.y%32){
					location.y+=speedVer;
				} else if((int)(location.y%32) == 3 && speedVer<location.y%32){
					location.y+=speedVer;
				} else if((int)(location.y%32) == 4 && speedVer<location.y%32){
					location.y+=speedVer;
				} else if((int)(location.y%32) == 5 && speedVer<location.y%32){
					location.y+=speedVer;
				} else if((int)(location.y%32) == 6 && speedVer<location.y%32){
					location.y+=speedVer;
				} else if((int)(location.y%32) == 7 && speedVer<location.y%32){
					location.y+=speedVer;
				}else {
					location.y+=location.y%32;
					jump = false;
					falling = true;
					speedVer = 1;
				}
			}
			
		}
		if(falling){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) -1)) {
				location.y-=speedVer;
				if(speedVer<=7){
					speedVer+=0.2;
				}
			} else {
				if((int)(location.y%32) > 7){
					location.y-=speedVer;
					if(speedVer<=7){
						speedVer+=0.2;
					}
				} else if((int)(location.y%32) == 2 && speedVer<location.y%32){
					location.y-=speedVer;
				} else if((int)(location.y%32) == 3 && speedVer<location.y%32){
					location.y-=speedVer;
				} else if((int)(location.y%32) == 4 && speedVer<location.y%32){
					location.y-=speedVer;
				} else if((int)(location.y%32) == 5 && speedVer<location.y%32){
					location.y-=speedVer;
				} else if((int)(location.y%32) == 6 && speedVer<location.y%32){
					location.y-=speedVer;
				} else if((int)(location.y%32) == 7 && speedVer<location.y%32){
					location.y-=speedVer;
				}else {
					location.y-=location.y%32;
					falling = false;
					speedVer = 1;
				}
			}
		}
		
		if(movingLeft && !movingRight){
			if(!field.fieldIsFull((int)(location.x/32) - 1, (int)(location.y/32))
					&& !field.fieldIsFull((int)(location.x/32)-1, (int)(location.y/32) +1)){//TODO check field up
				location.x-=speedHor;
				if(speedHor<=7){
					speedHor += 0.2;
				}
			} else{
				speedHor = 1;
			}
		}
		
		if(movingRight && !movingLeft){
			if(!field.fieldIsFull((int)(location.x/32) + sizeX+ 1, (int)(location.y/32))
					&& !field.fieldIsFull((int)(location.x/32) + sizeX +1, (int)(location.y/32) +1)){//TODO check field up
				location.x+=speedHor;
				if(speedHor<=7){
					speedHor += 0.2;
				}
			} else{
				speedHor = 1;
			}
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

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
	private float speedHorNorm;
	private float speedVertNorm;
	private float maxSpeedHor;
	private float maxSpeedVer;
	private float horMult;
	private float vertMult;
	
	public Mario(Field field){
		this.field = field;
		jump = false;
		movingLeft = false;
		movingRight = false;
		falling = true;
		sizeX = 1;
		sizeY = 2;
		speedHor = 10;
		speedVer = 10;
		maxSpeedHor = 500;
		maxSpeedVer = 500;
		speedHorNorm = 200;
		speedVertNorm = 200;
		horMult = 100;
		vertMult = 100;
		location = new Vector2(100,500);
	}
	
	public void inputUp(){
		if(!falling && !jump){
			jumpStartLocation = location;
			jump = true;
			speedVer = speedVertNorm;
		}
	}
	
	public void inputLeft(){
		if(!movingLeft){
			speedHor = speedHorNorm;
			movingLeft = true;
		}	
	}
	
	public void inputRight(){
		if(!movingRight){
			speedHor = speedHorNorm;
			movingRight = true;
		}		
	}
	
	public void inputNoLeft(){
		speedHor = speedHorNorm;
		movingLeft = false;
	}
	
	public void inputNoRight(){
		speedHor = speedHorNorm;
		movingRight = false;
	}
	
	public boolean pointAtCord(float x, float y) {
		return field.fieldIsFull((int)(x/32),(int)(y/32));
	}
	
	private void physics(float delta){		
		if(jump){
			float movement = speedVer*delta;
			boolean y = true;
			for(int i = 0; i < movement; i++){
				if((pointAtCord((location.x),(location.y+64+i))||pointAtCord((location.x+31),(location.y+64+i)))){
					y = false;
					location.y += (i-1);
					jump = false;
					falling = true;
					break;
				}
			}
			if(y){
				location.y += movement;
				speedVer -= vertMult*delta;
				if(speedVer<0){
					speedVer = 0;
					jump = false;
					falling = true;
				}
			}
		}
		if(falling){
			int movement = (int)(speedVer*delta);
			boolean y = true;
			for(int i = 0; i < movement; i++){
				if((pointAtCord((location.x),(location.y-i))||pointAtCord((location.x+31),(location.y-i)))){
					y = false;
					System.out.println(i);
					location.y -= (i-1);
					speedVer = speedVertNorm;
					falling = false;
					break;
				}
			}
			if(y){
				
				location.y -= movement;
				speedVer += vertMult*delta;
				if(speedVer>maxSpeedVer){
					speedVer=maxSpeedVer;
				}
			}
		}
		
		if(movingLeft && !movingRight){
			float movement = speedHor*delta;
			boolean x = true;
			for(int i = 0; i < movement; i++){
				if((pointAtCord((location.x - i),(location.y))||pointAtCord((location.x - i),(location.y+32))||pointAtCord((location.x - i),(location.y+64)))){
					x = false;
					location.x -= (i-1);
					speedHor = speedHorNorm;
					break;
				}
			}
			if(x){
				location.x -= movement;
				speedHor += horMult*delta;
				if(speedHor>maxSpeedHor){
					speedHor=maxSpeedHor;
				}
			}
		}
		if(movingRight && !movingLeft){
			float movement = speedHor*delta;
			boolean x = true;
			for(int i = 0; i < movement; i++){
				if((pointAtCord((location.x + 32 + i),(location.y))||pointAtCord((location.x + 32 + i),(location.y+32))||pointAtCord((location.x + 32 + i),(location.y+64)))){
					x = false;
					location.x += (i-1);
					speedHor = speedHorNorm;
					break;
				}
			}
			if(x){
				location.x += movement;
				speedHor += horMult*delta;
				if(speedHor>maxSpeedHor){
					speedHor=maxSpeedHor;
				}
			}
		}
		
		if(!jump && !falling){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) -1)
					&& !field.fieldIsFull((int)(location.x/32)+1, (int)(location.y/32) -1)){//TODO check field up
				falling = true;
			}
		}
		/*
		if(movingRight && !movingLeft){
			if(!field.fieldIsFull((int)((location.x-31)/32) + sizeX+ 1, (int)(location.y/32))
					&& !field.fieldIsFull((int)((location.x-31)/32) + sizeX +1, (int)(location.y/32) +1)){//TODO check field up
				location.x+=speedHor;
				if(speedHor<=7){
					speedHor += 0.2;
				}
			} else{
				speedHor = 1;
			}
		}
		if(jump){
			if((!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) + sizeY+1) &&
					!field.fieldIsFull((int)((location.x+31)/32), (int)(location.y/32) + sizeY+1)
					&& jumpStartLocation.y < location.y+96)){//TODO check field up
				location.y += speedVer*delta;
				speedVer -=0.2*delta;
				if(speedVer < 0){
					jump = false;
					falling = true;
				}
			}  else {
				if((int)(location.y%32) < 25){
					location.y+=speedVer*delta;
					if(speedVer<=7){
						speedVer+=0.2*delta;
					}
				} else if((int)(location.y%32) == 31 && speedVer<32-location.y%32){
					location.y+=speedVer*delta;
				} else if((int)(location.y%32) == 30 && speedVer<32-location.y%32){
					location.y+=speedVer*delta;
				} else if((int)(location.y%32) == 29 && speedVer<32-location.y%32){
					location.y+=speedVer*delta;
				} else if((int)(location.y%32) == 28 && speedVer<32-location.y%32){
					location.y+=speedVer*delta;
				} else if((int)(location.y%32) == 27 && speedVer<32-location.y%32){
					location.y+=speedVer*delta;
				} else if((int)(location.y%32) == 26 && speedVer<32-location.y%32){
					location.y+=speedVer*delta;
				} else {
					location.y+=32-location.y%32;
					jump = false;
					falling = true;
					speedVer = 1;
				}
			}
			
		}
		if(falling){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) -1)&&
					!field.fieldIsFull((int)((location.x+31)/32), (int)(location.y/32) -1)) {
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
			if(!field.fieldIsFull((int)((location.x-31)/32)+1, (int)(location.y/32))
					&& !field.fieldIsFull((int)((location.x-31)/32)+1, (int)(location.y/32) +1)){//TODO check field up
				location.x-=speedHor;
				if(speedHor<=7){
					speedHor += 0.2;
				}
			} else{
				speedHor = 1;
			}
		}*/
		
	}
	
	public void update(float delta){
		physics(delta);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, location.x*Main.SCALING_FACTOR.x, location.y*Main.SCALING_FACTOR.y, 2*sprite.getWidth()*Main.SCALING_FACTOR.x, 2*sprite.getHeight()*Main.SCALING_FACTOR.y);
	}
	
}

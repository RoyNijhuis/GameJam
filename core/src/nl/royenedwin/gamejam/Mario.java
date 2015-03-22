package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Mario implements MoveableObject{
	private int lives;
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
	public static final String TEXTURE_PATH_HEART = "heart.png";
	public static final String TEXTURE_PATH_WALK1 = "mario_1.png";
	public static final String TEXTURE_PATH_WALK2 = "mario_2.png";
	public static final String TEXTURE_PATH_WALK3 = "mario_3.png";
	public static final String TEXTURE_PATH_WALK4 = "mario_4.png";
	public static final String TEXTURE_PATH_WALK5 = "mario_5.png";
	public static final String TEXTURE_PATH_WALK6 = "mario_6.png";
	private int current_animation;
	private int walkedPixels;
	private static Sprite heart = new Sprite(new Texture(TEXTURE_PATH_HEART));
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private float speedHor;
	private float speedVer;
	private float speedHorNorm;
	private float speedVertNorm;
	private float maxSpeedHor;
	private float maxSpeedVer;
	private float horMult;
	private float vertMult;
	private boolean lastFacedDirectionIsLeft;
	private Vector2 locationBlock;
	private static ArrayList<ChestItem> chestItems;
	private AutoTurret isCarryingTurret;
	
	public Mario(Field field){
		lives = 3;
		isCarryingTurret = null;
		chestItems = new ArrayList<ChestItem>();
		this.field = field;
		walkedPixels = 0;
		current_animation = 0;
		jump = false;
		movingLeft = false;
		movingRight = false;
		falling = true;
		sizeX = 1;
		sizeY = 2;
		speedHor = 10;
		speedVer = 10;
		maxSpeedHor = 500;
		maxSpeedVer = 2000;
		speedHorNorm = 200;
		speedVertNorm = 320;
		horMult = 100;
		vertMult = 360;
		location = new Vector2(100,500);
		lastFacedDirectionIsLeft = false;
		locationBlock = new Vector2((int)location.x/32, (int)location.y/32);
	}
	
	public void carryTurret(AutoTurret turret) {
		isCarryingTurret = turret;
		turret.pickup();
	}
	
	public boolean lastFacedDirectionIsLeft() {
		return lastFacedDirectionIsLeft;
	}
	
	public void carryKeyPressed() {
		if(isCarryingTurret == null) {
			ArrayList<Collidable> set = field.getCollidingObjects();
			
			Vector2 v2 =new Vector2(location.x+64,location.y);
			Vector2 v4 =new Vector2(location.x+32,location.y);
			Vector2 v3 =new Vector2(location.x-32,location.y);
			for(Collidable x: set){
				if(x instanceof AutoTurret) {
					Vector2 pos = ((AutoTurret)x).getPostition();
					if((int)(location.x/32) == (int)(pos.x/32) && (int)(location.y/32) == (int)(pos.y/32)){
						carryTurret((AutoTurret)x);
					} else if((int)(v2.x/32) == (int)(pos.x/32) && (int)(v2.y/32) == (int)(pos.y/32)){
						carryTurret((AutoTurret)x);
					} else if((int)(v3.x/32) == (int)(pos.x/32) && (int)(v3.y/32) == (int)(pos.y/32)){
						carryTurret((AutoTurret)x);
					} else if((int)(v4.x/32) == (int)(pos.x/32) && (int)(v4.y/32) == (int)(pos.y/32)){
						carryTurret((AutoTurret)x);
					}
				}
			}
		} else {
			if(Field.fieldIsEmpty((int)(location.x/32), (int)(location.y/32)) && Field.fieldIsEmpty((int)(location.x/32)+1, (int)(location.y/32)) && Field.fieldIsEmpty((int)(location.x/32)+1, (int)(location.y/32)+1) && Field.fieldIsEmpty((int)(location.x/32), (int)(location.y/32)+1)){
				//velden zijn leeg, dus turret kan geplaatst worden
				isCarryingTurret.setPosition(new Vector2(location.x-(location.x%32), location.y-(location.y%32)));
				isCarryingTurret.place();
				isCarryingTurret = null;
			}
		}
	}
	
	public boolean isCarryingTurret() {
		return isCarryingTurret != null;
	}
	
	public static void addChestItem(ChestItem item) {
		chestItems.add(item);
	}
	
	public static ArrayList<ChestItem> getChestItems() {
		return chestItems;
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
		lastFacedDirectionIsLeft = true;
	}
	
	public void inputRight(){
		if(!movingRight){
			speedHor = speedHorNorm;
			movingRight = true;
		}		
		lastFacedDirectionIsLeft = false;
	}
	
	public void inputNoLeft(){
		speedHor = speedHorNorm;
		movingLeft = false;
		walkedPixels = 0;
		sprite = new Sprite(new Texture(TEXTURE_PATH));
		if(movingRight) {
			lastFacedDirectionIsLeft = false;
		}
	}
	
	public void inputNoRight(){
		speedHor = speedHorNorm;
		movingRight = false;
		walkedPixels = 0;
		sprite = new Sprite(new Texture(TEXTURE_PATH));
		if(movingLeft) {
			lastFacedDirectionIsLeft = true;
		}
	}
	
	public boolean pointAtCord(float x, float y) {
		return field.fieldIsFull((int)(x/32),(int)(y/32));
	}
	
	public boolean pointHasTrampoline(float x, float y) {
		return field.fieldHasTrampoline((int)(x/32),(int)(y/32));
	}
	
	public void shoot() {
		if(lastFacedDirectionIsLeft) {
			Game.createStone(new Vector2(location.x+sprite.getWidth()/2, location.y+sprite.getHeight()/2), new Vector2(-10,5));
		} else {
			Game.createStone(new Vector2(location.x+sprite.getWidth()/2, location.y+sprite.getHeight()/2), new Vector2(10,5));
		}
	}
	
	private void physics(float delta){		
		if(jump){
			float movement = speedVer*delta;
			boolean y = true;
			for(int i = 0; i < movement; i++){
				if((pointAtCord((location.x),(location.y+62+i))||pointAtCord((location.x+31),(location.y+62+i)))){
					y = false;
					location.y += (i-1);
					speedVer -= vertMult*delta;
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
			float movement = speedVer*delta;
			boolean y = true;
			for(int i = 0; i < movement; i++){
				if(pointHasTrampoline((location.x),(location.y-i))||pointHasTrampoline((location.x+31),(location.y-i))) {
					y = false;
					location.y -= (i-1);
					speedVer = speedVertNorm*1.5f;
					falling = false;
					jump = true;
					break;
				}
				if((pointAtCord((location.x),(location.y-i))||pointAtCord((location.x+31),(location.y-i)))){
					y = false;
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
				if((pointAtCord((location.x - i),(location.y))||pointAtCord((location.x - i),(location.y+32))||pointAtCord((location.x - i),(location.y+62)))){
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
				walkedPixels+=movement;
			}
		}
		if(movingRight && !movingLeft){
			float movement = speedHor*delta;
			boolean x = true;
			for(int i = 0; i < movement; i++){
				if((pointAtCord((location.x + 32 + i),(location.y))||pointAtCord((location.x + 32 + i),(location.y+32))||pointAtCord((location.x + 32 + i),(location.y+62)))){
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
				walkedPixels+=movement;
			}
		}
		
		if(!jump && !falling){
			if(!field.fieldIsFull((int)(location.x/32), (int)(location.y/32) -1)
					&& !field.fieldIsFull((int)(location.x/32)+1, (int)(location.y/32) -1)){
				falling = true;
				speedVer = 0;
			}
		}
	}
	
	public void update(float delta){
		physics(delta);
		
		if(movingLeft || movingRight) {
			switch(current_animation) {
			case 0:
				sprite = new Sprite(new Texture(TEXTURE_PATH_WALK1));
				break;
			case 1:
				sprite = new Sprite(new Texture(TEXTURE_PATH_WALK2));
				break;
			case 2:
				sprite = new Sprite(new Texture(TEXTURE_PATH_WALK3));
				break;
			case 3:
				sprite = new Sprite(new Texture(TEXTURE_PATH_WALK4));
				break;
			case 4:
				sprite = new Sprite(new Texture(TEXTURE_PATH_WALK5));
				break;
			case 5:
				sprite = new Sprite(new Texture(TEXTURE_PATH_WALK6));
				break;
			}
			
			if(walkedPixels >= 20) {
				if(current_animation != 5) {
					current_animation++;
				} else {
					current_animation = 0;
				}
				walkedPixels = 0;
			}
		}
		Vector2 temp = new Vector2((int)location.x/32, (int)location.y/32);
		if(!temp.equals(locationBlock)){
			locationBlock = temp;
			collisionBlocks();
		}
	}
	
	private void collisionBlocks(){
		ArrayList<Collidable> set = field.getCollidingObjects();
		
		Vector2 v2 =new Vector2(location.x+64,location.y);
		Vector2 v3 =new Vector2(location.x-32,location.y);
		for(Collidable x: set){
			Vector2 pos = ((StaticObject)x).getPostition();
			if((int)(location.x/32) == (int)(pos.x/32) && (int)(location.y/32) == (int)(pos.y/32)){
				x.isTouched(this);
			} else if((int)(v2.x/32) == (int)(pos.x/32) && (int)(v2.y/32) == (int)(pos.y/32)){
				x.isTouched(this);
			} else if((int)(v3.x/32) == (int)(pos.x/32) && (int)(v3.y/32) == (int)(pos.y/32)){
				x.isTouched(this);
			}
		}
	}
	
	public void teleport(Vector2 position, Portal source) {
		for(Collidable c: field.getCollidingObjects()) {
			if(c instanceof Portal) {
				if(!c.equals(source) && ((Portal)c).getID() == source.getID()) {
					location = new Vector2(((Portal) c).getPostition());
				}
			}
		}
	}
	
	public void takeLive(){
		lives--;
		if(lives==0) {
			Game.kill();
		}
	}
	@Override
	public void render(SpriteBatch batch) {
		for(int i = 0; i<lives; i++){
			batch.draw(heart, 10 + 74*i, 10, heart.getWidth()*Game.SCALING_FACTOR.x, heart.getHeight()*Game.SCALING_FACTOR.y);
		}
		int c = 0;
		for(ChestItem z: chestItems){
			c++;
			batch.draw(z.getSprite(), 1900*Game.SCALING_FACTOR.x - 74*c*Game.SCALING_FACTOR.x, 10, z.getSprite().getWidth()*Game.SCALING_FACTOR.x, z.getSprite().getHeight()*Game.SCALING_FACTOR.y);
		}

		if(lastFacedDirectionIsLeft) {
			if(!sprite.isFlipX()) {
				sprite.flip(true, false);
			}
			batch.draw(sprite, location.x*Game.SCALING_FACTOR.x, location.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
		} else {
			batch.draw(sprite, location.x*Game.SCALING_FACTOR.x, location.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
		}
	}
	
	public Vector2 getPositionPixels() {
		return location;
	}

	@Override
	public Vector2 getPosition() {
		return new Vector2((int)(location.x/32),(int)(location.y/32));
	}

	public void addLive() {
		lives++;
		
	}
	
}

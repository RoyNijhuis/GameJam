package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AutoTurret implements StaticObject, Collidable, Updateable{

	public static final String TEXTURE_PATH = "autoturret.png";
	public static final String LASER_PATH = "laser.png";
	private Sprite sprite;
	private Sprite laserSprite;
	private Vector2 position;
	private int endLaserX;
	private float deltaTime;
	private Mario pickedUp;
	private boolean facingLeft;
	private boolean disabled;
	private float disableTime;
	
	public AutoTurret(boolean facingLeft) {
		disabled = false;
		this.facingLeft = facingLeft;
		sprite = new Sprite(new Texture(TEXTURE_PATH));
		laserSprite = new Sprite(new Texture(LASER_PATH));
		endLaserX=0;
		deltaTime=0;
		disableTime = 0;
	}

	public void render(SpriteBatch batch) {
		
		if(!disabled){
			if(pickedUp == null) {
				if(facingLeft) {
					if(!sprite.isFlipX()) {
						sprite.flip(true, false);
					}
				} else {
					if(sprite.isFlipX()) {
						sprite.flip(true, false);
					}
				}
				
				batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
				int x = (int)(position.x/32);
				int y = (int)(position.y/32)+1;
				
				if(!facingLeft) {
					for(int currentX=x+1; currentX<60; currentX++) {
						if(!Field.fieldIsFull2(currentX, y)) {
							//niet vol, draw laser
							if(currentX == x+1) {
								batch.draw(laserSprite, (currentX*32+16)*Game.SCALING_FACTOR.x, (position.y+37)*Game.SCALING_FACTOR.y, (laserSprite.getWidth()*Game.SCALING_FACTOR.x)/2f, laserSprite.getHeight()*Game.SCALING_FACTOR.y);
							} else {
								batch.draw(laserSprite, (currentX*32)*Game.SCALING_FACTOR.x, (position.y+37)*Game.SCALING_FACTOR.y, laserSprite.getWidth()*Game.SCALING_FACTOR.x, laserSprite.getHeight()*Game.SCALING_FACTOR.y);
							}
							
						} else {
							//vol, stop laser
							endLaserX = currentX-1;
							break;
						}
					}
				} else {
					System.out.println("here");
					for(int currentX=x; currentX>=0; currentX--) {
						if(!Field.fieldIsFull2(currentX, y)) {
							//niet vol, draw laser
							if(currentX == x) {
								batch.draw(laserSprite, (currentX*32)*Game.SCALING_FACTOR.x, (position.y+37)*Game.SCALING_FACTOR.y, (laserSprite.getWidth()*Game.SCALING_FACTOR.x)/2f, laserSprite.getHeight()*Game.SCALING_FACTOR.y);
							} else {
								batch.draw(laserSprite, (currentX*32)*Game.SCALING_FACTOR.x, (position.y+37)*Game.SCALING_FACTOR.y, laserSprite.getWidth()*Game.SCALING_FACTOR.x, laserSprite.getHeight()*Game.SCALING_FACTOR.y);
							}
							System.out.println("going on");
							
						} else {
							System.out.println("stopped");
							//vol, stop laser
							endLaserX = currentX+1;
							break;
						}
					}
				}
			} else {
				
				//batch.draw(sprite, pickedUp.getPositionPixels().x*Game.SCALING_FACTOR.x, pickedUp.getPositionPixels().y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
			}
		} else {
			batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
			
		}
	}
	
	public void pickup() {
		pickedUp = Game.getMario();
		Field.setField(new Vector2((int)(position.x/32), 33-(int)(position.y/32)), null);
	}
	
	public void place() {
		facingLeft = pickedUp.lastFacedDirectionIsLeft();
		pickedUp = null;
		Field.setField(new Vector2((int)(position.x/32), 33-(int)(position.y/32)), this);
	}

	public Vector2 getPostition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public void isTouched(Object o) {
		
	}

	@Override
	public void update(float delta) {
		deltaTime+=delta;
		Vector2 posMario = Game.getMario().getPosition();
		int x = (int)(position.x/32);
		int y = (int)(position.y/32)+1;
		StaticObject[][] fields = Field.getFields();
		if(!disabled){
			if(pickedUp == null) {
				if(!facingLeft) {
					if(posMario.y == y || posMario.y+1 == y) {
						if(posMario.x>x+1 && posMario.x<=endLaserX) {
							//collision with laser or turret
							if(deltaTime>1) {
								Game.getMario().takeLive();
								deltaTime=0;
							}
						}
					}
				} else {
					if(posMario.y == y || posMario.y+1 == y) {
						if(posMario.x<x && posMario.x>=endLaserX) {
							//collision with laser or turret
							if(deltaTime>1) {
								Game.getMario().takeLive();
								deltaTime=0;
							}
						}
					}
				}
			}
		} else {
			System.out.println(disableTime);
			disableTime -= delta;
			if(disableTime<=0){
				disabled = false;
				disableTime = 0;
			}
		}
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	public void hit() {
		disabled = true;
		disableTime = 5;
	}
	
	public void hit2() {
		Field.setField(new Vector2((int)(position.x/32), 33-(int)(position.y/32)), null);
	}
}

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
	
	public AutoTurret() {
		sprite = new Sprite(new Texture(TEXTURE_PATH));
		laserSprite = new Sprite(new Texture(LASER_PATH));
		endLaserX=0;
		deltaTime=0;
	}

	public void render(SpriteBatch batch) {
		if(pickedUp == null) {
			batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
			int x = (int)(position.x/32);
			int y = (int)(position.y/32)+1;
		
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
			batch.draw(sprite, pickedUp.getPositionPixels().x*Game.SCALING_FACTOR.x, pickedUp.getPositionPixels().y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
		}
	}
	
	public void pickup() {
		pickedUp = Game.getMario();
	}
	
	public void place() {
		pickedUp = null;
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
		System.out.println("UPDATE");
		deltaTime+=delta;
		Vector2 posMario = Game.getMario().getPosition();
		int x = (int)(position.x/32);
		int y = (int)(position.y/32)+1;
		StaticObject[][] fields = Field.getFields();
		
		if(pickedUp == null) {
			if(posMario.y == y || posMario.y+1 == y) {
				System.out.println("TURRET YYYYY   !!!!!");
				if(posMario.x>=x && posMario.x<=endLaserX) {
					//collision with laser or turret
					if(deltaTime>1) {
						Game.getMario().takeLive();
						deltaTime=0;
					}
				}
			}
		}
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}
}

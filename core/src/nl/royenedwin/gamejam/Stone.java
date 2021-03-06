package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Stone implements MoveableObject{

	public static final String TEXTURE_PATH = "cobblestone.png";
	private static Sprite sprite = new Sprite(new Texture(TEXTURE_PATH));
	private Vector2 position;
	private Vector2 velocity;
	
	public Stone(Vector2 position, Vector2 velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, position.x*Game.SCALING_FACTOR.x, position.y*Game.SCALING_FACTOR.y, sprite.getWidth()*Game.SCALING_FACTOR.x, sprite.getHeight()*Game.SCALING_FACTOR.y);
	}

	@Override
	public void update(float delta) {
		velocity.y-=delta*15;
		position.x+=velocity.x;
		position.y+=velocity.y;
		if(Field.fieldIsFull2((int)(position.x/32), (int)(position.y)/32)){
			Game.removeObject(this);
		} else if(position.x <0 || position.x>1920 || position.y<0 || position.y>1080){
			Game.removeObject(this);
		}
		Ghost ghost = Game.getGhost();
		if(ghost != null) {
			if((int)(position.x/32) == (int)(ghost.getPosition().x/32) && (int)(position.y/32) == (int)(ghost.getPosition().y/32)){
				ghost.hit();
				Game.removeObject(this);
			} else if((int)(position.x/32) == (int)(ghost.getPosition().x/32)+1 && (int)(position.y/32) == (int)(ghost.getPosition().y/32)){
				ghost.hit();
				Game.removeObject(this);
			} else if((int)(position.x/32) == (int)(ghost.getPosition().x/32) && (int)(position.y/32) == (int)(ghost.getPosition().y/32)+1){
				ghost.hit();
				Game.removeObject(this);
			} else if((int)(position.x/32) == (int)(ghost.getPosition().x/32)+1 && (int)(position.y/32) == (int)(ghost.getPosition().y/32)+1){
				ghost.hit();
				Game.removeObject(this);
			}
		}
		ArrayList<Collidable> set = Field.getCollidingObjects2();
		Vector2 location = position;
		Vector2 v2 =new Vector2(location.x+64,location.y);
		Vector2 v4 =new Vector2(location.x+32,location.y);
		Vector2 v3 =new Vector2(location.x-32,location.y);
		for(Collidable x: set){
			if(x instanceof AutoTurret) {
				AutoTurret z = (AutoTurret) x;
				if((int)(position.x/32) == (int)(z.getPosition().x/32) && (int)(position.y/32) == (int)(z.getPosition().y/32)){
					z.hit();
					Game.removeObject(this);
				} else if((int)(position.x/32) == (int)(z.getPosition().x/32)+1 && (int)(position.y/32) == (int)(z.getPosition().y/32)){
					z.hit();
					Game.removeObject(this);
				} else if((int)(position.x/32) == (int)(z.getPosition().x/32) && (int)(position.y/32) == (int)(z.getPosition().y/32)+1){
					z.hit();
					Game.removeObject(this);
				} else if((int)(position.x/32) == (int)(z.getPosition().x/32)+1 && (int)(position.y/32) == (int)(z.getPosition().y/32)+1){
					z.hit();
					Game.removeObject(this);
				}
			}
		}
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
}

package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FullWalk implements StaticObject{
	private Vector2 position;
	@Override
	public void render(SpriteBatch batch) {
		
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public Vector2 getPostition() {
		// TODO Auto-generated method stub
		return position;
	}
}

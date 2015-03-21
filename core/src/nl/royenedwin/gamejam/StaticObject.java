package nl.royenedwin.gamejam;

import com.badlogic.gdx.math.Vector2;

public interface StaticObject extends Drawable{
	public Vector2 getPostition();
	public void setPosition(Vector2 position);
}

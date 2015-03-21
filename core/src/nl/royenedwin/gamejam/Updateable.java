package nl.royenedwin.gamejam;

import com.badlogic.gdx.math.Vector2;

public interface Updateable {
	public void update(float delta);
	public Vector2 getPosition();
}

package nl.royenedwin.gamejam;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class InputProcessor extends InputAdapter {
	
	private Mario mario;
	
	public InputProcessor(Mario mario) {
		this.mario = mario;
	}
	
	public boolean keyDown (int keycode) {
		if(keycode == Keys.A || keycode == Keys.LEFT) {
			mario.inputLeft();
		} else if(keycode == Keys.D || keycode == Keys.RIGHT) {
			mario.inputRight();
		} else if(keycode == Keys.W || keycode == Keys.UP) {
			mario.inputUp();
		} else if(keycode == Keys.SPACE) {
			System.out.println("SHOOT BITCH");
			mario.shoot();
		}
		System.out.println("key down");
		return true;
	}
	
	public boolean keyUp (int keycode) {
		if(keycode == Keys.A || keycode == Keys.LEFT) {
			mario.inputNoLeft();
		} else if(keycode == Keys.D || keycode == Keys.RIGHT) {
			mario.inputNoRight();
		}
		System.out.println("key up");
		return true;
	}
}

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
		}
		if(keycode == Keys.W || keycode == Keys.UP) {
			mario.inputUp();
		}
		if(keycode == Keys.E) {
			mario.carryKeyPressed();
		}
		if(keycode == Keys.NUMPAD_1 || keycode == Keys.NUM_1) {
			mario.wapenKeyPressed(1);
		}
		if(keycode == Keys.NUMPAD_2 || keycode == Keys.NUM_2) {
			mario.wapenKeyPressed(2);
		}
		if(keycode == Keys.NUMPAD_3 || keycode == Keys.NUM_3) {
			mario.wapenKeyPressed(3);
		}
		if(keycode == Keys.NUMPAD_4 || keycode == Keys.NUM_4) {
			mario.wapenKeyPressed(4);
		}
		if(keycode == Keys.NUMPAD_5 || keycode == Keys.NUM_5) {
			mario.wapenKeyPressed(5);
		}
		if(keycode == Keys.SPACE) {
			mario.shoot();
		}
		return true;
	}
	
	public boolean keyUp (int keycode) {
		if(keycode == Keys.A || keycode == Keys.LEFT) {
			mario.inputNoLeft();
		} else if(keycode == Keys.D || keycode == Keys.RIGHT) {
			mario.inputNoRight();
		}
		return true;
	}
}

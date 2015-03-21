package nl.royenedwin.gamejam;

import com.badlogic.gdx.Screen;

public class Main extends com.badlogic.gdx.Game {
	MainMenu menu;
	
	public void create() {
		menu = new MainMenu(this);
		super.setScreen(menu);
	}
	
	public void setScreen(Screen s) {
		super.setScreen(s);
	}
}

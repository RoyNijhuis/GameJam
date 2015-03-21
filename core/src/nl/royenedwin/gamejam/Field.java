package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Field implements Drawable, Updateable{

	private StaticObject[][] fields;
	
	public Field() {
		Texture level1 = new Texture("level1.png");
		fields = new StaticObject[level1.getHeight()][level1.getWidth()];
		level1.getTextureData().prepare();
		Pixmap tmp = level1.getTextureData().consumePixmap();
		for(int x=0;x<60;x++) {
			for(int y=0;y<34;y++) {
				System.out.println("x=" + (33-y) + " y=" + x + " color=" + tmp.getPixel(33-y, x));
				//fields[33-y][x];
				if(tmp.getPixel(33-y, x) == 255) {
					fields[33-y][x] = new Block();
				} else {
					fields[33-y][x] = null;
				}
			}
		}
		
		//set positions
		for(int x=0;x<60;x++) {
			for(int y=0;y<34;y++) {
				if(fields[33-y][x] != null) {
					fields[33-y][x].setPosition(new Vector2((33-y)*32,x*32));
				}
			}
		}
	}
	
	public StaticObject[][] readMiniMap() {
		return null;
	}
	
	public StaticObject[][] getFields() {
		return fields;
	}
	
	public void setField(Vector2 pos, StaticObject object) {
		this.fields[(int)pos.x][(int)pos.y] = object;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		for(StaticObject[] temp: fields) {
			for(StaticObject o: temp) {
				//Render every object in the field
				if(o != null) {
					o.render(batch);
				}
			}
		}
	}
	
	public boolean fieldIsFull(int x, int y) {
		return fields[x][y] != null;
	}

	@Override
	public void update() {
		
	}
}

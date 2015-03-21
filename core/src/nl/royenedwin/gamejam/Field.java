package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Field implements Drawable, Updateable{

	private StaticObject[][] fields;
	private ArrayList<Collidable> collidingObjects;
	
	public Field() {
		Texture level1 = new Texture("level1.png");
		collidingObjects = new ArrayList<Collidable>();
		fields = new StaticObject[level1.getWidth()][level1.getHeight()];
		level1.getTextureData().prepare();
		Pixmap tmp = level1.getTextureData().consumePixmap();
		for(int x=0;x<60;x++) {
			for(int y=0;y<34;y++) {
				//System.out.println("x=" + y + " y=" + x + " color=" + tmp.getPixel(x, y));
				//fields[33-y][x];
				if(tmp.getPixel(x, y) == 255) {
					fields[x][y] = new Block();
				} else if(tmp.getPixel(x, y) == -16776961) {
					fields[x][y] = new Chest();
					fields[x+1][y] = new FullWalk();
					collidingObjects.add((Collidable)fields[x][y]);
				} else if(tmp.getPixel(x, y) == 117375231) {
					fields[x][y] = new Door();
					collidingObjects.add((Collidable)fields[x][y]);
					fields[x][y-1] = new FullNotWalk();
				} else {
					if(!(fields[x][y] instanceof FullWalk) && !(fields[x][y] instanceof FullNotWalk)) {
						fields[x][y] = null;
					}
				}
			}
		}
		
		//set positions
		for(int x=0;x<60;x++) {
			for(int y=0;y<34;y++) {
				if(fields[x][y] != null) {
					fields[x][y].setPosition(new Vector2(x*32,(33-y)*32));
				}
			}
		}
	}
	
	public ArrayList<Collidable> getCollidingObjects() {
		return collidingObjects;
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
		return fields[x][33-y] != null && !(fields[x][33-y] instanceof Chest) && !(fields[x][33-y] instanceof FullWalk);
	}

	@Override
	public void update(float delta) {
		
	}
}

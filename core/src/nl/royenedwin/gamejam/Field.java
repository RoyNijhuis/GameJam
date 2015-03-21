package nl.royenedwin.gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Field implements StaticObject, Updateable{

	private StaticObject[][] fields;
	
	public Field() {
		fields = new StaticObject[48][27];
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
				o.render(batch);
				
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
